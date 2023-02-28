package com.myomi.seller.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.myomi.product.entity.Product;
import com.myomi.qna.entity.Qna;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SellerInfoResponseDto {
	@JsonIgnore
	private Product product;
	@JsonIgnore
	private Qna qna;
	
	private String companyName;
	private Long followCnt;
	private int qnaCnt;
	private Long orderCnt;
	
	@Builder
	public SellerInfoResponseDto(Product product, Qna qna, String companyName, Long followCnt, int qnaCnt,
			Long orderCnt) {
		this.product = product;
		this.qna = qna;
		this.companyName = companyName;
		this.followCnt = followCnt;
		this.qnaCnt = qnaCnt;
		this.orderCnt = orderCnt;
	}
	
	
	
	

}
