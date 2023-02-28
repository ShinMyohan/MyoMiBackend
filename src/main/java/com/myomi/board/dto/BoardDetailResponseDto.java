package com.myomi.board.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.myomi.board.entity.Board;
import com.myomi.comment.entity.Comment;
import com.myomi.user.entity.User;

import lombok.Getter;

@Getter
public class BoardDetailResponseDto {
	private Long boardNum;
    private User user;
    private String category;
    private String title;
    private String content;
    private LocalDateTime createdDate;
    private Long hits;
    private List<Comment> comments;
    
    
	public BoardDetailResponseDto(Board entity) {
		
		this.boardNum = entity.getBoardNum();
		this.user = entity.getUser();
		this.category = entity.getCategory();
		this.title = entity.getTitle();
		this.content = entity.getContent();
		this.createdDate = entity.getCreatedDate();
		this.hits = entity.getHits();
		this.comments = entity.getComments();
	}
    
    
}
