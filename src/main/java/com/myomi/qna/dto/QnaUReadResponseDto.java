package com.myomi.qna.dto;

import java.time.LocalDateTime;

import org.springframework.security.core.Authentication;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.myomi.product.entity.Product;
import com.myomi.qna.entity.Qna;
import com.myomi.user.entity.User;

import lombok.Builder;
import lombok.Getter;

@Getter
public class QnaUReadResponseDto {
	private Long qnaNum;
	
	@JsonIgnore
	private User userId;
	
	@JsonIgnore
	private Product product;
	
	private String id;
	private String pName;
	private String category;
	private int week;
	private String detail;
	private String queTitle;
	private String queContent;
	
	@JsonFormat(timezone = "Asia/Seoul", pattern = "yy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime queCreatedDate;
	
	private String ansContent;
	
	@JsonFormat(timezone = "Asia/Seoul", pattern = "yy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime ansCreatedDate;
	
	private Authentication user;
	
	@Builder
	public QnaUReadResponseDto(Long qnaNum, User userId, Product product, String id, String pName, String category, int week,
			String detail, String queTitle, String queContent, LocalDateTime queCreatedDate, String ansContent,
			LocalDateTime ansCreatedDate, Authentication user) {
		this.qnaNum = qnaNum;
		this.userId = userId;
		this.product = product;
		this.id=id;
		this.pName = pName;
		this.category = category;
		this.week = week;
		this.detail = detail;
		this.queTitle = queTitle;
		this.queContent = queContent;
		this.queCreatedDate = queCreatedDate;
		this.ansContent = ansContent;
		this.ansCreatedDate = ansCreatedDate;
		this.user = user;
	}
	
	
	public QnaUReadResponseDto(Qna qna) {
		this.qnaNum = qna.getQnaNum();
		this.userId = qna.getUserId();
		this.product = qna.getProdNum();
		this.queTitle = qna.getQueTitle();
		this.queContent = qna.getQueContent();
		this.queCreatedDate = qna.getQueCreatedDate();
		this.ansContent = qna.getAnsContent();
		this.ansCreatedDate = qna.getAnsCreatedDate();
	}


	
	

}
