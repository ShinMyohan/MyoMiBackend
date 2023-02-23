package com.myomi.order.entity;

import com.myomi.product.entity.Product;
import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "orders_detail")
public class OrderDetail {
    @EmbeddedId
    private OrderDetailEmbedded id = new OrderDetailEmbedded();

    @MapsId("oNum")
    @ManyToOne
    @JoinColumn(name = "order_num")
    private Order order;

    @MapsId("pNum") // 복합키
    @ManyToOne
    @JoinColumn(name = "prod_num")
    private Product product;

    @Column(name = "prod_cnt", nullable = false)
    private int prodCnt;

    @Builder
    public OrderDetail(Order order, Product product, int prodCnt) {
        this.order = order;
        this.product = product;
        this.prodCnt = prodCnt;
    }
}
