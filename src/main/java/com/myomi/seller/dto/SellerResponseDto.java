package com.myomi.seller.dto;

import java.util.Date;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Getter@NoArgsConstructor
public class SellerResponseDto {
	private String sellerId;
	private String companyName;
	private int status;
	private Date signoutDate;
	
	@Builder
	public SellerResponseDto( String sellerId, String companyName,
			  int status,Date signoutDate) {
		this.sellerId = sellerId;
		this.companyName = companyName;
		this.status = status;
		this.signoutDate=signoutDate;
	}
	
	
}
