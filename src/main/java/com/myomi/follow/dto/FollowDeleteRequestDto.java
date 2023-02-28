package com.myomi.follow.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.myomi.seller.entity.Seller;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor 
public class FollowDeleteRequestDto {
	private String sellerId;

}
