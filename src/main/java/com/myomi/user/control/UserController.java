package com.myomi.user.control;

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

import com.myomi.jwt.dto.TokenDto;
import com.myomi.user.dto.UserDto;
import com.myomi.user.dto.UserLoginRequestDto;
import com.myomi.user.dto.UserSignUpReqeustDto;
import com.myomi.user.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
	@Autowired
	private final UserService userService;
	
    @PostMapping("/login")
    public TokenDto login(@RequestBody UserLoginRequestDto userLoginRequestDto) {
        String userId = userLoginRequestDto.getUserId();
        String password = userLoginRequestDto.getPassword();
        TokenDto tokenDto = userService.login(userId, password);
        return tokenDto;
    }
    
    @PostMapping("/test")
    public String test() {
    	return "sucess";
    }
    
    //22일 저녁 추가
    @PostMapping("/signup")
    public String signup(@RequestBody UserSignUpReqeustDto userSignUpReqeustDto) {
    	return userService.signup(userSignUpReqeustDto);
    }
    
    @GetMapping("/signup/check/{tel}/exists")
    public ResponseEntity<Boolean> checkTelDuplicate(@PathVariable String tel) {
    	return ResponseEntity.ok(userService.checkTelExists(tel));
    }
    
    @GetMapping("/info")
    public ResponseEntity<UserDto> userinfo(Authentication user) {
    	return userService.getUserInfo(user);
    }
    
    @PutMapping("/modify")
    public ResponseEntity<UserDto> userUpdate(UserDto userDto, Authentication user) {
    	return userService.updateUserInfo(userDto, user);
    }
}
