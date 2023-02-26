package com.myomi.product.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.myomi.product.entity.Product;
import com.myomi.seller.entity.Seller;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor @JsonAutoDetect
public class ProductUpdateDto {
	@NotBlank
	private String category;
//	
	@NotBlank
	private String name;
//	
	@NotBlank
	private Long originPrice;
	
	private int percentage;
	
	@NotBlank
	private int week;
	
	@Size(max = 60, message = "상품 특이사항을 입력해주세요.")
	private String detail;
	
	private int status;
	
	//상품 업데이트시 사용
	public Product toEntity(Long prodNum, 
			ProductUpdateDto productUpdateDto
			, Seller seller) {
		return Product.builder()
				.prodNum(prodNum)
				.seller(seller)
				.category(category)
				.name(name)
				.originPrice(originPrice)
				.percentage(percentage)
				.week(week)
				.detail(detail)
				.status(status)
				.build();
	}
}