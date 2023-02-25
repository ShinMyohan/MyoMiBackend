package com.myomi.product.dto;

import java.util.List;

import com.myomi.qna.entity.Qna;
import com.myomi.review.entity.Review;
import com.myomi.seller.entity.Seller;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProdReadOneDto {
	private Long prodNum;
//	@JsonIgnore //셀러 정보 가지고 올거면 쓰지말자
	private Seller seller;
	private String category;
	private String name;
	private Long originPrice;
	private int percentage;
	private int week;
	private int status;
	private String detail;
//	@JsonIgnore
	private List<Review> reviews;
//	@JsonIgnore
	private List<Qna> qnas;
	
	@Builder
	public ProdReadOneDto(Long prodNum, Seller seller, String category,
			String name, Long originPrice, int percentage, 
			int week, int status, String detail, List<Review> reviews, List<Qna> qnas) {
		this.prodNum = prodNum;
		this.seller = seller;
		this.category = category;
		this.name = name;
		this.originPrice = originPrice;
		this.percentage = percentage;
		this.week = week;
		this.status = status;
		this.detail = detail;
		this.reviews = reviews;
		this.qnas = qnas;
	}
}
