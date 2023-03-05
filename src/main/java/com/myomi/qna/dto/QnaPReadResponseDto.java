package com.myomi.qna.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.myomi.product.entity.Product;
import com.myomi.qna.entity.Qna;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class QnaPReadResponseDto {
	private Long qnaNum;
	private String userId;
	private Product product;
	private String queTitle;
	private String queContent;
	private String pName;

	
	@JsonFormat(timezone = "Asia/Seoul", pattern = "yy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime queCreatedDate;
	private String ansContent;
	
	@JsonFormat(timezone = "Asia/Seoul", pattern = "yy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime ansCreatedDate;
	
	@Builder
	public QnaPReadResponseDto(Long qnaNum, String userId, Product product,String queTitle, String queContent, LocalDateTime queCreatedDate,
			String ansContent, LocalDateTime ansCreatedDate, String pName) {
		this.qnaNum = qnaNum;
		this.userId = userId;
		this.product = product;
		this.queTitle = queTitle;
		this.queContent = queContent;
		this.queCreatedDate = queCreatedDate;
		this.ansContent = ansContent;
		this.ansCreatedDate = ansCreatedDate;
		this.pName = pName;
	}
	
	//상품 리스트 조회시
	public QnaPReadResponseDto toDto(Qna qna) {
		return QnaPReadResponseDto.builder()
				.qnaNum(qna.getQnaNum())
				.userId(qna.getUserId().getName())
				.queTitle(qna.getQueTitle())
				.queContent(qna.getQueContent())
				.queCreatedDate(qna.getQueCreatedDate())
				.ansContent(qna.getAnsContent())
				.ansCreatedDate(qna.getAnsCreatedDate())
				.build();
	}
	
}
