package com.myomi.user.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.myomi.jwt.dto.TokenDto;
import com.myomi.jwt.provider.JwtTokenProvider;
import com.myomi.user.dto.UserSignUpReqeustDto;
import com.myomi.user.entity.User;
import com.myomi.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
//@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    
    private final BCryptPasswordEncoder encoder;
    
    /**
     * 1. 로그인 요청으로 들어온 ID, PWD 기반으로 Authentication 객체 생성
     * 2. authenticate() 메서드를 통해 요청된 Member에 대한 검증이 진행 => loadUserByUsername 메서드를 실행. 해당 메서드는 검증을 위한 유저 객체를 가져오는 부분으로써, 어떤 객체를 검증할 것인지에 대해 직접 구현
     * 3. 검증이 정상적으로 통과되었다면 인증된 Authentication객체를 기반으로 JWT 토큰을 생성
     */
    @Transactional
    public TokenDto login(String memberId, String password) {
        // 1. Login ID/PW 를 기반으로 Authentication 객체 생성
        // 이때 authentication 는 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(memberId, password);
 
        // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
        // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
 
        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenDto tokenDto = jwtTokenProvider.generateToken(authentication);
 
        return tokenDto;
    }
    
    public String signup(UserSignUpReqeustDto userSignUpReqeustDto) {
    	//휴대폰 번호가 이미 등록된 번호인지 확인 (이 메서드는 아래에 있습니다.)
    	boolean check = checkTelExists(userSignUpReqeustDto.getTel());
    	
    	if(check) { //번호가 이미 등록된 번호면 예외발생
    		throw new IllegalArgumentException("이미 사용중인 번호입니다.");
    	}
    	
    	//비번 난수화 풀기
    	String encPwd = encoder.encode(userSignUpReqeustDto.getPwd());
    	
    	//User 객체 만들어서 save()
    	User user = userRepository.save(userSignUpReqeustDto.toEntity(encPwd));
    	
    	if(user != null) { //유저가 Null이 아니면 유저 id 반환
    		return user.getId();
    	}
    	throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
    
    //이미 등록된 휴대폰번호인지 확인 - 해당 메서드는 user repository에 선언되어있습니다.
    public boolean checkTelExists(String tel) {
    	return userRepository.existsUserByTel(tel);
    }
}