package com.myomi.cart.dto;

import com.myomi.product.entity.Product;
import com.myomi.user.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CartReadResponseDto {
    private User user;
    private Product product;
    private int prodCnt;

    @Builder
    public CartReadResponseDto(Product product, int prodCnt) {
        this.product = product;
        this.prodCnt = prodCnt;
    }
}
