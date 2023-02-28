package com.myomi.order.entity;

import com.myomi.product.entity.Product;
import lombok.Builder;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.myomi.product.entity.Product;
import lombok.Builder;

import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "orders_detail")
public class OrderDetail {
    @EmbeddedId
    private OrderDetailEmbedded id = new OrderDetailEmbedded();

    @MapsId("orderNum")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_num", referencedColumnName = "num")
    private Order order;

    @MapsId("prodNum") // 복합키
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prod_num", referencedColumnName = "num")
    private Product product;

    @Column(name = "prod_cnt", nullable = false)
    private int prodCnt;

    @Builder
    private OrderDetail(Order order, Product product, int prodCnt) {
//        this.order = order;
//        this.id = id;
//        registerOrder(order, product);
        this.prodCnt = prodCnt;
    }

    // 연관관계 편의 메소드
    public void registerOrderAndProduct(Order order, Product product) {
//        if(this.order != null) {
//            this.order.getOrderDetails().remove(this);
//        }
        this.order = order;
        this.product = product;
        order.addOrderDetail(this);

//        if(!order.getOrderDetails().contains(this)) {
//            order.getOrderDetails().add(this);
//        }
    }
}
