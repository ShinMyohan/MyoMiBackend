package com.myomi.product.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.myomi.seller.entity.Seller;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductDto {
	private Long prodNum;
	
	@JsonIgnore
	private Seller seller;
//	private SellerDto sellerDto;
	@NotBlank
	private String category;
	private String name;
	@NotBlank
	private Long originPrice;
	private int percentage;
	@NotBlank
	private int week;
	private int status; //있으면 제이슨에서 계속 받으려함
	@Size(max = 60, message = "상품 특이사항을 입력해주세요.")
	private String detail;
	
	@Builder
	public ProductDto(
			Long prodNum, 
			Seller seller, String category,
			String name, Long originPrice, int percentage, 
			int week,
			int status,
			String detail) {
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
	
	//상품 등록시 사용
//	public Product toEntity(ProductDto productDto
////			, Seller seller
//			) {
//		return Product.builder()
//				.seller(seller)
//				.category(category)
//				.name(name)
//				.originPrice(originPrice)
//				.percentage(percentage)
//				.week(week)
//				.detail(detail)
//				.fee(9) //기본값 9로 넣어주려고 셋팅
//				.build();
//	}
}
