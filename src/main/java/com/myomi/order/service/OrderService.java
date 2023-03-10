package com.myomi.order.service;

import com.myomi.common.status.*;
import com.myomi.coupon.entity.Coupon;
import com.myomi.coupon.repository.CouponRepository;
import com.myomi.order.dto.OrderDetailRequestDto;
import com.myomi.order.dto.OrderDetailResponseDto;
import com.myomi.order.dto.OrderListResponseDto;
import com.myomi.order.dto.OrderRequestDto;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CouponRepository couponRepository;

    // 주문서 작성
    @Transactional
    public ResponseDetails addOrder(Authentication user, OrderRequestDto requestDto)
                                throws TokenValidFailedException, NoResourceException, ProductSoldOutException {
        String path = "/api/order";
        log.info("주문 가능한 회원인지 확인합니다. [userId : {}]", user.getName());
        User u = userRepository.findById(user.getName())
                .orElseThrow(() -> new TokenValidFailedException(ErrorCode.UNAUTHORIZED, "로그인한 회원만 주문이 가능합니다."));
        if(u.getSignoutDate() != null) {
            log.info("탈퇴한 회원이 주문을 요청함. [userId : {}]", u.getId());
            throw new TokenValidFailedException(ErrorCode.UNAUTHORIZED, "탈퇴한 회원은 주문이 불가능합니다.");
        } else if(u.getRole() != 0) {
            log.info("주문이 불가능한 판매자가 주문을 요청함. [userId : {}]", u.getId());
            throw new TokenValidFailedException(ErrorCode.UNAUTHORIZED, "판매자는 주문이 불가능합니다.");
        }
        // 실제 상품 가격
        double realPrice = 0.0;

        // 상품이 있는지 확인
        for (OrderDetailRequestDto od : requestDto.getOrderDetails()) {
            Product product = productRepository.findById(od.getProdNum())
                    .orElseThrow(() -> new NoResourceException(ErrorCode.RESOURCE_NOT_FOUND, "해당 상품이 없습니다."));
            if(product.getStatus() != 0) {
                log.info("품절된 상품은 주문할 수 없음. [상품번호 prodNum : {}]", product.getProdNum());
                throw new ProductSoldOutException(ErrorCode.BAD_REQUEST, "PRODUCT_STATUS_ERROR");
            } else {
                // 상품 가격 계산
                realPrice += product.getOriginPrice() * (1 - product.getPercentage() * 0.01) * od.getProdCnt();
                log.info("주문 상품의 기본 가격 총합 : " + realPrice);
            }
        }

        // 사용 가능한 쿠폰인지 확인
        if (requestDto.getCouponNum() != 0) {
            Optional<Coupon> coupon = couponRepository.findByCouponNumAndUserId(requestDto.getCouponNum(), u.getId());
            if(coupon.isEmpty()) {
                log.info("해당 유저가 사용 가능한 쿠폰이 아님. [userId : {}, couponNum : {}]" + u.getId(), requestDto.getCouponNum());
                  return new ResponseDetails("사용가능한 쿠폰이 아닙니다.",400, path);
            } else if (coupon.get().getStatus() != 0) {
                log.info("쿠폰이 사용됐거나 만료됐음. [userId : {}, couponNum : {}]" + u.getId(), requestDto.getCouponNum());
                return new ResponseDetails("쿠폰이 사용가능한 상태인지 확인해주세요.",400, path);
            } else {
                // 쿠폰을 적용한 총 금액
                realPrice *= (1 - coupon.get().getPercentage() * 0.01);
                log.info("쿠폰을 적용한 상품의 금액 : " + realPrice);
            }
        }
        // 사용포인트가 본인 가진 것 보다 적은지 확인
        if (u.getPoint().getTotalPoint() < requestDto.getUsedPoint()) {
            log.info("보유포인트 잔액을 초과했음. [userId : {}, userPoint : {}, requestPoint : {}]",
                                                u.getId(), u.getPoint().getTotalPoint(), requestDto.getUsedPoint());
            return new ResponseDetails("보유포인트 잔액을 초과했습니다. 400 에러를 응답합니다.",400, path);
        } else {
            // 사용한 포인트를 제외한 총 금액
            realPrice -= requestDto.getUsedPoint();
            log.info("포인트를 적용한 상품의 금액 : " + realPrice);
        }

        // 적립 예상 금액 확인
        if ((int) (realPrice * 0.01) != requestDto.getSavePoint()) {
            log.info("적립 예상 금액 :" + (int) (realPrice * 0.01) + " , 요청으로 들어온 적립 금액 : " + requestDto.getSavePoint()
                    + "두 값이 다르므로 400 에러를 응답합니다. [userId : {}]", u.getId());
            return new ResponseDetails("적립 예상 금액이 일치하지 않습니다.", 400, path);
        }

        // 계산한 금액과 가져온 금액이 같은지 확인
        if ((long) realPrice != requestDto.getTotalPrice()) {
            log.info("계산된 총 금액 : " + realPrice + ", 요청으로 들어온 총 금액 : " + requestDto.getTotalPrice()
                    + "두 값이 다르므로 400 에러를 응답합니다. [userId : {}]", u.getId());
            return new ResponseDetails("총 금액을 확인해주세요", 400, path);
        }

        // 주문 기본 등록
        Order order = requestDto.toEntity(u);
        for (OrderDetailRequestDto orderDetail : requestDto.getOrderDetails()) {
            // 주문 상세 등록
            Optional<Product> prod = productRepository.findById(orderDetail.getProdNum());
            // 연관관계 등록
            orderDetail.toEntity(orderDetail).registerOrderAndProduct(order, prod.get());
        }
        // 배송 정보 등록
        requestDto.getDelivery().registerOrder(order);
        requestDto.getDelivery().toEntity(order);

        orderRepository.save(order);
        return new ResponseDetails(order.getOrderNum(), 200, path);
    }

    // 회원 정보로 주문 목록 확인 (마이페이지)
    @Transactional
    public ResponseDetails getOrderListByUserId(Authentication user) {
        String path = "/api/order/list";
        Map<Long, List<OrderListResponseDto>> result = orderRepository.findAllByUserId(user.getName());
        log.info("회원의 주문 목록을 불러옴. [userId : {}]", user.getName());
        return new ResponseDetails(result, 200, path);
    }

    // 회원 정보로 주문 상세 조회 (마이페이지)
    @Transactional
    public ResponseDetails getOrderByUserId(Authentication user, Long orderNum) throws NoResourceException {
        String path ="/api/order/" + orderNum;
        log.info("회원이 주문번호로 주문상세를 조회합니다. [userId: {}, orderNum :{}]", user.getName(), orderNum);
        Order order = orderRepository.findByUserIdAndOrderNum(user.getName(), orderNum)
                                        .orElseThrow(() -> new NoResourceException(ErrorCode.RESOURCE_NOT_FOUND, "해당 주문번호가 없습니다."));

        List<Object> orderProdList = new ArrayList<>();
        for (int i = 0; i <= order.getOrderDetails().size() - 1; i++) {
            Product product = order.getOrderDetails().get(i).getProduct();
            // 주문한 상품 이름과 수량
            orderProdList.add(product.getProdNum());
            orderProdList.add(product.getName());
            orderProdList.add(product.getProductImgUrl());
            orderProdList.add(order.getOrderDetails().get(i).getProdCnt());
            // 주문한 상품 한개 당 가격
            orderProdList.add(product.getOriginPrice() * (1 - product.getPercentage() * 0.01));
        }

        OrderDetailResponseDto dto = new OrderDetailResponseDto();
        dto = dto.toDto(order, orderProdList);
        return new ResponseDetails(dto, 200, path);
    }
}