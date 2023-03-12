package com.myomi.qna.dto;

import org.springframework.web.multipart.MultipartFile;

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
	private Long prodNum;
	private String queTitle;
	private String queContent;
	private MultipartFile file;
	
	
	@Builder
	public QnaEditRequestDto(String queTitle, String queContent,MultipartFile file, Long prodNum) {
		this.queTitle = queTitle;
		this.queContent = queContent;
		this.file = file;
		this.prodNum = prodNum;
	}
}
