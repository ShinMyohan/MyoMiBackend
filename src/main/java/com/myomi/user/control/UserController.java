package com.myomi.user.control;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myomi.jwt.dto.TokenDto;
import com.myomi.user.dto.UserLoginRequestDto;
import com.myomi.user.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
	private final UserService userService;
	 
    @PostMapping("/login")
    public TokenDto login(@RequestBody UserLoginRequestDto userLoginRequestDto) {
        String userId = userLoginRequestDto.getUserId();
        String password = userLoginRequestDto.getPassword();
        TokenDto tokenDto = userService.login(userId, password); //포스트맨에서 이걸로 넣어야함
        return tokenDto;
    }
    
    @PostMapping("/test")
    public String test() {
    	return "sucess";
    }
}
