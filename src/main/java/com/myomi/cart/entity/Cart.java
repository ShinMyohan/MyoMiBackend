package com.myomi.cart.entity;

import com.myomi.product.entity.Product;
import com.myomi.user.entity.User;
import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@ToString
@Entity
public class Cart {
    @EmbeddedId
    private CartEmbedded id = new CartEmbedded();

    @MapsId("userId") // PK, 회원 아이디
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @MapsId("pNum") // PK, 상품 번호
    @ManyToOne
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

    public void changeProdCnt(int prodCnt) { // 더티체킹
        this.prodCnt = prodCnt;
    }
}
