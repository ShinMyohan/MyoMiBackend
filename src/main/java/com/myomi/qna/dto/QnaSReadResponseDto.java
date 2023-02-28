package com.myomi.qna.dto;

import java.time.LocalDateTime;

import com.myomi.product.entity.Product;
import com.myomi.user.entity.User;

import lombok.Builder;
import lombok.Getter;

@Getter
public class QnaSReadResponseDto {
	private Long qnaNum;
	private User userId;
	private Product product;
	private String queTitle;
	private String queContent;
	private LocalDateTime queCreatedDate;
	private String ansContent;
	private LocalDateTime ansCreatedDate;
	
	@Builder
	public QnaSReadResponseDto(Long qnaNum, User userId, Product product, String queTitle, String queContent,
			LocalDateTime queCreatedDate, String ansContent, LocalDateTime ansCreatedDate) {
		this.qnaNum = qnaNum;
		this.userId = userId;
		this.product = product;
		this.queTitle = queTitle;
		this.queContent = queContent;
		this.queCreatedDate = queCreatedDate;
		this.ansContent = ansContent;
		this.ansCreatedDate = ansCreatedDate;
	}
	
	
}
