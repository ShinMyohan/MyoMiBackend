package com.myomi.order.service;

import com.myomi.coupon.entity.Coupon;
import com.myomi.coupon.repository.CouponRepository;
import com.myomi.exception.FindException;
import com.myomi.order.dto.OrderDetailRequestDto;
import com.myomi.order.dto.OrderRequestDto;
import com.myomi.order.dto.OrderResponseDto;
import com.myomi.order.entity.Order;
import com.myomi.order.repository.OrderRepository;
import com.myomi.product.entity.Product;
import com.myomi.product.repository.ProductRepository;
import com.myomi.user.entity.User;
import com.myomi.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CouponRepository couponRepository;

    /* TODO: 1. 주문서 작성 (배송, 상세) (OK)
             2. 배송정보 입력 (OK)
             3. 상세정보 입력 (OK)
             4. 내 주문 목록 조회 (OK)
             5. 주문 상세 조회 (OK)
             6. 결제 API
             7. 결제시 포인트 차감/적립/쿠폰적용
             8. 결제 시, 장바구니에서 주문한 상품 삭제 -> cart에서 메서드 가져오기
             9. 배송 3일 전에 주문 취소 (OK)
    */

    // 주문서 작성
    @Transactional
    public Long addOrder(Authentication user, OrderRequestDto requestDto) { // TODO: 수령일 4일전에부터 신청가능
        User u = userRepository.findById(user.getName())
                .orElseThrow(() -> new IllegalArgumentException("로그인한 사용자만 이용 가능합니다."));

        // 실제 상품 가격
        double realPrice = 0.0;

//        try {
        // 상품이 있는지 확인
        for (OrderDetailRequestDto od : requestDto.getOrderDetails()) {
            Product product = productRepository.findById(od.getProdNum())
                    .orElseThrow(() -> new IllegalArgumentException("해당 상품이 없습니다."));
            if (product.getStatus() != 0) {
                log.info("품절된 상품입니다.");
            } else {
                // 상품 가격 계산
                realPrice += product.getOriginPrice() * (1 - product.getPercentage() * 0.01) * od.getProdCnt();
            }
        }

        if (requestDto.getCouponNum() != 0) {
            // 사용 가능한 쿠폰인지 확인
            Coupon coupon = couponRepository.findByCouponNumAndUserId(requestDto.getCouponNum(), u.getId())
                    .orElseThrow(() -> new IllegalArgumentException("사용 가능한 쿠폰이 없습니다."));
            if (coupon.getStatus() != 0) {
                throw new IllegalArgumentException("사용할 수 없는 쿠폰입니다.");
            } else {
                // 쿠폰을 적용한 총 금액
                realPrice *= (1 - coupon.getPercentage() * 0.01);
            }
        }
        // 사용포인트가 본인 가진 것 보다 적은지 확인
        if (u.getPoint().getTotalPoint() < requestDto.getUsedPoint()) {
            throw new IllegalArgumentException("보유포인트 잔액을 초과했습니다.");
        } else {
            // 사용한 포인트를 제외한 총 금액
            realPrice -= requestDto.getUsedPoint();
        }

        if ((int) (realPrice * 0.01) != requestDto.getSavePoint()) {
            log.info("realPrice * 0.01 :" + (int) (realPrice * 0.01) + " , requestDto.getSavePoint() : " + requestDto.getSavePoint());
            throw new IllegalArgumentException("적립 예정 금액이 다릅니다.");
        }

        // 계산한 금액과 가져온 금액이 같은지 확인
        if ((long) realPrice != requestDto.getTotalPrice()) {
            throw new IllegalArgumentException("총 금액을 확인해주세요");
        }

        // 주문 기본 등록
        Order order = requestDto.toEntity(u);
        // 주문 가능한 상품인지 확인
        for (OrderDetailRequestDto orderDetail : requestDto.getOrderDetails()) {
            // 주문 상세 등록
            Optional<Product> prod = productRepository.findById(orderDetail.getProdNum());
            if (prod.isPresent() && prod.get().getStatus() == 0) {
                // 연관관계 등록
                orderDetail.toEntity(orderDetail).registerOrderAndProduct(order, prod.get());
            } else {
                throw new IllegalArgumentException("구매할 수 없는 상품입니다.");
            }
        }
        // 배송 정보 등록
        requestDto.getDelivery().registerOrder(order);
        requestDto.getDelivery().toEntity(order);

        orderRepository.save(order);
        return order.getOrderNum();
//        } catch (IllegalArgumentException e) {
//            throw new IllegalArgumentException("주문에 실패했습니다.");
//        }
    }

    // 회원 정보로 주문 목록 확인
    @Transactional
    public List<OrderResponseDto> getOrderListByUserId(Authentication user) {
        return orderRepository.findAllByUserId(user.getName());
    }

    // 회원 정보로 주문 상세 조회
    @Transactional
    public OrderResponseDto getOrderByUserId(Authentication user, Long num) throws FindException {
        Order order = orderRepository.findByUserIdAndOrderNum(user.getName(), num)
                .orElseThrow(() -> new FindException("해당 주문번호가 없습니다."));
        OrderResponseDto orderResponseDto = new OrderResponseDto();
        return orderResponseDto.toDto(order);
    }
}