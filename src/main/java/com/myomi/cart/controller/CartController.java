package com.myomi.cart.controller;

import com.myomi.cart.dto.CartReadResponseDto;
import com.myomi.cart.dto.CartSaveRequestDto;
import com.myomi.cart.service.CartService;
import com.myomi.product.entity.Product;
import com.myomi.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
//@RequestMapping("/cart")
@RequiredArgsConstructor
@Slf4j
public class CartController {

    private final CartService cartService;

    // 임시 회원정보
    User user = User.builder()
            .id("id1").build();


    @GetMapping("/cart")
    public List<CartReadResponseDto> read() {
        return cartService.findByUserId(user.getId());
    }

    @PostMapping("/cart")
    public void create(CartSaveRequestDto cartSaveRequestDto) {
        // 임시 상품 정보
        cartSaveRequestDto.setUser(user); // jwt전 임시 사용 -> dto에 setter 사용 금지
        Product prod = new Product();
        prod.setPNum(2L);
        cartSaveRequestDto.setProduct(prod);
        cartSaveRequestDto.setProdCnt(4);
        cartService.createCart(cartSaveRequestDto);
    }
}
