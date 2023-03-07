package com.myomi.seller.dto;

import com.myomi.product.entity.Product;
import com.myomi.user.entity.User;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SellerProductResponseDto {
	private User sellerId;
	private Product product;
	private Long prodNum;
	private String prodName;
	private Long prodPrice;
	private int prodPercentage;
	private Long reviewCnt;
	
	@Builder
	public SellerProductResponseDto(User sellerId, Product product, Long prodNum, String prodName, Long prodPrice,
			int prodPercentage, Long reviewCnt) {
		super();
		this.sellerId = sellerId;
		this.product = product;
		this.prodNum = prodNum;
		this.prodName = prodName;
		this.prodPrice = prodPrice;
		this.prodPercentage = prodPercentage;
		this.reviewCnt = reviewCnt;
	}
}
