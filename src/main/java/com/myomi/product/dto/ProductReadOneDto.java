package com.myomi.product.dto;

import java.util.List;

import com.myomi.product.entity.Product;
import com.myomi.qna.dto.QnaPReadResponseDto;
import com.myomi.review.dto.ReviewDetailResponseDto;
import com.myomi.review.dto.ReviewReadResponseDto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductReadOneDto {
	private Long prodNum;
	private String sellerName;
	private String sellerId;
	private String category;
	private String name;
	private Long originPrice;
	private int percentage;
	private int week;
	private String detail;
	private List<ReviewReadResponseDto> reviews;
	private List<ReviewDetailResponseDto>bestReviews;
	private List<QnaPReadResponseDto> qnas;
	private String productImgUrl;
	
	@Builder
	public ProductReadOneDto(Long prodNum, String sellerName, String sellerId,String category, String name, Long originPrice, 
			int percentage, int week, String detail, List<ReviewReadResponseDto> reviews,  List<QnaPReadResponseDto> qnas, List<ReviewDetailResponseDto>bestReviews,String productImgUrl) {
		this.prodNum = prodNum;
		this.sellerName = sellerName;
		this.sellerId = sellerId;
		this.category = category;
		this.name = name;
		this.originPrice = originPrice;
		this.percentage = percentage;
		this.week = week;
		this.detail = detail;
		this.reviews = reviews;
		this.qnas = qnas;
		this.bestReviews = bestReviews;
		this.productImgUrl = productImgUrl;
	}
	
	public ProductReadOneDto toDto(Product product, List<ReviewReadResponseDto> review, List<QnaPReadResponseDto> qnas,List<ReviewDetailResponseDto>bestReviews) {
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
			.bestReviews(bestReviews)
			.build();
	}
}
