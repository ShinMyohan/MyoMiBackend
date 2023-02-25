package com.myomi.user.service;

import java.time.LocalDateTime;
import java.util.Optional;

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

import com.myomi.jwt.dto.TokenDto;
import com.myomi.jwt.provider.JwtTokenProvider;
import com.myomi.membership.entity.Membership;
import com.myomi.user.dto.UserDto;
import com.myomi.user.dto.UserSignUpReqeustDto;
import com.myomi.user.entity.User;
import com.myomi.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
//@Transactional(readOnly = true) //true로 되어있다는건 commit을 안하겠다는거
//레이지된 애
@RequiredArgsConstructor
public class UserService {
	@Autowired
	private final UserRepository userRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private LocalDateTime date = LocalDateTime.now();
    
    @Autowired
    private final PasswordEncoder passwordEncoder;

//    private Logger logger = LoggerFactory.getLogger(getClass());
    
    /**
     * 1. 로그인 요청으로 들어온 ID, PWD 기반으로 Authentication 객체 생성
     * 2. authenticate() 메서드를 통해 요청된 Member에 대한 검증이 진행 => loadUserByUsername 메서드를 실행. 해당 메서드는 검증을 위한 유저 객체를 가져오는 부분으로써, 어떤 객체를 검증할 것인지에 대해 직접 구현
     * 3. 검증이 정상적으로 통과되었다면 인증된 Authentication객체를 기반으로 JWT 토큰을 생성
     */
    @Transactional
    public TokenDto login(String userId, String password) {
//    	User optU = userRepository.findById(userId)
//    			.orElseThrow(() -> new IllegalArgumentException());
    	
    	Optional<User> optU = userRepository.findById(userId);
    	log.info("입력한 id:" + userId + " 디비아이디: " + optU.get().getId());
    	log.info("입력한 비번:" + password + " 디비비번: " + optU.get().getPwd());
    	
    	if(!passwordEncoder.matches(password, optU.get().getPwd())) {
    		log.error("비밀번호 오류");
    		throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    	}
    	
    	//-----되는 코드 
    	// 1. Login ID/PW 를 기반으로 Authentication 객체 생성
        // 이때 authentication 는 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userId, optU.get().getPwd()); //들어온 raw한 패스워드를 인코딩해서 디비에 있는 인코딩 된 패스워드랑 비교했어야했다!!!!!!
//    	UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userId, passwordEncoder.encode(password));
    	log.info("authenticationToken: "+authenticationToken.getName());
        
        // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
        // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);// 
        log.info("authentication: "+authentication.getName());
        
        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenDto tokenDto = jwtTokenProvider.generateToken(authentication);
 
        log.info(tokenDto.getAccessToken());
        return tokenDto;
    }
    
    public String signup(UserSignUpReqeustDto userSignUpReqeustDto) {
    	
    	//휴대폰 번호가 이미 등록된 번호인지 확인 (이 메서드는 아래에 있습니다.)
    	boolean check = checkTelExists(userSignUpReqeustDto.getTel());
    	
    	if(check) { //번호가 이미 등록된 번호면 예외발생
    		throw new IllegalArgumentException("이미 사용중인 번호입니다.");
    	}
//    	Membership m = new Membership();
//    	m.setMNum(1);
//    	m.setMLevel("골드");
    	
//    	String encPwd = passwordEncoder.encode(userSignUpReqeustDto.getPwd());
//    	String encPwd = new BCryptPasswordEncoder().encode(userSignUpReqeustDto.getPwd());
    	String encPwd = userSignUpReqeustDto.getPwd();
//    	
//    	//User 객체 만들어서 save()
////    	User user = userRepository.save(userSignUpReqeustDto.toEntity(encPwd));
    	User user = User.builder()
    			.id(userSignUpReqeustDto.getId())
    			.pwd(encPwd)
				.role(0)
				.name(userSignUpReqeustDto.getName())
				.tel(userSignUpReqeustDto.getTel())
				.email(userSignUpReqeustDto.getEmail())
				.addr(userSignUpReqeustDto.getAddr())
				.createdDate(date)
//				.membership(m)
    			.build();

    	log.info("\n" + "아이디: "+user.getId() +" 패스워드: "+ user.getPwd() +" 유저 권한: "+ user.getRole() +" 유저이름: "+ user.getName() + "\n"
    			+" 유저 휴대폰번호: "+ user.getTel() +" 유저 이메일 "+ user.getEmail() +" 유저 주소: "+ user.getAddr() +" 유저 가입날짜: "+ user.getCreatedDate());

    	userRepository.save(user);
    	if(user != null) { //유저가 Null이 아니면 유저 id 반환
    		return user.getId();
    	}
    	throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
    
    //이미 등록된 휴대폰번호인지 확인 - 해당 메서드는 user repository에 선언되어있습니다.
    public boolean checkTelExists(String tel) {
    	return userRepository.existsUserByTel(tel);
    }
    
    // 회원정보 검색
    public ResponseEntity<UserDto> getUserInfo(Authentication user) {
    	String username = user.getName();
    	Optional<User> u = userRepository.findById(username);
    	
    	Membership m = new Membership();
    	m.setMNum(1);
    	m.setMLevel("골드");
    	
    	return new ResponseEntity<>(
    			UserDto.builder()
    				.id(username)
	    			.role(u.get().getRole())
	    			.name(u.get().getName())
	    			.tel(u.get().getTel())
	    			.email(u.get().getEmail())
	    			.addr(u.get().getAddr())
	    			.createdDate(u.get().getCreatedDate())
	    			.membership(m)
	    			.point(u.get().getPoint())
	    			.seller(u.get().getSeller())
	    			.build(), HttpStatus.OK);
    };
    
    public ResponseEntity<UserDto> updateUserInfo(UserDto userDto, Authentication user) {
//    	String username = user.getName();
//    	Optional<User> u = userRepository.findById(username);
    	
    	User u = User.builder()
				.id(user.getName())
				.pwd(userDto.getPwd())
    			.name(userDto.getName())
    			.tel(userDto.getTel())
    			.email(userDto.getEmail())
    			.addr(userDto.getAddr())
//    			.createdDate(u.get().getCreatedDate())
    			.build();
    	
    	userRepository.save(u);
    	return new ResponseEntity<>(HttpStatus.OK);
    }
}