package com.myomi.cart.dto;

import com.myomi.cart.entity.Cart;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CartSaveRequestDto {
    private Long prodNum;
    private int prodCnt;

    public Cart toEntity(CartSaveRequestDto requestDto) {
        return Cart.builder()
//                .product(requestDto.getProduct())
                .prodCnt(requestDto.getProdCnt())
                .build();
    }
}
