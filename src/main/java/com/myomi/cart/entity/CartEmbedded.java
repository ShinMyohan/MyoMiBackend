package com.myomi.cart.entity;

import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class CartEmbedded implements Serializable{
	// 회원 아이디
	private String userId;
	// 상품번호
	private Long num;
}
