package com.myomi.follow.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.myomi.seller.entity.Seller;

import lombok.Builder;
import lombok.Getter;

@Getter
public class FollowReadResponseDto {
	private String sellerId;
	private String companyName;
	private Long followCnt;
	
	@Builder
	public FollowReadResponseDto(String sellerId, String companyName, Long followCnt) {
		this.sellerId = sellerId;
		this.companyName = companyName;
		this.followCnt = followCnt;

	}
	
	
	

}
