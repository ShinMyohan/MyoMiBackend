package com.myomi.admin.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Getter
@NoArgsConstructor
@Data
public class AdminDto {

	private String adminId;
	private String pwd;

	@Builder
	public AdminDto(String adminId, String pwd) {
		this.adminId = adminId;
		this.pwd = pwd;
	}

}
