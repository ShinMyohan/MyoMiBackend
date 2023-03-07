package com.myomi.point.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MyPageDto {
    private int totalPoint;
    private Long couponCount;
    private Long followCount;
    private String userName;
    private int membership;
    
    @Builder
    public MyPageDto(int totalPoint, Long couponCount, Long followCount, String userName, int membership) {
    	this.totalPoint = totalPoint;
    	this.couponCount = couponCount;
    	this.followCount = followCount;
    	this.userName = userName;
    	this.membership = membership;
    }
	
	
}
