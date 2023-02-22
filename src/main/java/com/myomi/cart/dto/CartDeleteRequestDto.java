package com.myomi.cart.dto;

import com.myomi.product.entity.Product;
import lombok.Builder;

public class CartDeleteRequestDto {
    private Product product;

    @Builder
    public CartDeleteRequestDto(Product product) {
        this.product = product;
    }
}
