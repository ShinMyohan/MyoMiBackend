package com.myomi.order.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DeliveryRequestDto {
    private String name;
    private String tel;
    private String addr;
    private String deliveryMsg;
    private String receiveDate;
}
