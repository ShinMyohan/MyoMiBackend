package com.myomi.board.vo;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.myomi.comment.vo.CommentVo;
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
public class BoardVo {
	private int num;
	private UserVo user;
	private String category;
	private String title;
	private String content;
	@JsonFormat(timezone = "Asia/Seoul", pattern = "yy-MM-dd")
	private Date createdDate;
	private int hits;
	private List<CommentVo> comment;
}