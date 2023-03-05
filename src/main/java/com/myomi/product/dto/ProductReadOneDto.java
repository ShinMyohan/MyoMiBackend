package com.myomi.product.dto;

import java.util.List;

import com.myomi.product.entity.Product;
import com.myomi.qna.dto.QnaPReadResponseDto;
import com.myomi.review.entity.Review;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductReadOneDto {
	private Long prodNum;
//	@JsonIgnore //셀러 정보 가지고 올거면 쓰지말자
//	private Seller seller;
	private String sellerName;
	private String sellerId;
	private String category;
	private String name;
	private Long originPrice;
	private int percentage;
	private int week;
//	private int status;
	private String detail;
//	@JsonIgnore
	private List<Review> reviews;
//	@JsonIgnore
//	private List<Qna> qnas;
	private List<QnaPReadResponseDto> qnas;
	private String productImgUrl;
	
	@Builder
	public ProductReadOneDto(Long prodNum, String sellerName, String sellerId,String category,
			String name, Long originPrice, int percentage, int week, 
//			int status,
			String detail, List<Review> reviews, 
//			List<Qna> qnas,
			List<QnaPReadResponseDto> qnas,
			String productImgUrl) {
		this.prodNum = prodNum;
		this.sellerName = sellerName;
		this.sellerId = sellerId;
		this.category = category;
		this.name = name;
		this.originPrice = originPrice;
		this.percentage = percentage;
		this.week = week;
//		this.status = status;
		this.detail = detail;
		this.reviews = reviews;
		this.qnas = qnas;
		this.productImgUrl = productImgUrl;
	}
	
	public ProductReadOneDto toDto(Product product, List<Review> review, List<QnaPReadResponseDto> qnas) {
		return ProductReadOneDto.builder()
			.prodNum(product.getProdNum())
			.sellerName(product.getSeller().getCompanyName())
			.sellerId(product.getSeller().getId())
			.category(product.getCategory())
			.name(product.getName())
			.originPrice(product.getOriginPrice())
			.percentage(product.getPercentage())
			.week(product.getWeek())
			.detail(product.getDetail())
			.reviews(review)
			.qnas(qnas)
			.productImgUrl(product.getProductImgUrl())
			.build();
	}
}
