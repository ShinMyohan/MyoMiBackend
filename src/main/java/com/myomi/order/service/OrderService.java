package com.myomi.order.service;

import com.myomi.order.dto.DeliveryDto;
import com.myomi.order.dto.OrderDetailDto;
import com.myomi.order.dto.OrderDto;
import com.myomi.order.entity.Order;
import com.myomi.order.entity.OrderDetail;
import com.myomi.order.repository.OrderRepository;
import com.myomi.user.entity.User;
import com.myomi.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    /* TODO: 1. 주문서 작성 (배송, 상세) (OK)
             2. 배송정보 입력 (OK)
             3. 상세정보 입력 (OK)
             4. 내 주문 목록 조회 (OK)
             5. 주문 상세 조회
             6. 결제 API
             7. 결제시 포인트 차감/적립/쿠폰적용
             8. 결제 시, 장바구니에서 주문한 상품 삭제
             9. 배송일 전에 주문 취소
    */

    // 주문서 작성 TODO: 상품도 같이 등록되는 문제
    public void addOrder(Authentication user, OrderDto orderDto) {
        User u = userRepository.findById(user.getName())
                .orElseThrow(() -> new IllegalArgumentException("로그인한 사용자만 이용 가능합니다."));

        // 주문 기본 등록
        Order order = orderDto.toEntity(u, orderDto);

        // 주문 상세 등록
        List<OrderDetail> orderDetailList = orderDto.getOrderDetail();
        OrderDetailDto orderDetailDto = new OrderDetailDto();

        for (OrderDetail orderDetail : orderDetailList) {
            // 연관관계 등록
            orderDetail.registerOrder(order);
            orderDetailDto.createOrderDetail(orderDetail);
        }

        // 배송 정보 등록
        // 연관관계 등록
        orderDto.getDelivery().registerOrder(order);
        DeliveryDto delDto = new DeliveryDto();
        delDto.createDelivery(orderDto);

        orderRepository.save(order);
    }

    // 회원 정보로 주문 목록 확인
    public List<OrderDto> findOrderListByUserId(Authentication user) {
        return orderRepository.findAllByUserId(user.getName());
    }

    // 회원 정보로 주문 상세 조회
}
