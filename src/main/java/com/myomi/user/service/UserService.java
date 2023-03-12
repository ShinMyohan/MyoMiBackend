package com.myomi.user.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.myomi.common.status.ErrorCode;
import com.myomi.common.status.NoResourceException;
import com.myomi.jwt.dto.TokenDto;
import com.myomi.jwt.provider.JwtTokenProvider;
import com.myomi.membership.entity.MembershipLevel;
import com.myomi.user.dto.UserDto;
import com.myomi.user.dto.UserSignUpReqeustDto;
import com.myomi.user.entity.User;
import com.myomi.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
	@Autowired
	private final UserRepository userRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private LocalDateTime date = LocalDateTime.now();
    
    @Autowired
    private final PasswordEncoder passwordEncoder;
    
    /**
     * 1. 로그인 요청으로 들어온 ID, PWD 기반으로 Authentication 객체 생성
     * 2. authenticate() 메서드를 통해 요청된 Member에 대한 검증이 진행 => loadUserByUsername 메서드를 실행. 해당 메서드는 검증을 위한 유저 객체를 가져오는 부분으로써, 어떤 객체를 검증할 것인지에 대해 직접 구현
     * 3. 검증이 정상적으로 통과되었다면 인증된 Authentication객체를 기반으로 JWT 토큰을 생성
     */
    @Transactional
    public Map<String, Object> login(String userId, String password) {
    	User optU = userRepository.findById(userId)
    			.orElseThrow(()-> new NoResourceException(ErrorCode.BAD_REQUEST, "NOT_FOUND_USER"));
//    	log.info("입력한 id:" + userId + " 디비아이디: " + optU.getId());
//    	log.info("입력한 비번:" + password + " 디비비번: " + optU.getPwd());
    	
    	if(!passwordEncoder.matches(password, optU.getPwd())) {
    		log.error("비밀번호 오류");
    		throw new NoResourceException(ErrorCode.BAD_REQUEST, "DISCORD_PASSWORD");
    	}
    	
    	// 1. Login ID/PW 를 기반으로 Authentication 객체 생성
        // 이때 authentication 는 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userId, optU.getPwd());
    	log.info("authenticationToken: "+authenticationToken.getName());
        
        // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
        // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);// 
        log.info("authentication: "+authentication.getName());
        
        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenDto tokenDto = jwtTokenProvider.generateToken(authentication);
        Map map = new HashMap<>();
        map.put("token", tokenDto);
        map.put("userName", optU.getName());
        map.put("userRole", optU.getRole());
        log.info(tokenDto.getAccessToken());
        return map;
    }
    
    //회원가입
    public String signup(UserSignUpReqeustDto userSignUpReqeustDto) {
    	//휴대폰 번호가 이미 등록된 번호인지 확인 (이 메서드는 아래에 있습니다.)
    	boolean checkTel = checkTelExists(userSignUpReqeustDto.getTel());
    	if(checkTel) { //번호가 이미 등록된 번호면 예외발생
    		throw new IllegalArgumentException("이미 사용중인 번호입니다.");
    	}
    	
    	boolean checkId = checkIdExists(userSignUpReqeustDto.getId());
    	if(checkId) { //아이디가 이미 등록된 아이디면 예외발생
    		throw new IllegalArgumentException("이미 사용중인 아이디입니다.");
    	}
    	
    	String encPwd = passwordEncoder.encode(userSignUpReqeustDto.getPwd());

    	User user = User.builder()
    			.id(userSignUpReqeustDto.getId())
    			.pwd(encPwd)
				.role(0)
				.name(userSignUpReqeustDto.getName())
				.tel(userSignUpReqeustDto.getTel())
				.email(userSignUpReqeustDto.getEmail())
				.addr(userSignUpReqeustDto.getAddr())
				.createdDate(date)
				.membership(MembershipLevel.NEW)
    			.build();

    	userRepository.save(user);
    	if(user != null) { //유저가 Null이 아니면 유저 id 반환
    		return user.getId();
    	}
    	throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
    
    public ResponseEntity<?> checkIdDup(String id) {
    	boolean checkId = checkIdExists(id);
    	if(checkId) { //아이디가 이미 등록된 아이디면 예외발생
    		throw new IllegalArgumentException("이미 사용중인 아이디입니다.");
    	}
    	return new ResponseEntity<>(HttpStatus.OK);
    }
    
    // 회원정보 검색
    public ResponseEntity<UserDto> getUserInfo(Authentication user) {
    	String username = user.getName();
    	User u = userRepository.findById(username)
    			.orElseThrow(()->new NoResourceException(ErrorCode.RESOURCE_NOT_FOUND, "NOT_FOUND_USER"));

    	return new ResponseEntity<>(
    			UserDto.builder()
    				.id(username)
	    			.role(u.getRole())
	    			.name(u.getName())
	    			.tel(u.getTel())
	    			.email(u.getEmail())
	    			.addr(u.getAddr())
	    			.createdDate(u.getCreatedDate())
	    			.membership(u.getMembership())
	    			.point(u.getPoint())
	    			.seller(u.getSeller())
	    			.build(), HttpStatus.OK);
    };
    
    //회원 정보 수정
    public ResponseEntity<UserDto> updateUserInfo(UserDto userDto, Authentication user) {
    	User u = User.builder()
				.id(user.getName())
				.pwd(userDto.getPwd())
    			.name(userDto.getName())
    			.tel(userDto.getTel())
    			.email(userDto.getEmail())
    			.addr(userDto.getAddr())
    			.build();
    	
    	userRepository.save(u);
    	return new ResponseEntity<>(HttpStatus.OK);
    }
    
    //----- 중복 방지를 위한 메서드 ----
    //이미 등혹된 아이디인지 확인
    public boolean checkIdExists(String id) {
    	return userRepository.existsUserById(id);
    }
    
    //이미 등록된 휴대폰번호인지 확인 - 해당 메서드는 user repository에 선언되어있습니다.
    public boolean checkTelExists(String tel) {
    	return userRepository.existsUserByTel(tel);
    }
}