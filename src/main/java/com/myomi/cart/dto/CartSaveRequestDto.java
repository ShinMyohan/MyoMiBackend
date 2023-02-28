package com.myomi.cart.dto;

import com.myomi.cart.entity.Cart;
import com.myomi.product.entity.Product;
import com.myomi.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CartSaveRequestDto {
    private User user;
    private Product product;
    private int prodCnt;

    public Cart toEntity(CartSaveRequestDto requestDto) {
        return Cart.builder()
                .user(requestDto.getUser())
                .product(requestDto.getProduct())
                .prodCnt(requestDto.getProdCnt())
                .build();
    }
}
