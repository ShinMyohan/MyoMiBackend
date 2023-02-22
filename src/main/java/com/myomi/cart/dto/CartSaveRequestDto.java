package com.myomi.cart.dto;

import com.myomi.product.entity.Product;
import com.myomi.user.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter // jwt전에 임시 사용
public class CartSaveRequestDto {
    private User user;
    private Product product;
    private int prodCnt;
}
