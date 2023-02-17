package com.myomi.order.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class OrderDetailEmbedded implements Serializable{
	// 주문 번호
	@Column(name = "oder_num")
	private Long oNum;
	// 상품 번호
	@Column(name = "prod_num")
	private Long pNum;
}
