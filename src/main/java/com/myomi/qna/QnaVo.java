package com.myomi.qna;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

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
	private String userId;
	private int prodNum;
	private String queTitle;
	private String queContent;
	private Date queCreatedDate;
	private String ansContent;
	@JsonFormat(timezone = "Asia/Seoul", pattern = "yy-MM-dd")
	private Date ansCreatedDate;
}
