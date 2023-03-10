package com.myomi.seller.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
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
	private String prodImgUrl;
	private int status;
	@JsonFormat(timezone = "Asia/Seoul", pattern = "yy-MM-dd")
	private LocalDateTime modifiedDate;
	
	@Builder
	public SellerProductResponseDto(User sellerId, Product product, Long prodNum, String prodName, Long prodPrice,
			int prodPercentage, Long reviewCnt, String prodImgUrl,int status, LocalDateTime modifiedDate) {
		super();
		this.sellerId = sellerId;
		this.product = product;
		this.prodNum = prodNum;
		this.prodName = prodName;
		this.prodPrice = prodPrice;
		this.prodPercentage = prodPercentage;
		this.reviewCnt = reviewCnt;
		this.prodImgUrl = prodImgUrl;
		this.status = status;
		this.modifiedDate = modifiedDate;
	}
}
