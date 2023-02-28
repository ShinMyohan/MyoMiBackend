package com.myomi.cart.dto;

import com.myomi.cart.entity.Cart;
import com.myomi.product.entity.Product;
import com.myomi.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CartSaveRequestDto {
    private Product product;
    private int prodCnt;

    public Cart toEntity(String userId, CartSaveRequestDto requestDto) {
        return Cart.builder()
                .user(User.builder().id(userId).build())
                .product(requestDto.getProduct())
                .prodCnt(requestDto.getProdCnt())
                .build();
    }
}
