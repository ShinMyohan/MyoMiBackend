package com.myomi.user.control;

import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myomi.user.dto.UserDto;
import com.myomi.user.dto.UserLoginRequestDto;
import com.myomi.user.dto.UserSignUpReqeustDto;
import com.myomi.user.service.CertificationSevice;
import com.myomi.user.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "회원")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
	@Autowired
	private final UserService userService;
	@Autowired
	private final CertificationSevice certificationSevice;
	
	@ApiOperation(value = "회원| 일반 로그인")
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody UserLoginRequestDto userLoginRequestDto) {
        String userId = userLoginRequestDto.getUserId();
        String password = userLoginRequestDto.getPassword();
        Map<String, Object> map = userService.login(userId, password);
        return map;
    }

    @ApiOperation(value = "사용자| 일반 회원가입")
    @PostMapping("/signup")
    public String signup(@RequestBody UserSignUpReqeustDto userSignUpReqeustDto) {
    	return userService.signup(userSignUpReqeustDto);
    }
    
    @ApiOperation(value = "사용자| 등록된 아이디 중복 체크")
    @PostMapping("/signup/check/{id}")
    public ResponseEntity<Boolean> checkIdDuplicate(@PathVariable String id) {
    	return ResponseEntity.ok(userService.checkIdExists(id));
    }
    
    @ApiOperation(value = "사용자| 등록된 휴대폰번호 중복 체크")
    @GetMapping("/signup/check/{tel}/exists")
    public ResponseEntity<Boolean> checkTelDuplicate(@PathVariable String tel) {
    	return ResponseEntity.ok(userService.checkTelExists(tel));
    }
    
    @ApiOperation(value = "회원| 내 정보 조회")
    @GetMapping("/info")
    public ResponseEntity<UserDto> userinfo(Authentication user) {
    	return userService.getUserInfo(user);
    }
    
    @ApiOperation(value = "회원| 내 정보 수정")
    @PutMapping("/modify")
    public ResponseEntity<UserDto> userUpdate(UserDto userDto, Authentication user) {
    	return userService.updateUserInfo(userDto, user);
    }
    
    @ApiOperation(value = "사용자| 휴대폰번호 본인인증")
    @PostMapping("/check/sendSMS")
	public String sendSMS(String phoneNumber) {
		Random rand = new Random();
		String numStr = "";
		for(int i=0; i<4; i++) {
			String ran = Integer.toString(rand.nextInt(10));
			numStr+=ran;
		}
		log.info("수신자번호: "+phoneNumber);
		log.info("인증번호: "+numStr);
		certificationSevice.certifiedPhoneNumber(phoneNumber, numStr);
		return numStr;
	}
    
    @ApiOperation(value = "관리자| 셀러 승인")
    @GetMapping("/seller/sendSMS")
	public void sendSMSToSeller(String phoneNumber) {
		log.info("수신자번호: "+phoneNumber);
//		log.info("인증번호: "+numStr);
		certificationSevice.certifiedSeller(phoneNumber);
	}
}
