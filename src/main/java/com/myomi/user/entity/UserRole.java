package com.myomi.user.entity;

//@Getter 
//@NoArgsConstructor
//@Entity
//@Table(name = "user_roles")
public enum UserRole {
	ROLE_ADMIN("USER"), ROLE_SELLER("SELLER"), ROLE_USER("일반사용자");
	
//	UserRole(String string) {
//		// TODO Auto-generated constructor stub
//	}
//
//	@Id
//	@Column(name = "user_id")
//	private String userId;
//	
//	@MapsId
//	@OneToOne
//	@JoinColumn(name = "user_id")
//	private User user;
//	
//	@OneToOne
//	@JoinColumn(name = "role")
//	private UserRole roles;
	
	private String description;
	
	UserRole(String description) {
		this.description = description;
	}
}
