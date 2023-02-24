package com.myomi.order.dto;

import com.myomi.order.entity.Delivery;
import com.myomi.order.entity.Order;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class DeliveryDto {
    private Order order;
    private String name;
    private String tel;
    private String addr;
    private String deliveryMsg;
    private String receiveDate;

    public Delivery createDelivery(OrderDto orderDto) {
//        delivery.registerOrder(o);
        return Delivery.builder()
//                .order(Order.builder().order(orderDto.getOrder()).build())
                .name(orderDto.getDelivery().getName())
                .tel(orderDto.getDelivery().getTel())
                .addr(orderDto.getDelivery().getAddr())
                .deliveryMsg(orderDto.getDelivery().getDeliveryMsg())
                .receiveDate(orderDto.getDelivery().getReceiveDate())
                .build();
    }
}
