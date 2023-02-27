package com.myomi.order.dto;

import com.myomi.order.entity.Delivery;
import com.myomi.order.entity.Order;
import com.myomi.user.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class OrderRequestDto {
    private String msg;
    private Long couponNum;
    private Long usedPoint;
    private Long totalPrice;
    private Long savePoint;
    private LocalDateTime createdDate;
    private LocalDateTime payCreatedDate;
    private LocalDateTime canceledDate;
    private String impUid;

    // 주문정보
    private List<OrderDetailRequestDto> orderDetails;
    // 배송정보
    private Delivery delivery;

    public Order toEntity(User u) {
        return Order.builder()
                .user(u)
                .createdDate(LocalDateTime.now())
                .msg(msg)
                .couponNum(couponNum)
                .usedPoint(usedPoint)
                .savePoint(savePoint)
                .totalPrice(totalPrice)
                .delivery(delivery)
                .build();
    }
}
