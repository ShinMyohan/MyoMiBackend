package com.myomi.review.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.myomi.order.vo.OrderVo;
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
public class ReviewVo {
	private int num;
	private OrderVo order;
	private UserVo user;
	private int sort;
	private String title;
	private String content;
	@JsonFormat(timezone = "Asia/Seoul", pattern = "yy-MM-dd")
	private Date createdDate;
	private int stars;
}
