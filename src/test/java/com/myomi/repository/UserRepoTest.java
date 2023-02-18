package com.myomi.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.myomi.user.entity.Membership;
//import com.myomi.user.entity.Point;
import com.myomi.user.entity.User;
import com.myomi.user.repository.UserRepository;

@SpringBootTest
class UserRepoTest {
//	private Logger logger = LoggerFactory.getLogger(getClass()); 
	@Autowired
	private UserRepository ur;
	
	@Test
	void testUserSave() {
		for(int i=6; i<=9; i++) {
			User user = new User();
			user.setId("id"+i);
			user.setPwd("비밀번호"+i);
			user.setName("유저이름"+i);
			user.setTel("010-"+(i*1000+i*100+i*10+i)+"-"+(i*1000+i*100+i*10+i));
			user.setEmail("이메일"+i+"@myomi.com");
			user.setAddr("한국 어딘가"+i);
			user.setRole(1);
			
			LocalDateTime date = LocalDateTime.now();
			user.setCreatedDate(date);
			
			Membership membership = new Membership();
			membership.setMembershipNum(1);
			membership.setMembershipLevel("브론즈");
			
//			user.setMembership(membership);
			
//			Point point = new Point();
//			point.setTotalPoint(2000);
			
//			user.setPoint(point);
			
			ur.save(user);
		}
	}
	
	
	@Test
	void testUserFindById() {
		Optional<User> optU = ur.findById("id6");
		assertTrue(optU.isPresent());
		String expectedName = "유저이름6";
		assertEquals(expectedName, optU.get().getName());
	}
	
//	@Test
//	void testUserUpdate() {
//		//회원 이메일, 이름, 비밀번호, 주소, 멤버십
//		Optional<User> optU = ur.findById("id1");
//		assertTrue(optU.isPresent());
//		Membership membership = new Membership();
//		membership.setMembershipNum(2);
//		membership.setMembershipLevel("실버");
//		
//		User user = new User();
//		user.setEmail("songeon@email.com");
//		user.setName("임성언");
//		user.setPwd("songsongsong");
//		user.setAddr("경기도 군포시 산본동");
//		user.setMembership(membership);
//		
//		ur.save(user);
//	}
}