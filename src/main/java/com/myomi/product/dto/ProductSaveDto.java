package com.myomi.product.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor @JsonAutoDetect
public class ProductSaveDto {
	@NotBlank
	private String category;
	
	@NotBlank
	private String name;
	
	@NotBlank
	private Long originPrice;
	
	private int percentage;
	
	@NotBlank
	private int week;
	
//	private int status;
	
	@Size(max = 60, message = "상품 특이사항을 입력해주세요.")
	private String detail;
	
//	public Product toEntity() {
//		return Product.builder()
//				.pNum(pNum)
//				.seller(seller)
////				.seller(user.getId)
//				.category(category)
//				.name(name)
//				.originPrice(originPrice)
//				.percentage(percentage)
//				.week(week)
//				.status(status)
//				.detail(detail)
//				.build();
//	}
}
