package com.myomi.comment.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.myomi.board.entity.Board;
import com.myomi.comment.entity.Comment;
import com.myomi.user.dto.UserDto;
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
	private Long boardNum;
	@JsonIgnore
	private User user;
	private String content;
	@JsonFormat(timezone = "Asia/Seoul", pattern = "yy-MM-dd")
	private LocalDateTime createdDate;
	private String category;
	private String title;
	private String userName;
	private Long parent;
	@JsonIgnore
	private List<Comment> reply;
	private boolean enableUpdate;
    private boolean enableDelete;

	@Builder
	public CommentDto(Long commentNum, Board board, Long boardNum, User user, String content, LocalDateTime createdDate,
			String category, String title, String userName, Long parent, List<Comment> reply, boolean enableUpdate,
			boolean enableDelete) {
		super();
		this.commentNum = commentNum;
		this.board = board;
		this.boardNum = boardNum;
		this.user = user;
		this.content = content;
		this.createdDate = createdDate;
		this.category = category;
		this.title = title;
		this.userName = userName;
		this.parent = parent;
		this.reply = reply;
		this.enableUpdate = enableUpdate;
		this.enableDelete = enableDelete;
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






