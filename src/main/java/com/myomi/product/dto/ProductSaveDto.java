package com.myomi.product.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.myomi.product.entity.Product;
import com.myomi.seller.entity.Seller;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor @JsonAutoDetect
public class ProductSaveDto {
	@NotBlank
	private String category;
	
	@NotBlank
	@Size(min = 1, max = 20, message = "최대 30자까지 입력할 수 있습니다.")
	private String name;
	
	@NotBlank
	@Pattern(regexp = "^[0-9]*$")
	private Long originPrice;
	
	@Pattern(regexp = "^[0-9]*$")
	private int percentage;
	
	@NotBlank
	@Pattern(regexp = "^[0-6]*$")
	private int week;
	
	@Size(max = 60, message = "최대 30자까지 입력할 수 있습니다.")
	private String detail;
	
	private MultipartFile file;
	
	
	@Builder
	public ProductSaveDto(String category, String name, Long originPrice,  int percentage, int week, String detail, MultipartFile file) {
		this.category = category;
		this.name = name;
		this.originPrice = originPrice;
		this.percentage = percentage;
		this.week = week;
		this.detail = detail;
		this.file = file;
	}
	
	//상품 등록시 사용
	public Product toEntity(ProductSaveDto productSaveDto
			, Seller seller, String fileUrl
			) {
		return Product.builder()
				.seller(seller)
				.category(productSaveDto.getCategory())
				.name(productSaveDto.getName())
				.originPrice(productSaveDto.getOriginPrice())
				.percentage(productSaveDto.getPercentage())
				.week(productSaveDto.getWeek())
				.detail(productSaveDto.getDetail())
				.reviewCnt(0L)
				.stars(0)
				.fee(9) //기본값 9로 넣어주려고 셋팅
				.productImgUrl(fileUrl)
				.build();
	}
}
