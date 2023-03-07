package com.myomi.review.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.myomi.review.entity.Review;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Getter
@NoArgsConstructor
public class ReviewReadResponseDto {
	private String userId;
	private String prodName;
	private Long reviewNum;
	private String title;
	private String content;
	private int sort;
	@JsonFormat(timezone = "Asia/Seoul", pattern = "yy-MM-dd")
	private LocalDateTime createdDate;
	private float stars;
	
	@Builder
	public ReviewReadResponseDto(String userId,String prodName,Long reviewNum,String title, String content,int sort,LocalDateTime createdDate, float stars) {
		this.userId=userId;
		this.prodName=prodName;
		this.reviewNum=reviewNum;
		this.title=title;
		this.content=content;
		this.sort=sort;
		this.createdDate=createdDate;
		this.stars=stars;
	}
	public ReviewReadResponseDto(Review entity) {
		this.prodName = entity.getOrderDetail().getProduct().getName();
		this.reviewNum = entity.getReviewNum();
		this.userId = entity.getUser().getId();
		this.sort = entity.getSort();
		this.title = entity.getTitle();
		this.content = entity.getContent();
		this.createdDate = entity.getCreatedDate();
		this.stars = entity.getStars();
	}
	
	//상품 상세 조회시
	public ReviewReadResponseDto toDto(Review review) {
		return ReviewReadResponseDto.builder()
				.reviewNum(review.getReviewNum())
				.userId(review.getUser().getName())
				.title(review.getTitle())
				.content(review.getContent())
				.createdDate(review.getCreatedDate())
				.stars(review.getStars())
				.build();
	}
}
