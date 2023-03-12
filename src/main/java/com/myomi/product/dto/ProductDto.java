package com.myomi.product.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.myomi.product.entity.Product;
import com.myomi.seller.entity.Seller;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductDto {
	private Long prodNum;
	@JsonIgnore
	@NotBlank
	private Seller seller;
	@NotBlank
	private String category;
	@NotBlank
	private String name;
	@NotBlank
	private Long originPrice;
	@NotBlank
	private int percentage;
	@NotBlank
	private int week;
	private int status;
	@Size(max = 60, message = "상품 특이사항을 입력해주세요.")
	private String detail;
	private String productImgUrl;
	private Long reviewCnt;
	private float stars;
	private String storeName;
	
	@Builder
	public ProductDto(Long prodNum, Seller seller, String category, String name, Long originPrice, int percentage, 
			int week, int status, String detail, String productImgUrl, Long reviewCnt, float stars, String storeName) {
		this.prodNum = prodNum;
		this.seller = seller;
		this.category = category;
		this.name = name;
		this.originPrice = originPrice;
		this.percentage = percentage;
		this.week = week;
		this.status = status;
		this.detail = detail;
		this.productImgUrl = productImgUrl;
		this.reviewCnt = reviewCnt;
		this.stars = stars;
		this.storeName = storeName;
	}
	
	//상품 리스트 조회시
	public ProductDto toDto(Product product) {
		return ProductDto.builder()
				.prodNum(product.getProdNum())
				.seller(product.getSeller())
				.category(product.getCategory())
				.name(product.getName())
				.originPrice(product.getOriginPrice())
				.percentage(product.getPercentage())
				.week(product.getWeek())
				.detail(product.getDetail())
				.productImgUrl(product.getProductImgUrl())
				.reviewCnt(product.getReviewCnt())
				.stars(product.getStars())
				.storeName(product.getSeller().getCompanyName())
				.status(product.getStatus())
				.build();
	}
}
