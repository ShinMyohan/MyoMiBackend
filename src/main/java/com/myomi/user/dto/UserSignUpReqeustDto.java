package com.myomi.user.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.myomi.user.entity.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor

public class UserSignUpReqeustDto {
	private final LocalDateTime date = LocalDateTime.now(); 
	
	@NotBlank(message = "사용자 아이디는 필수 입력 값입니다.")
	@Size(min = 4, max = 10,  message = "로그인 아이디는 최소 4자리에서 10자리 입니다.")
	private String id;
	
	@NotBlank(message = "비밀번호는 필수 입력 값입니다.")
	@Size(min = 4, max = 10,  message = "비밀번호는 최소 4자리에서 10자리 입니다.")
	private String pwd;
	
	@NotBlank(message = "이름은 필수 입력 값입니다.")
	@Size(min = 1, max = 10, message = "최소 1자에서 10자까지 입력할 수 있습니다.")
	private String name;
	
	@NotBlank(message = "번호는 필수 입력 값입니다.")
	@Size(min = 9, max = 15, message = "휴대폰 본인인증을 해주세요.")
	private String tel;
	
	@Size(max = 20, message = "주문정보가 이메일로 발송됩니다. 정확히 입력해주세요.")
	@Email
	private String email;
	
	@Size(max = 60, message = "기본 배송지를 입력해주세요.")
	private String addr;
	
//	@Builder
	public User toEntity(String encPwd) {
		return User.builder()
				.id(id)
				.pwd(encPwd)
				.name(name)
				.tel(tel)
				.email(email)
				.addr(addr)
				.createdDate(date)
				.role(0)
				.build();
	}
}
