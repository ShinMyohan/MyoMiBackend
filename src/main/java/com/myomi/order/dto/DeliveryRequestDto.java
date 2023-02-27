package com.myomi.order.dto;

import com.myomi.order.entity.Delivery;
import com.myomi.order.entity.Order;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class DeliveryRequestDto {
    private String name;
    private String tel;
    private String addr;
    private String deliveryMsg;
    private String receiveDate;

    // 주문 정보 넣을 시 사용


//    @Builder
//    public DeliveryDto(String name, String tel, String addr, String deliveryMsg, String receiveDate) {
//        this.name = name;
//        this.tel = tel;
//        this.addr = addr;
//        this.deliveryMsg = deliveryMsg;
//        this.receiveDate = receiveDate;
//    }

//    public static DeliveryDto toDto(Delivery delivery) {
//        return DeliveryDto.builder()
//                .name(delivery.getName())
//                .tel(delivery.getTel())
//                .addr(delivery.getAddr())
//                .deliveryMsg(delivery.getDeliveryMsg())
//                .receiveDate(delivery.getReceiveDate())
//                .build();
//    }
}
