package com.myomi.cart.service;

import com.myomi.cart.dto.CartDeleteRequestDto;
import com.myomi.cart.dto.CartReadResponseDto;
import com.myomi.cart.dto.CartSaveRequestDto;
import com.myomi.cart.entity.Cart;
import com.myomi.cart.repository.CartRepository;
import com.myomi.common.status.*;
import com.myomi.product.entity.Product;
import com.myomi.product.repository.ProductRepository;
import com.myomi.user.entity.User;
import com.myomi.user.repository.UserRepository;
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
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    // 회원 id로 장바구니 목록 불러오기
    @Transactional
    public List<CartReadResponseDto> getCartList(Authentication user) {
        List<Cart> carts = cartRepository.findByUserId(user.getName());
        List<CartReadResponseDto> list = new ArrayList<>();
        if (carts.size() == 0) {
            log.info("회원의 장바구니에 담겨있는 상품이 없음");
        } else {
            for (Cart cart : carts) {
                if (cart.getProduct().getStatus() != 0) {
                    log.info("장바구니에 넣은 상품이 품절됨 [상품번호 prodNum : {}]", cart.getProduct().getProdNum());
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
    public ResponseDetails addCart(Authentication user, CartSaveRequestDto requestDto) throws ProductSoldOutException {
        String path = "/api/cart";
        Product product = productRepository.findById(requestDto.getProdNum())
                .orElseThrow(() -> new NoResourceException(ErrorCode.RESOURCE_NOT_FOUND, "존재하지 않는 상품입니다."));

        // 회원이거나 판매자는 상품을 장바구니에 담을 수 없음
        User u = userRepository.findById(user.getName())
                .orElseThrow(() -> new TokenValidFailedException(ErrorCode.UNAUTHORIZED, "회원만 이용 가능합니다."));
        if (u.getRole() != 0) {
            log.info("판매자가 장바구니에 상품을 담으려고 시도함 [userId : {}, prodNum : {}]", u.getId(), product.getProdNum());
            throw new TokenValidFailedException(ErrorCode.UNAUTHORIZED, "판매자는 상품을 장바구니에 담을 수 없습니다.");
        }

        if (product.getStatus() != 0) {
            log.info("품절된 상품은 장바구니에 담을 수 없습니다. [상품번호 prodNum : {}]", product.getProdNum());
            throw new ProductSoldOutException(ErrorCode.BAD_REQUEST, "PRODUCT_STATUS_ERROR");
        }
        Optional<Cart> cartOpt = cartRepository.findByUserIdAndProduct(user.getName(), product);

        if (cartOpt.isPresent()) {
            // 장바구니에 이미 상품이 있다면 수량만 추가
            saveCart(user, requestDto);
            log.info("장바구니에 존재하는 상품입니다. 수량을 추가합니다. [회원 userId : {}, 상품 번호 prodNum : {}]",
                    user.getName(), product.getProdNum());
            return new ResponseDetails(requestDto, 200, path);
        } else {
            Cart cart = requestDto.toEntity(requestDto);
            cart.registerUser(u);
            cart.registerProduct(product);
            log.info(u.getId() + "님의 장바구니에 상품번호 " + product.getProdNum() + "번째 상품이 담겼습니다.");
            cartRepository.save(cart);

            return new ResponseDetails(product.getProdNum(), 200, path);
        }
    }

    // 상품 추가할때도 수량 수정, 수량만 수정할 때는 더티체킹만 하도록
    @Transactional
    public ResponseDetails saveCart(Authentication user, CartSaveRequestDto requestDto) {
        String path = "/api/cart";
        Product product = productRepository.findById(requestDto.getProdNum())
                .orElseThrow(() -> new NoResourceException(ErrorCode.RESOURCE_NOT_FOUND, "해당 상품번호에 해당하는 상품이 없습니다."));
        if (product.getStatus() != 0) {
            log.info("품절된 상품은 장바구니에 담을 수 없습니다. [상품번호 prodNum : {}]", product.getProdNum());
            throw new ProductSoldOutException(ErrorCode.BAD_REQUEST, "PRODUCT_STATUS_ERROR");
        }
        cartRepository.updateCart(user.getName(), product.getProdNum(), requestDto.getProdCnt());
        log.info(user.getName() + "님의 장바구니에 상품번호 " + product.getProdNum() + "번째 상품이 " + requestDto.getProdCnt() + "개 수정되었습니다.");
        return new ResponseDetails(requestDto, 200, path);
    }

    @Transactional
    public ResponseDetails removeCart(Authentication user, List<CartDeleteRequestDto> requestDto) {
        String path = "/api/cart";
        for (CartDeleteRequestDto dto : requestDto) {
            cartRepository.deleteCartByUserIdAndProduct(user.getName(), dto.getProdNum());
            log.info(user.getName() + "님의 장바구니에서 " + dto.getProdNum() + "번째 상품을 성공적으로 삭제했습니다.");
        }
        return new ResponseDetails(requestDto, 200, path);
    }
}
