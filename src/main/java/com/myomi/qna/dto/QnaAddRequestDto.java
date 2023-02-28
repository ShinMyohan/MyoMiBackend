package com.myomi.qna.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter 
@NoArgsConstructor

public class QnaAddRequestDto {
	private String queTitle;
	private String queContent;
	
	@Builder
	public QnaAddRequestDto(String queTitle, String queContent) {
		this.queTitle = queTitle;
		this.queContent = queContent;
	}
	
}
