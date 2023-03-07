package com.myomi.review.dto;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Getter
@NoArgsConstructor
public class ReviewReadResponseDto {
	private String userId;
	private String prodName;
	private Long reviewNum;
	private String title;
	private String content;
	private int sort;
	@JsonFormat(timezone = "Asia/Seoul", pattern = "yy-MM-dd")
	private LocalDateTime createdDate;
	private float stars;
	
	@Builder
	public ReviewReadResponseDto(String userId,String prodName,Long reviewNum,String title, String content,int sort,LocalDateTime createdDate, float stars) {
		this.userId=userId;
		this.prodName=prodName;
		this.reviewNum=reviewNum;
		this.title=title;
		this.content=content;
		this.sort=sort;
		this.createdDate=createdDate;
		this.stars=stars;
	}

	}
