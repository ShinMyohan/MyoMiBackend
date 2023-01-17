package com.myomi.follow.vo;

import com.myomi.seller.vo.SellerVo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FollowVo {
	private String userId;
	private SellerVo seller;
}
