package com.myomi.product.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.myomi.seller.entity.Seller;

import lombok.Builder;
import lombok.Getter;

@Getter
//@NoArgsConstructor
public class ProductDto {
	private Long prodNum;
	
	@JsonIgnore
	private Seller seller;
//	private SellerDto sellerDto;
	private String category;
	private String name;
	private Long originPrice;
	private int percentage;
	private int week;
	private int status;
	private String detail;
	
	@Builder
	public ProductDto(Long prodNum, Seller seller, String category,
			String name, Long originPrice, int percentage, 
			int week, int status, String detail) {
		this.prodNum = prodNum;
		this.seller = seller;
		this.category = category;
		this.name = name;
		this.originPrice = originPrice;
		this.percentage = percentage;
		this.week = week;
		this.status = status;
		this.detail = detail;
	}
}
