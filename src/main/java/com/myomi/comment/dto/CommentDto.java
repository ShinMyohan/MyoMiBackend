package com.myomi.comment.dto;

import java.time.LocalDateTime;

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
	private Board board;
	private User user;
	private String content;
	private LocalDateTime createdDate;


	@Builder
	public CommentDto(Long cNum, Board board, User user, String content, LocalDateTime createdDate) {
		this.cNum = cNum;
		this.board = board;
		this.user = user;
		this.content = content;
		this.createdDate = createdDate;
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
