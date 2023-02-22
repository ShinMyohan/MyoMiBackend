package com.myomi.board.dto;

import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnDefault;

import com.myomi.board.entity.Board;
import com.myomi.user.entity.User;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
 public class BoardAddRequestDto {
    private User user;
    private String category;
    private String title;
    private String content;
    private LocalDateTime createdDate;
    private Long hits;
    
    
  @Builder  
 public BoardAddRequestDto(Long bNum, User user, String category, String title, String content,
			LocalDateTime createdDate, Long hits) {

		//this.bNum = bNum;
		this.user = user;
		this.category = category;
		this.title = title;
		this.content = content;
		this.createdDate = createdDate;
	    this.hits = hits;
	}


}