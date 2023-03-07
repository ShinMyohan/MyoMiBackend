package com.myomi.cart.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor
public class CartDeleteRequestDto {
    private Long prodNum;

//    @Builder
//    public CartDeleteRequestDto(Long prodNum) {
//        this.prodNum = prodNum;
//    }
}
