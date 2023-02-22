package com.myomi.user.control;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myomi.jwt.dto.TokenDto;
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
	private final UserService userService;
	
	private final BCryptPasswordEncoder passwordEncoder;
	 
    @PostMapping("/login")
    public TokenDto login(@RequestBody UserLoginRequestDto userLoginRequestDto) {
        String userId = userLoginRequestDto.getUserId();
        String password = userLoginRequestDto.getPassword();
        TokenDto tokenDto = userService.login(userId, password); //포스트맨에서 이걸로 넣어야함
        return tokenDto;
    }

    @PostMapping("/signup")
    public String signup(@RequestBody UserSignUpReqeustDto userSignUpReqeustDto) {
    	return userService.signup(userSignUpReqeustDto);
    }
    
    @GetMapping("/signup/check/{tel}/exists")
    public ResponseEntity<Boolean> checkTelDuplicate(@PathVariable String tel) {
    	return ResponseEntity.ok(userService.checkTelExists(tel));
    }
    
    @PostMapping("/test")
    public String test() {
    	return "sucess";
    }
}
