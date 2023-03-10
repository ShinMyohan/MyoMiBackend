package com.myomi.cart.control;

import com.myomi.cart.dto.CartDeleteRequestDto;
import com.myomi.cart.dto.CartReadResponseDto;
import com.myomi.cart.dto.CartSaveRequestDto;
import com.myomi.cart.service.CartService;
import com.myomi.common.status.ProductSoldOutException;
import com.myomi.common.status.ResponseDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> cartList(Authentication user){
        List<CartReadResponseDto> result = cartService.getCartList(user);
        ResponseDetails responseDetails = ResponseDetails.success(result, "/api/cart/list");
        return new ResponseEntity<>(responseDetails, HttpStatus.valueOf(responseDetails.getHttpStatus()));
    }

    @PostMapping("/cart")
    public ResponseEntity<?> cartSave(Authentication user, @RequestBody CartSaveRequestDto requestDto) throws ProductSoldOutException {
        ResponseDetails responseDetails = cartService.addCart(user, requestDto);
        return new ResponseEntity<>(responseDetails, HttpStatus.valueOf(responseDetails.getHttpStatus()));
    }

    @PutMapping("/cart")
    public ResponseEntity<?> cartModify(Authentication user, @RequestBody CartSaveRequestDto requestDto) {
        ResponseDetails responseDetails = cartService.saveCart(user, requestDto);
        return new ResponseEntity<>(responseDetails, HttpStatus.valueOf(responseDetails.getHttpStatus()));
    }

    @DeleteMapping("/cart")
    public ResponseEntity<?> cartRemove(Authentication user, @RequestBody List<CartDeleteRequestDto> requestDto) {
        ResponseDetails responseDetails = cartService.removeCart(user, requestDto);
        return new ResponseEntity<>(responseDetails, HttpStatus.valueOf(responseDetails.getHttpStatus()));
    }
}
