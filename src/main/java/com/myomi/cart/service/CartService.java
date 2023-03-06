package com.myomi.cart.service;

import com.myomi.cart.dto.CartDeleteRequestDto;
import com.myomi.cart.dto.CartReadResponseDto;
import com.myomi.cart.dto.CartSaveRequestDto;
import com.myomi.cart.entity.Cart;
import com.myomi.cart.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartService {

    private final CartRepository cartRepository;

    /*  TODO : 1. 회원 id로 장바구니 목록 불러오기
               2. 장바구니에 저장하기(이미 있으면 수량만 추가)
               3. 장바구니에서 선택 삭제하기(한개, 여러개 가능)
               5. 장바구니 수량 변경
    */

    @Transactional
    public List<CartReadResponseDto> getCartList(Authentication user) {
        List<Cart> carts = cartRepository.findByUserId(user.getName());
        List<CartReadResponseDto> list = new ArrayList<>();
        if (carts.size() == 0) {
            log.info("장바구니가 비었습니다.");
        } else {
            for (Cart cart : carts) {
                if(cart.getProduct().getStatus() != 0) {
                    log.info("품절된 상품입니다.");
                } else {
                    CartReadResponseDto dto = new CartReadResponseDto();
                    list.add(dto.toDto(cart));
                }
            }
        }
        return list;
    }

    // 장바구니 추가
    @Transactional
    public void addCart(Authentication user, CartSaveRequestDto requestDto) {
        if(requestDto.getProduct().getStatus() != 0){
            log.info("품절된 상품입니다.");
        }
        Optional<Cart> cartOpt = cartRepository.findByUserIdAndProduct(user.getName(), requestDto.getProduct());

        if (cartOpt.isPresent()) {
            // 장바구니에 이미 상품이 있다면 수량만 추가
            saveCart(user, requestDto);
        } else {
            Cart cart = requestDto.toEntity(user.getName(), requestDto);
            log.info(cart.getUser().getId() + "님의 장바구니에 상품번호 " + cart.getProduct().getProdNum() + "번째 상품이 담겼습니다.");
            cartRepository.save(cart);
        }
    }

    // 상품 추가할때도 수량 수정, 수량만 수정할 때는 더티체킹만 하도록
    @Transactional
    public void saveCart(Authentication user, CartSaveRequestDto requestDto) {
        cartRepository.updateCart(user.getName(), requestDto.getProduct().getProdNum(), requestDto.getProdCnt());
        log.info(user.getName() + "님의 장바구니에 상품번호 " + requestDto.getProduct().getProdNum() + "번째 상품이 " + requestDto.getProdCnt() + "개 더 추가되었습니다.");
    }

    @Transactional
    public void removeCart(Authentication user, List<CartDeleteRequestDto> requestDto) {
        for (CartDeleteRequestDto dto : requestDto) {
            cartRepository.deleteCartByUserIdAndProduct(user.getName(), dto.getProdNum());
        }
    }
}
