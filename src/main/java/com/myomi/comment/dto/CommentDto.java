package com.myomi.comment.dto;

import java.time.LocalDateTime;

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
public class CommentDto {
	private Long cNum;
	@JsonIgnore
	private Board board;
	@JsonIgnore
	private User user;
	private String content;
	@JsonFormat(timezone = "Asia/Seoul", pattern = "yy-MM-dd")
	private LocalDateTime createdDate;
	private String category;
	private String title;
	private String userId;


	@Builder
	public CommentDto(Long cNum, Board board, User user, String content, LocalDateTime createdDate, String category,
			String title,String userId) {
		super();
		this.cNum = cNum;
		this.board = board;
		this.user = user;
		this.content = content;
		this.createdDate = createdDate;
		this.category = category;
		this.title = title;
		this.userId = userId;
	}
	

	public Comment toEntity(User user, Board board) {
		LocalDateTime date = LocalDateTime.now();
		return Comment.builder()
				.board(board)
				.user(user)
				.content(content)
				.createdDate(date)
				.build();
	}


	

}
