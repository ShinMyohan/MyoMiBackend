package com.myomi.seller.vo;

import com.myomi.user.vo.UserVo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SellerVo {
	private UserVo user;
	private String companyName;
	private String companyNum;
	private String internetNum;
	private String addr;
	private String manager;
	private int status;
	private int followCnt;
}
