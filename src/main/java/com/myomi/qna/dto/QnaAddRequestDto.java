package com.myomi.qna.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter 
@NoArgsConstructor

public class QnaAddRequestDto {
	private String userId;
	private String queTitle;
	private String queContent;
	private MultipartFile file;
	
	@Builder
	public QnaAddRequestDto(String queTitle, String queContent,MultipartFile file, String userId) {
		this.queTitle = queTitle;
		this.queContent = queContent;
		this.file = file;
		this.userId = userId;
	}
	
}
