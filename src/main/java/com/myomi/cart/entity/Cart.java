package com.myomi.cart.entity;

import com.myomi.product.entity.Product;
import com.myomi.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@ToString
@Entity
public class Cart {
    @EmbeddedId
    private CartEmbedded id = new CartEmbedded();

    @MapsId("userId") // PK, 회원 아이디
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @MapsId("prodNum") // PK, 상품 번호
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prod_num")
    private Product product;

    @Column(name = "prod_cnt", nullable = false)
    private int prodCnt;

    @Builder
    public Cart(User user, Product product, int prodCnt) {
        this.user = user;
        this.product = product;
        this.prodCnt = prodCnt;
    }

//    public void updateProdCnt(Cart cart) { // 더티체킹
//        this.user = cart.getUser();
//        this.product = cart.getProduct();
//        this.prodCnt = cart.getProdCnt();
//    }

    // 연관관계 편의 메서드
}
