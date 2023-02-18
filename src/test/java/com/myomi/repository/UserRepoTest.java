package com.myomi.repository;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.myomi.user.entity.Membership;
import com.myomi.user.entity.User;
import com.myomi.user.repository.UserRepository;

@SpringBootTest
class UserRepoTest {
	//private Logger logger = LoggerFactory.getLogger(getClass()); 
	@Autowired
	private UserRepository ur;
	
	@Test
	void testUserSave() {
		
		for(int i=1; i<=7; i++) {
			User user = new User();
			user.setId("id"+i);
			user.setPwd("비밀번호"+i);
			user.setName("유저이름"+i);
			user.setTel("010-"+(i*1000+i*100+i*10+i)+"-"+(i*1000+i*100+i*10+i));
			user.setEmail("이메일"+i+"@myomi.com");
			user.setAddr("한국 어딘가"+i);
			user.setRole(1);
			
			Membership membership = new Membership();
			membership.setMembershipNum(1);
			membership.setMembershipLevel("브론즈");
			
			user.setMembership(membership);
			
			ur.save(user);
		}
		System.out.println(ur);
	}

}
