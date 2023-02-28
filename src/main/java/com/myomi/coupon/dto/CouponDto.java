package com.myomi.coupon.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.myomi.coupon.entity.Coupon;
import com.myomi.user.entity.User;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CouponDto {
private Long couponNum;
    
    @JsonIgnore
	private User user;
	private int sort;
	@JsonFormat(timezone = "Asia/Seoul", pattern = "yy-MM-dd")
	private LocalDateTime createdDate;
	private int percentage;
	@JsonFormat(timezone="Asia/Seoul", pattern ="yyyy-MM-dd")
	private LocalDateTime usedDate;
	private int status;
	private String userId;
	
	
	@Builder
	public CouponDto(Long couponNum, User user, int sort, LocalDateTime createdDate, int percentage,
			LocalDateTime usedDate, int status, String userId) {
		super();
		this.couponNum = couponNum;
		this.user = user;
		this.sort = sort;
		this.createdDate = createdDate;
		this.percentage = percentage;
		this.usedDate = usedDate;
		this.status = status;
		this.userId = userId;
	}
	
	public Coupon toEntity(User user) {
		LocalDateTime date = LocalDateTime.now();
		return Coupon.builder()
				.user(user)
				.sort(sort)
				.createdDate(date)
				.percentage(percentage)
				.status(status)
				.build();
	}


	
}
