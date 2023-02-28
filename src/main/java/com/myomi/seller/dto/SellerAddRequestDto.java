package com.myomi.seller.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.myomi.user.entity.User;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter 
@NoArgsConstructor
public class SellerAddRequestDto {
	@JsonIgnore
	private User sellerId;
	private String companyName;
	private String companyNum;
	private String internetNum;
	private String addr;
	private String manager;
	private String bankAccount;
	
	@Builder
	public SellerAddRequestDto(User sellerId, String companyName, String companyNum, String internetNum, String addr,
			String manager, String bankAccount) {
		this.sellerId = sellerId;
		this.companyName = companyName;
		this.companyNum = companyNum;
		this.internetNum = internetNum;
		this.addr = addr;
		this.manager = manager;
		this.bankAccount = bankAccount;
	}
	
	
	

}
