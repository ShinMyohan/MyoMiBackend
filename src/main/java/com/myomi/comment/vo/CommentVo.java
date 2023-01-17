package com.myomi.comment.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.myomi.board.vo.BoardVo;
import com.myomi.user.vo.UserVo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CommentVo {
	private int num;
	private BoardVo board;
	private UserVo user;
	private String content;
	private int parent;
	@JsonFormat(timezone = "Asia/Seoul", pattern = "yy-MM-dd")
	private Date createdDate;
}
