package com.myomi.order.service;

import com.myomi.order.dto.OrderDetailDto;
import com.myomi.order.dto.OrderDto;
import com.myomi.order.entity.Order;
import com.myomi.order.repository.OrderRepository;
import com.myomi.user.entity.User;
import com.myomi.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
             5. 주문 상세 조회 (OK)
             6. 결제 API
             7. 결제시 포인트 차감/적립/쿠폰적용
             8. 결제 시, 장바구니에서 주문한 상품 삭제 -> cart에서 메서드 가져오기
             9. 배송 3일 전에 주문 취소 (OK)
    */

    // 주문서 작성 TODO: 상품도 같이 등록되는 문제
    @Transactional
    public void addOrder(Authentication user, OrderDto orderDto) {
        User u = userRepository.findById(user.getName())
                .orElseThrow(() -> new IllegalArgumentException("로그인한 사용자만 이용 가능합니다."));

        // 주문 기본 등록
        Order order = orderDto.toEntity(u, orderDto);

        // 주문 상세 등록
        List<OrderDetailDto> orderDetailList = orderDto.getOrderDetails();
//        OrderDetailDto orderDetailDto = new OrderDetailDto();

//        for (OrderDetail orderDetail : orderDetailList) {
//            // 연관관계 등록
//            orderDetail.registerOrder(order);
//            orderDetailDto.toEntity(orderDetail);
//        }
        for (OrderDetailDto orderDetailDto : orderDetailList) {
            // 연관관계 등록
            orderDetailDto.toEntity(orderDetailDto).registerOrder(order);
//        }

            // 배송 정보 등록
            // 연관관계 등록
//        orderDto.getDelivery().registerOrder(order);
//        DeliveryDto delDto = new DeliveryDto();
//        delDto.toEntity(orderDto);
            orderDto.getDelivery().toEntity(orderDto.getDelivery(), order);

            orderRepository.save(order);
        }
    }

    // 회원 정보로 주문 목록 확인
    @Transactional
    public List<OrderDto> findOrderListByUserId(Authentication user) {
        return orderRepository.findAllByUserId(user.getName());
    }

    // 회원 정보로 주문 상세 조회
    @Transactional
    public OrderDto findOrderByUserId(Authentication user, Long num) {
        Order order = orderRepository.findByUserIdAndOrderNum(user.getName(), num);
        OrderDto orderDto = new OrderDto();
        return orderDto.toDto(user.getName(), order);
    }

    // 배송일 3일 전에 주문 취소 가능(cancledDate update), 일자 계산
    @Transactional
    public void modifyOrderCanceledDate(Authentication user, Long num) {
        Order order = orderRepository.findByUserIdAndOrderNum(user.getName(), num);
        String receiveDate = order.getDelivery().getReceiveDate();
        LocalDateTime now = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate receive = LocalDate.parse(receiveDate, formatter);
        LocalDateTime receiveDay = receive.atStartOfDay();
        int betweenDays = (int) Duration.between(now, receiveDay).toDays();

        if (betweenDays >= 3) {
            order.updateCanceledDate(num);
        } else {
            log.info("배송일 3일전 까지만 취소 가능합니다");
        }
    }


}
