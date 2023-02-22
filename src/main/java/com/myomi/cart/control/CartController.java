package com.myomi.cart.control;

import com.myomi.cart.dto.CartDeleteRequestDto;
import com.myomi.cart.dto.CartReadResponseDto;
import com.myomi.cart.dto.CartSaveRequestDto;
import com.myomi.cart.service.CartService;
import com.myomi.product.entity.Product;
import com.myomi.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
            .id("id1").build();


    @GetMapping("/cart")
    public List<CartReadResponseDto> cartList() {
        return cartService.findCartList(user.getId());
    }

    @PostMapping("/cart")
    public void cartSave(CartSaveRequestDto requestDto) {
        // 임시 상품 정보
        requestDto.setUser(user); // jwt전 임시 사용 -> dto에 setter 사용 금지
        Product prod = new Product();
        prod.setPNum(1L);
        requestDto.setProduct(prod);
        requestDto.setProdCnt(5);
        cartService.addCart(requestDto);
    }

    @DeleteMapping("/cart")
    public void cartRemove(@RequestBody List<CartDeleteRequestDto> requestDto) {
        cartService.removeCart(user, requestDto);
    }
}
