package com.myomi.board.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.myomi.board.entity.Board;
import com.myomi.comment.entity.Comment;
import com.myomi.user.entity.User;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardReadResponseDto {
	private Long bNum;
	@JsonIgnore
    private User user;
    private String category;
    private String title;
    private String content;
    @JsonFormat(timezone = "Asia/Seoul", pattern = "yy-MM-dd")
    private LocalDateTime createdDate;
    private Long hits;
    private List<Comment> comments;
 
    
    
    @Builder  
    public BoardReadResponseDto(Long bNum, User user, String category, String title, String content,
    		LocalDateTime createdDate, Long hits,Comment comment, List<Comment> comments) {
    	this.bNum = bNum;
    	this.user = user;
    	this.category = category;
    	this.title = title;
    	this.content = content;
    	this.createdDate = createdDate;
    	this.hits = hits;
    	this.comments = comments;
   
    }

    
    public Board toEntity(User user) {
    	LocalDateTime date = LocalDateTime.now();
    	return Board.builder()
   		//  	.bNum(bNum)
    			.user(user)
    			.category(category)
    			.title(title)
    			.content(content)
    			.createdDate(date)
//    			.hits(hits)
    			.build();
    }

   


}
