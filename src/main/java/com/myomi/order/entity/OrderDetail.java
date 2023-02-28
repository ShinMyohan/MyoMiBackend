package com.myomi.order.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.myomi.product.entity.Product;

<<<<<<< HEAD
import lombok.Builder;
=======
>>>>>>> develop
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "orders_detail")
public class OrderDetail {
    @EmbeddedId
    private OrderDetailEmbedded id = new OrderDetailEmbedded();

    @MapsId("orderNum")
    @ManyToOne
    @JoinColumn(name = "order_num")
    private Order order;

    @MapsId("prodNum") // 복합키
    @ManyToOne
    @JoinColumn(name = "prod_num")
    private Product product;

    @Column(name = "prod_cnt", nullable = false)
    private Long prodCnt;

    @Builder
	public OrderDetail(OrderDetailEmbedded id,Order order, Product product, Long prodCnt) {
		this.id = id;
    	this.order = order;
		this.product = product;
		this.prodCnt = prodCnt;
	}
    
    
}
