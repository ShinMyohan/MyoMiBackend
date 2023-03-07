package com.myomi.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderSumResponseDto {
    private String userId;
    private Long totalPrice;
}
