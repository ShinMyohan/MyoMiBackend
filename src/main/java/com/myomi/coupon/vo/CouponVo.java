package com.myomi.coupon.vo;

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
public class CouponVo {
	private int num;
	private UserVo user;
	private int sort;
	private int percentage;
	@JsonFormat(timezone = "Asia/Seoul", pattern = "yy-MM-dd")
	private Date createdDate;
	@JsonFormat(timezone = "Asia/Seoul", pattern = "yy-MM-dd")
	private Date usedDate;
	private int status;
}
