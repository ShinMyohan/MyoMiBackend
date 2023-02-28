package com.myomi.cart.dto;

import com.myomi.cart.entity.Cart;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CartReadResponseDto {
    private Long prodNum;
    private int prodCnt;

    @Builder
    public CartReadResponseDto(Long prodNum, int prodCnt) {
        this.prodNum = prodNum;
        this.prodCnt = prodCnt;
    }

    public CartReadResponseDto toDto(Cart cart) {
        return CartReadResponseDto.builder()
                .prodNum(cart.getProduct().getProdNum())
                .prodCnt(cart.getProdCnt())
                .build();
    }
}
