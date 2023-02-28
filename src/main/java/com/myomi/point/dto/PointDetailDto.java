package com.myomi.point.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.myomi.point.entity.Point;
import com.myomi.point.entity.PointDetail;
import com.myomi.point.entity.PointDetailEmbedded;
import com.myomi.user.entity.User;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PointDetailDto {
	 @JsonFormat(timezone = "Asia/Seoul", pattern = "yy-MM-dd")
     private LocalDateTime createdDate;

     private int sort;
     private int amount;
     @JsonIgnore
     private Point point;
     private int totalPoint;
     private String id;
     
     private PointDetailEmbedded pointEmbedded;
     
     
     @Builder
	public PointDetailDto(LocalDateTime createdDate, int sort,
			int amount, Point point, int totalPoint, String id, PointDetailEmbedded pointEmbedded) {
		this.createdDate = createdDate;

		this.sort = sort;
		this.amount = amount;
		this.point = point;
		this.totalPoint = totalPoint;
		this.id = id;
		this.pointEmbedded = pointEmbedded;
		
	}
     
     public PointDetail toEntity() {
    	 return PointDetail.builder()
    			 .amount(amount)
    			 .sort(sort)
    			 .build();
     }



}
