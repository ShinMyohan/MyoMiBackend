package com.myomi.review.vo;

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
public class BestReviewVo {
	private ReviewVo review; //=reviewNum
	@JsonFormat(timezone = "Asia/Seoul", pattern = "yy-MM-dd")
	private Date createdDate;
}
