package com.myomi.seller.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SellerDto {
	private String id;
	private String companyName;
	private String companyNum;
	private String internetNum;
	private String addr;
	private String manager;
	private int status;
	private Long followCnt;
//	private List<Follow> follows;
	
	@Builder
	public SellerDto(String id, String companyName, String companyNum, String internetNum,
			String addr, String manager, int status, Long followCnt) {
		this.id = id;
		this.companyName = companyName;
		this.companyNum = companyNum;
		this.internetNum = internetNum;
		this.addr = addr;
		this.manager = manager;
		this.status = status;
		this.followCnt = followCnt;
	}
}
