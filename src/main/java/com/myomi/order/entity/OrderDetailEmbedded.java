package com.myomi.order.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class OrderDetailEmbedded implements Serializable{
	// 주문 번호
	@Column(name="order_num")
	private Long oNum;
	// 상품 번호
	@Column(name="prod_num")
	private Long pNum;
}
