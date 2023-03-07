package com.myomi.seller.dto;

import com.myomi.user.entity.User;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SellerReadResponseDto {
	private String sellerId;
	private String companyName;
	private String companyNum;
	private String internetNum;
	private String addr;
	private String manager;
	private String bankAccount;
	private int status;
	
	@Builder
	public SellerReadResponseDto(String sellerId, String companyName, String companyNum, String internetNum, String addr,
			String manager, String bankAccount, int status) {
		this.sellerId = sellerId;
		this.companyName = companyName;
		this.companyNum = companyNum;
		this.internetNum = internetNum;
		this.addr = addr;
		this.manager = manager;
		this.bankAccount = bankAccount;
		this.status = status;
	}
}
