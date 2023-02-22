package com.myomi.cart.dto;

import com.myomi.product.entity.Product;
import lombok.*;

@Getter @Setter @NoArgsConstructor // setter 임시 사용
public class CartDeleteRequestDto {
    private Product product;
}
