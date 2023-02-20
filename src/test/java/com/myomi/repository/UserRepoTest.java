package com.myomi.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.myomi.coupon.entity.Coupon;
import com.myomi.coupon.repository.CouponRepository;
import com.myomi.point.entity.Point;
import com.myomi.point.repository.PointRepository;
import com.myomi.user.entity.Membership;
import com.myomi.user.entity.User;
import com.myomi.user.repository.UserRepository;

@SpringBootTest
class UserRepoTest {
//	private Logger logger = LoggerFactory.getLogger(getClass()); 
	@Autowired
	private UserRepository ur;
	
	@Autowired
	private PointRepository pr;
	
	@Autowired
	private CouponRepository cr;
	
	@Test //성공
	@DisplayName("회원가입")
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
			
			LocalDateTime date = LocalDateTime.now();
			user.setCreatedDate(date);
			
			Membership membership = new Membership();
			membership.setMembershipNum(1);
			membership.setMembershipLevel("브론즈");
			
			user.setMembership(membership);
			
			Point point = new Point();
			point.setTotalPoint(2000);
			point.setId("id"+i);
			point.setUserId(user); //중요! point 쪽에서도, user 객체 넣어준뒤 setter => 왜냐하면 양방향이니까!
			
			pr.save(point);
			
			Coupon coupon = new Coupon();
			coupon.setCpNum(i);
			coupon.setUser(user);
			coupon.setSort(0);
			coupon.setPercentage(5);
			coupon.setCreatedDate(date);
			coupon.setStatus(0);
			
			cr.save(coupon);
			
			user.setPoint(point); //중요! user 쪽에서도, point 객체 넣어준뒤 setter => 왜냐하면 양방향이니까!
			
			ur.save(user);
		}
	}
	
	
	@Test
	void testUserFindById() {
		Optional<User> optU = ur.findById("id1");
		assertTrue(optU.isPresent());
		String expectedName = "유저이름1";
		assertEquals(expectedName, optU.get().getName());
	}
	
	@Test //성공
	@DisplayName("회원가입 트리거 테스트")
	void testUserTrgSave() {
		User user = new User();
		user.setId("id1");
		user.setPwd("비밀번호1");
		user.setName("유저이름1");
		user.setTel("010-1111-1111");
		user.setEmail("이메일1@myomi.com");
		user.setAddr("한국 어딘가1");
		user.setRole(0);
		
		LocalDateTime date = LocalDateTime.now();
		user.setCreatedDate(date);
		
		Membership membership = new Membership();
		membership.setMembershipNum(1);
		membership.setMembershipLevel("브론즈");
		
		user.setMembership(membership);
		
		ur.save(user);
	}
	
	@Test
	void testUserUpdate() {
		//회원 이메일, 이름, 비밀번호, 주소, 멤버십
		Optional<User> optU = ur.findById("id1");
		assertTrue(optU.isPresent());
		Membership membership = new Membership();
		membership.setMembershipNum(1);
		membership.setMembershipLevel("골드");
		
		User user = optU.get();
		user.setMembership(membership);
		user.setRole(1);
		
		ur.save(user);
	}
	
	@Test
	void testDelUser() {
		Optional<User> optU = ur.findById("id1");
		
		ur.delete(optU.get());
	}
}
