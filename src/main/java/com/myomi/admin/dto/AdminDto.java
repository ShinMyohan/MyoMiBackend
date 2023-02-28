package com.myomi.admin.dto;

import java.util.List;

import com.myomi.notice.entity.Notice;

import lombok.Builder;

public class AdminDto {

	private String adminId;
	private String pwd;

	@Builder
	public AdminDto(String adminId, String pwd) {
		this.adminId = adminId;
		this.pwd = pwd;
	}
	
	
}
