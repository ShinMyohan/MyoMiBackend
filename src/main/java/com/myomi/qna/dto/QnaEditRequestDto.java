package com.myomi.qna.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.myomi.product.entity.Product;
import com.myomi.user.entity.User;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class QnaEditRequestDto {
	@JsonIgnore
	private User userId;
	@JsonIgnore
	private Product prodNum;
	private String queTitle;
	private String queContent;
	
	@Builder
	public QnaEditRequestDto(String queTitle, String queContent) {
		this.queTitle = queTitle;
		this.queContent = queContent;
	}
}
