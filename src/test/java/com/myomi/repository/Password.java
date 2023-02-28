package com.myomi.repository;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.myomi.user.entity.User;
import com.myomi.user.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
class Password {
	@Autowired
	private UserRepository ur;
	
	PasswordEncoder passwordEncoder = new PasswordEncoder() {
		
		@Override
		public boolean matches(CharSequence rawPassword, String encodedPassword) {
			log.warn("mathches:" + rawPassword + " :  " + encodedPassword);
			if(rawPassword.toString().equals(encodedPassword)) {
				return true;
			} else {
				return false;
			}
		}
		
		@Override
		public String encode(CharSequence rawPassword) {
			log.warn("before encode:" + rawPassword);
			return rawPassword.toString();
		}
	};
	
	@Test
	void testPwdChack() {
//		String rawPassword = "password";
//        String encodedPassword = passwordEncoder.encode(rawPassword);
        Optional<User> user = ur.findById("id4");
//		
//		CharSequence pwd = "pwd4";
        String p = "pwd4";
        CharSequence pwd = passwordEncoder.encode(p);
		String ePwd = user.get().getPwd();
		CharSequence eP = passwordEncoder.encode(ePwd);
//		System.out.println("원래 비번" + pwd);
//		if(!passwordEncoder.matches(pwd, ePwd)) {
//			
//		};
//        assertThat()
//        assertThat(passwordEncoder.matches(rawPassword, encodedPassword), is(true));
	}

}
