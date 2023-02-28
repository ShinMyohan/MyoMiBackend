package com.myomi.review.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.myomi.user.entity.User;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
@Getter
@ToString
public class ReviewReadResponseDto {
	private String userId;
	private String pName;
	private Long reviewNum;
	private String title;
	private String content;
	@JsonFormat(timezone = "Asia/Seoul", pattern = "yy-MM-dd")
	private LocalDateTime createdDate;
	private float stars;
	
	@Builder
	public ReviewReadResponseDto(String userId,String pName,Long reviewNum,String title, String content,LocalDateTime createdDate, float stars) {
		this.userId=userId;
		this.pName=pName;
		this.reviewNum=reviewNum;
		this.title=title;
		this.content=content;
		this.createdDate=createdDate;
		this.stars=stars;
	}

	}
