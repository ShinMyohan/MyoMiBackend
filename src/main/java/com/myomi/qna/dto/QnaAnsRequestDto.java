package com.myomi.qna.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.myomi.product.entity.Product;
import com.myomi.user.entity.User;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class QnaAnsRequestDto {
	@JsonIgnore
	private User userId;
	@JsonIgnore
	private Product prodNum;
	private String ansContent;

	
	@Builder
	public QnaAnsRequestDto(User userId, Product prodNum, String ansContent) {
		this.userId = userId;
		this.prodNum = prodNum;
		this.ansContent = ansContent;
	}
		

}
