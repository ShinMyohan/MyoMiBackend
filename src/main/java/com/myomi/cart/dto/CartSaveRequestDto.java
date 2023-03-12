package com.myomi.cart.dto;

import com.myomi.cart.entity.Cart;
import com.myomi.product.entity.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CartSaveRequestDto {
    private Product product;
    private int prodCnt;

    public Cart toEntity(CartSaveRequestDto requestDto) {
        return Cart.builder()
                .product(requestDto.getProduct())
                .prodCnt(requestDto.getProdCnt())
                .build();
    }
}
