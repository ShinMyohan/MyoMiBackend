package com.myomi.admin.dto;

import java.util.Date;

import com.myomi.seller.entity.Seller;

import lombok.Getter;

@Getter
public class SellerDetailDto {
	
	private String id;
	private String companyName;
	private String companyNum;
	private String internetNum;
	private String addr;
	private String manager;
	private String bank_account;
	private int status;
	private Date signoutDate;
	
	public SellerDetailDto(Seller entity) {
		this.id = entity.getSellerId().getId();
		this.companyName = entity.getCompanyName();
		this.companyNum = entity.getCompanyNum();
		this.internetNum = entity.getInternetNum();
		this.addr = entity.getAddr();
		this.manager = entity.getManager();
		this.bank_account = entity.getBank_account();
		this.status = entity.getStatus();
		this.signoutDate=entity.getSellerId().getSignoutDate();
	}
}
