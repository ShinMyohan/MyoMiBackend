package com.myomi.order.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PaymentCancelRequestDto {
    private Long orderNum;
}
