package com.myomi.qna.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter 
@NoArgsConstructor

public class QnaAddRequestDto {
	private String queTitle;
	private String queContent;
	private MultipartFile file;
	
	@Builder
	public QnaAddRequestDto(String queTitle, String queContent,MultipartFile file) {
		this.queTitle = queTitle;
		this.queContent = queContent;
		this.file = file;
	}
	
}
