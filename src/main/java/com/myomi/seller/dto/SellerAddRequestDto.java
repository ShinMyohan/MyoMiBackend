package com.myomi.seller.dto;

import org.springframework.web.multipart.MultipartFile;

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
	private MultipartFile file1;
	private MultipartFile file2;
	private String companyImgUrl;
	private String internetImgUrl;
	
	@Builder
	public SellerAddRequestDto(User sellerId, String companyName, String companyNum, String internetNum, String addr,
			String manager, String bankAccount,MultipartFile file1,MultipartFile file2,String companyImgUrl,String internetImgUrl) {
		this.sellerId = sellerId;
		this.companyName = companyName;
		this.companyNum = companyNum;
		this.internetNum = internetNum;
		this.addr = addr;
		this.manager = manager;
		this.bankAccount = bankAccount;
		this.file1 = file1;
		this.file2 = file2;
		this.companyImgUrl = companyImgUrl;
		this.internetImgUrl = internetImgUrl;
	}
}
