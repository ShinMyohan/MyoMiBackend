package com.myomi.qna.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class QnaVo {
	private int num;
	private UserVo user;
	private int prodNum;
	private String queTitle;
	private String queContent;
	@JsonFormat(timezone = "Asia/Seoul", pattern = "yy-MM-dd")
	private Date queCreatedDate;
	private String ansContent;
	@JsonFormat(timezone = "Asia/Seoul", pattern = "yy-MM-dd")
	private Date ansCreatedDate;
}
