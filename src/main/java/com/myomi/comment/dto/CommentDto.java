package com.myomi.comment.dto;

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
public class CommentDto {
	private Long commentNum;
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
	private int parent;
    private List<Comment> reply;


	@Builder
	public CommentDto(Long commentNum, Board board, User user, String content, LocalDateTime createdDate, String category,
			String title,String userId, List<Comment> reply) {
		super();
		this.commentNum = commentNum;
		this.board = board;
		this.user = user;
		this.content = content;
		this.createdDate = createdDate;
		this.category = category;
		this.title = title;
		this.userId = userId;
		this.parent = parent;
		this.reply = reply;
	}
	

	public Comment toEntity(User user, Board board) {
		LocalDateTime date = LocalDateTime.now();
		return Comment.builder()
				.board(board)
				.user(user)
				.content(content)
				.createdDate(date)
				.parent(parent)
				.build();
	}


	

}
