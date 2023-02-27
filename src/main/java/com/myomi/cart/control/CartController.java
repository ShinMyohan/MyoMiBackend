package com.myomi.cart.control;

import com.myomi.cart.dto.CartDeleteRequestDto;
import com.myomi.cart.dto.CartReadResponseDto;
import com.myomi.cart.dto.CartSaveRequestDto;
import com.myomi.cart.service.CartService;
import com.myomi.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RequestMapping("/cart")
@RequiredArgsConstructor
@Slf4j
public class CartController {

    private final CartService cartService;

    // 임시 회원정보
    User user = User.builder()
            .id("id2").build();


    @GetMapping("/cart")
    public List<CartReadResponseDto> cartList() {
        return cartService.findCartList(user);
    }

    @PostMapping("/cart")
    public void cartSave(@RequestBody CartSaveRequestDto requestDto) {
        cartService.addCart(requestDto);
    }

    @PutMapping("/cart")
    public void cartModify(@RequestBody CartSaveRequestDto requestDto) {
        cartService.saveCart(requestDto);
    }

    @DeleteMapping("/cart")
    public void cartRemove(Authentication user, @RequestBody List<CartDeleteRequestDto> requestDto) {
        cartService.removeCart(user, requestDto);
    }
}
