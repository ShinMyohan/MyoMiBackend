package com.myomi.cart.service;

import com.myomi.cart.dto.CartDeleteRequestDto;
import com.myomi.cart.dto.CartReadResponseDto;
import com.myomi.cart.dto.CartSaveRequestDto;
import com.myomi.cart.entity.Cart;
import com.myomi.cart.repository.CartRepository;
import com.myomi.product.entity.Product;
import com.myomi.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
               3. 장바구니에서 선택 삭제하기
               4. 장바구니 모두 삭제하기(위와 쿼리가 많이 다른지 확인)
               5. 장바구니 수량 변경
    *
    * */


    @Transactional
    public List<CartReadResponseDto> findCartList(String userId) {
        List<Cart> carts = cartRepository.findListByUserId(userId);
        List<CartReadResponseDto> list = new ArrayList<>();
        if (carts.size() == 0) {
            log.info("장바구니가 비었습니다.");
        } else {

            for (Cart cart : carts) {
                CartReadResponseDto dto = CartReadResponseDto.builder()
//                    .user(user)
                        .product(cart.getProduct())
                        .prodCnt(cart.getProdCnt())
                        .build();
                list.add(dto);
            }
        }
        return list;
    }

    @Transactional
    public void addCart(CartSaveRequestDto requestDto) {
        // 장바구니에 이미 상품이 있다면 수량만 추가
        String userId = requestDto.getUser().getId();
        Product prod = requestDto.getProduct();
        Optional<Cart> cartOpt = cartRepository.findByUserIdAndProduct(userId, prod);
        if (cartOpt.isEmpty()) {
            Cart cart = Cart.builder()
                    .user(requestDto.getUser())
                    .product(requestDto.getProduct())
                    .prodCnt(requestDto.getProdCnt())
                    .build();
            log.info(cart.getUser().getId() + "님의 장바구니에 상품번호 " + cart.getProduct().getPNum() + "번째 상품이 담겼습니다.");
            cartRepository.save(cart);
        } else {
            modifyCart(cartOpt.get());
            log.info(requestDto.getUser().getId() + "님의 장바구니에 상품번호 " + requestDto.getProduct().getPNum() + "번째 상품이 " + requestDto.getProdCnt() + "개 추가되었습니다.");
        }
    }

    @Transactional
    public void modifyCart(Cart cart) {
        cart.updateProdCnt(cart.getProdCnt()); // 더티체킹
    }

    @Transactional
    public void removeCart(User user, List<CartDeleteRequestDto> requestDto) {
        String userId = user.getId();
        for(CartDeleteRequestDto cart : requestDto) {
            cartRepository.deleteCartByUserIdAndProduct(userId, cart.getProduct().getPNum());
        }
    }
}
