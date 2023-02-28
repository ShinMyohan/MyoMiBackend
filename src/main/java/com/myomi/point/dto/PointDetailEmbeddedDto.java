package com.myomi.point.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.myomi.user.entity.User;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PointDetailEmbeddedDto {
	@JsonFormat(timezone = "Asia/Seoul", pattern = "yy-MM-dd")
	private LocalDateTime createdDate;
	@JsonIgnore
	private User user;
	private String uId;
	
	@Builder
	public PointDetailEmbeddedDto(LocalDateTime createdDate, User user, String uId) {
		
		this.createdDate = createdDate;
		this.user = user;
		this.uId = uId;
	}

	
}
