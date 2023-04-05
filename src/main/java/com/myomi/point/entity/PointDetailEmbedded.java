package com.myomi.point.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
//@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class PointDetailEmbedded implements Serializable {
	//생성일자
	@Column(name = "created_date")
	private LocalDateTime createdDate;
	
	//회원아이디
	@Column(name = "user_id")
	private String uId;

	@Builder
	public PointDetailEmbedded(LocalDateTime createdDate, String uId) {
		this.createdDate = createdDate;
		this.uId = uId;
	}
	
	
}