package com.myomi.point.dto;

import com.myomi.membership.entity.MembershipLevel;
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
    private MembershipLevel membership;
    
    @Builder
    public MyPageDto(int totalPoint, Long couponCount, Long followCount, String userName, MembershipLevel membership) {
    	this.totalPoint = totalPoint;
    	this.couponCount = couponCount;
    	this.followCount = followCount;
    	this.userName = userName;
    	this.membership = membership;
    }
	
	
}
