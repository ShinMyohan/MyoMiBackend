package com.myomi.order.service;

import com.myomi.order.dto.OrderDto;
import com.myomi.order.entity.Delivery;
import com.myomi.order.entity.Order;
import com.myomi.order.entity.OrderDetail;
import com.myomi.order.repository.OrderRepository;
import com.myomi.user.entity.User;
import com.myomi.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    /* TODO: 1. 주문서 작성 (배송, 상세)
             2. 장바구니에서 가져옴
             3. 배송정보 입력
             4. 상세정보 입력
             5. 결제 API
             6. 결제시 포인트 차감/적립/쿠폰적용
             7. 내 주문 목록 조회
             8. 주문 상세 조회
             9. 배송일 전에 주문 취소
    */

    // 주문서 작성
    public void addOrder(Authentication user, OrderDto requestDto) {
        User u = userRepository.findById(user.getName())
                .orElseThrow(() -> new IllegalArgumentException("로그인한 사용자만 이용 가능합니다."));

        Long savePoint = (long)(requestDto.getTotalPrice() * 0.01);

        Order order = Order.builder()
                .user(u)
                .createdDate(LocalDateTime.now())
                .msg(requestDto.getMsg())
                .couponNum(requestDto.getCouponNum())
                .usedPoint(requestDto.getUsedPoint())
                .savePoint(savePoint) // TODO: 그냥 받아와도 될까?
                .totalPrice(requestDto.getTotalPrice()) // TODO: 그냥 받아와도 될까?2

                .build();

        List<OrderDetail> odList = addOrderDetail(order, requestDto);
        Delivery delivery = addDelivery(order, requestDto);

        Order.builder().orderDetail(odList).delivery(delivery).build();
        orderRepository.save(order);

    }

    // 주문 상세 작성
    protected List<OrderDetail> addOrderDetail(Order order, OrderDto requestDto) {
        List<OrderDetail> list = new ArrayList<>();
        for (int i = 0; i <= requestDto.getProducts().size() - 1; i++) {
            OrderDetail od = OrderDetail.builder()
                    .order(order)
                    .product(requestDto.getProducts().get(i))
                    .prodCnt(requestDto.getProdCnt())
                    .build();
            list.add(od);
        }
        return list;
    }

    // 배송정보 작성
    protected Delivery addDelivery(Order order, OrderDto requestDto) {
        return Delivery.builder()
                .order(order)
                .name(requestDto.getName())
                .tel(requestDto.getTel())
                .addr(requestDto.getAddr())
                .deliveryMsg(requestDto.getDeliveryMsg())
                .receiveDate(requestDto.getReceiveDate())
                .build();
    }

    public List<OrderDto> findOrderListByUserId(Authentication user) {
        return orderRepository.findAllByUserId(user.getName());
    }


}
