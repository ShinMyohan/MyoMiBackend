package com.myomi.user.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter 
@RequiredArgsConstructor
public enum UserRole {
	ROLE_ADMIN("USER"), ROLE_SELLER("SELLER"), ROLE_USER("일반사용자");
	
	private String description;
	
	UserRole(String description) {
		this.description = description;
	}
}
