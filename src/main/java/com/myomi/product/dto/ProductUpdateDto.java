package com.myomi.product.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.myomi.product.entity.Product;
import com.myomi.seller.entity.Seller;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor @JsonAutoDetect
public class ProductUpdateDto {	
	@Size(max = 60, message = "상품 특이사항을 입력해주세요.")
	private String detail;
	
	private int status;
	
	//상품 업데이트시 사용
	public Product toEntity(Long prodNum, 
			ProductUpdateDto productUpdateDto
			, Seller seller, LocalDateTime date) {
		return Product.builder()
				.prodNum(prodNum)
				.seller(seller)
				.detail(productUpdateDto.getDetail())
				.status(productUpdateDto.getStatus())
				.modifiedDate(date)
				.build();
	}
}