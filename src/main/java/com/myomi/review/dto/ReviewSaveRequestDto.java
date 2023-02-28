package com.myomi.review.dto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter@NoArgsConstructor

public class ReviewSaveRequestDto {
	
	private String title;
	private String content;
	private float stars;
	
	@Builder
	public ReviewSaveRequestDto(String title, String content, float stars) {
		this.title=title;
		this.content=content;
		this.stars=stars;
	}
}
