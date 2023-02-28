package com.myomi.point.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.myomi.comment.entity.Comment;
import com.myomi.user.entity.User;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PointDto {

	private String id;
	@JsonIgnore
	private User userId;
	private int totalPoint;
	
	@Builder
	public PointDto(String id, User userId, int totalPoint) {
		this.id = id;
		this.userId = userId;
		this.totalPoint = totalPoint;
	}
	
}
