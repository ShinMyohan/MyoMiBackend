
package com.myomi.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.myomi.coupon.repository.CouponRepository;
import com.myomi.membership.entity.Membership;
import com.myomi.point.repository.PointRepository;
import com.myomi.user.entity.User;
import com.myomi.user.repository.UserRepository;

@SpringBootTest
class UserRepoTest {
	private Logger logger = LoggerFactory.getLogger(getClass()); 
	@Autowired
	private UserRepository ur;

	@Autowired
	private PointRepository pr;

	@Autowired
	private CouponRepository cr;

//	@Test //성공
//	@DisplayName("회원가입")
//	void testUserSave() {
//		for(int i=1; i<=7; i++) {
//			User user = User.builder()
//					.id("id"+i)
//			
////			user.setId("id"+i);
////			user.setPwd("비밀번호"+i);
////			user.setName("유저이름"+i);
////			user.setTel("010-"+(i*1000+i*100+i*10+i)+"-"+(i*1000+i*100+i*10+i));
////			user.setEmail("이메일"+i+"@myomi.com");
////			user.setAddr("한국 어딘가"+i);
////			user.setRole(0);
//			
//			LocalDateTime date = LocalDateTime.now();
////			user.setCreatedDate(date);

//			Membership membership = new Membership();
//			membership.setMNum(1);
//			membership.setMLevel("브론즈");
//			
//			user.setMembership(membership);
//			

////			Point point = new Point();
////			point.setTotalPoint(2000);
////			point.setId("id"+i);
////			point.setUserId(user); //중요! point 쪽에서도, user 객체 넣어준뒤 setter => 왜냐하면 양방향이니까!
////			
////			pr.save(point);
//			
////			Coupon coupon = new Coupon();
////			coupon.setCpNum(i*1L);
////			coupon.setUser(user);
////			coupon.setSort(0);
////			coupon.setPercentage(5);
////			coupon.setCreatedDate(date);
////			coupon.setStatus(0);
////			
////			cr.save(coupon);
//			
////			user.setPoint(point); //중요! user 쪽에서도, point 객체 넣어준뒤 setter => 왜냐하면 양방향이니까!
//			
//			ur.save(user);
//		}
//	}
	
	@Test
	void testSaveOneUser() {
		LocalDateTime date = LocalDateTime.now();
		Membership membership = new Membership();
		membership.setMNum(1);
		membership.setMLevel("브론즈");
		User user = User.builder()
				.id("id2")
				.pwd("2222")
				.name("유저2")
				.tel("010-2222-2222")
				.email("222@gamil.com")
				.addr("군포시 어딘가22")
				.role(0)
				.createdDate(date)
				.membership(membership)
				.build();
		ur.save(user);
	}
//	@Test
//	void testUserFindById() {
//		Optional<User> optU = ur.findById("id1");
//		assertTrue(optU.isPresent());
//		String expectedName = "유저이름1";
//		assertEquals(expectedName, optU.get().getName());
//	}

//	@Test
//	void testUserTel() {
//		boolean optU = ur.existsUserByTel("010-9999-9999");
//		logger.info("있으면 true - 없으면 false -> " + optU);
//		//있으면 true - 없으면 false
//	}
	
//	@Test //성공
//	@DisplayName("회원가입 트리거 테스트")
//	void testUserTrgSave() {
////		User user = new User();
////		user.setId("id1");
////		user.setPwd("비밀번호1");
////		user.setName("유저이름1");
////		user.setTel("010-1111-1111");
////		user.setEmail("이메일1@myomi.com");
////		user.setAddr("한국 어딘가1");
////		user.setRole(0);
//		Optional<User> optU = ur.findById("id1");
//		User user = optU.get();
//		LocalDateTime date = LocalDateTime.now();
//		user.setCreatedDate(date);
//		
//		Membership membership = new Membership();
//		membership.setMNum(1);
//		membership.setMLevel("골드");
//		
//		user.setMembership(membership);
//		
//		ur.save(user);
//	}
	
//	@Test
//	void testUserUpdate() {
//		//회원 이메일, 이름, 비밀번호, 주소, 멤버십
//		Optional<User> optU = ur.findById("id1");
//		assertTrue(optU.isPresent());
//		Membership membership = new Membership();
//		membership.setMNum(1);
//		membership.setMLevel("골드");
//		
//		User user = optU.get();
//		user.setMembership(membership);

//		user.setRole(1);

//
//		ur.save(user);
//	}
//	
//	@Test
//	void testDelUser() {
//		Optional<User> optU = ur.findById("id1");
//		
//		ur.delete(optU.get());
//	}
//	PasswordEncoder encoder = new PasswordEncoder() {
//		
//		@Override
//		public boolean matches(CharSequence rawPassword, String encodedPassword) {
//			if(rawPassword.equals(encodedPassword)) {
//				return true;
//			} else {
//				return false;
//			}
//		}
//		
//		@Override
//		public String encode(CharSequence rawPassword) {
//			return null;
//		}
//	};
//	
//	@Test
//	void testCheckPwd() {
//		Optional<User> user = ur.findById("id4");
//		
//		CharSequence pwd = "pwd4";
//		String ePwd = user.get().getPwd();
//		
//		if(encoder.matches(pwd, ePwd)) {
//			
//		}
//		System.out.println(ePwd);        
//	    System.out.println(encoder.matches(pwd, ePwd));
//	}
	
//	public class PasswordEncoderTest {
//		 
//	    @Autowired
//	    private PasswordEncoder passwordEncoder;
//	 
//	    @Test
//	    public void test() {
//	        String rawPassword = "password";
//	        String encodedPassword = passwordEncoder.encode(rawPassword);
////	        assertThat(passwordEncoder.matches(rawPassword, encodedPassword), is(true));
//	        assertThat(passwordEncoder.matches(rawPassword, encodedPassword), is(true));
//	    }
//
//		private void assertThat(boolean matches, Matcher<Boolean> matcher) {
//		}
//	}
}