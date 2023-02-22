package com.myomi.cart.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import com.myomi.product.entity.Product;
import com.myomi.user.entity.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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

    @MapsId("num") // PK, 상품 번호
    @ManyToOne
    @JoinColumn(name = "prod_num")
    private Product product;

    @Column(name = "prod_cnt", nullable = false)
    private int prodCnt;
}
