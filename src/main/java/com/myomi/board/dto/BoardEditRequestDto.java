package com.myomi.board.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BoardEditRequestDto {
    private String category;
    private String title;
    private String content;
    
   
    @Builder
	public BoardEditRequestDto(String category, String title, String content) {
		this.category = category;
		this.title = title;
		this.content = content;
	}
    
    
     
    
}
