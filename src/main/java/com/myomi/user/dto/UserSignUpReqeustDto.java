package com.myomi.user.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.myomi.membership.entity.Membership;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserSignUpReqeustDto {
	private LocalDateTime date = LocalDateTime.now(); 
	private Membership m = new Membership();
	
	@NotBlank(message = "사용자 아이디는 필수 입력 값입니다.")
	@Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{5,30}$", message = "아이디는 특수문자를 제외한 5자이상이여야 합니다")
	private String id;
	
	@NotBlank(message = "비밀번호는 필수 입력 값입니다.")
	@Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z]).{8,16}", message = "최소 하나의 문자 및 숫자를 포함한 8~16자이여야 합니다")
	private String pwd;
	
	@NotBlank(message = "이름은 필수 입력 값입니다.")
	@Size(min = 1, max = 10, message = "최소 1자에서 10자까지 입력할 수 있습니다.")
	@Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z]{2,30}$")
	private String name;
	
	@NotBlank(message = "번호는 필수 입력 값입니다.")
	@Pattern(regexp = "^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$", message = "휴대폰 본인인증을 해주세요.")
	private String tel;
	
	@Size(max = 20, message = "주문정보가 이메일로 발송됩니다. 정확히 입력해주세요.")
	@Pattern(regexp = "^(?:\\w+\\.?)*\\w+@(?:\\w+\\.)+\\w+$")
	@Email
	private String email;
	
	@Size(max = 60, message = "기본 배송지를 입력해주세요.")
	private String addr;
	
//	@Builder
//	public User toEntity(String encPwd) {
//		return User.builder()
//				.id(id)
//				.pwd(encPwd)
//				.role(UserRole.ROLE_USER)
//				.name(name)
//				.tel(tel)
//				.email(email)
//				.addr(addr)
//				.createdDate(date)
//				.build();
//	}
}