package com.myomi.cart.control;

import com.myomi.cart.dto.CartDeleteRequestDto;
import com.myomi.cart.dto.CartReadResponseDto;
import com.myomi.cart.dto.CartSaveRequestDto;
import com.myomi.cart.service.CartService;
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

    @GetMapping("/cart/list")
    public List<CartReadResponseDto> cartList(Authentication user) {
        return cartService.getCartList(user);
    }

    @PostMapping("/cart")
    public void cartSave(Authentication user, @RequestBody CartSaveRequestDto requestDto) {
        cartService.addCart(user, requestDto);
    }

    @PutMapping("/cart")
    public void cartModify(Authentication user, @RequestBody CartSaveRequestDto requestDto) {
        cartService.saveCart(user, requestDto);
    }

    @DeleteMapping("/cart")
    public void cartRemove(Authentication user, @RequestBody List<CartDeleteRequestDto> requestDto) {
        cartService.removeCart(user, requestDto);
    }
}
