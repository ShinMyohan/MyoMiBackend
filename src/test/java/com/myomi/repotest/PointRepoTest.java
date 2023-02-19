package com.myomi.repotest;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.myomi.point.entity.Point;
import com.myomi.point.repository.PointRepository;
import com.myomi.user.entity.Membership;
import com.myomi.user.entity.User;
import com.myomi.user.entity.UserRepository;

@SpringBootTest
class PointRepoTest {
//	private Logger logger = LoggerFactory.getLogger(getClass()); 
	@Autowired
	private UserRepository ur;
	
	@Autowired
	private PointRepository pr;
	
//	@Autowired
//	private PointDetailRepository pdr;
	
	@Test
	void testUserAndPointSave() {
		for(int i=1; i<=5; i++) {
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
//			
//			Point point = new Point();
//			PointDetail pd = new PointDetail();
//			pd.setUserId(user);
//			//pd.setCreatedDate(date);
//			pd.setAmount(2000);
//			pd.setSort(0);
//	
//			
//			point.setTotalPoint(2000);
//			point.setId("id"+i);
//			point.setUserId(user); //중요! point 쪽에서도, user 객체 넣어준뒤 setter => 왜냐하면 양방향이니까!
//			
//			pr.save(point);
//		    
//			user.setPoint(point); //중요! user 쪽에서도, point 객체 넣어준뒤 setter => 왜냐하면 양방향이니까!
//			
			ur.save(user);
		}
	}
	
	@Test
	void saveTest() {
		Optional<User> optU = ur.findById("id1");
		assertTrue(optU.isPresent());
//		User user = optU.get();
//		PointDetail pd = new PointDetail();
//		LocalDateTime date = LocalDateTime.now();
//		pd.setUserId(user);
//		pd.setCreatedDate(date);
//		pd.setAmount(2000);
//		pd.setSort(0);
//		
//		pdr.save(pd);
	
	}
//	@Test
//	void saveTestPoint() {
//		Optional<User> optU = ur.findById("id2");
//		assertTrue(optU.isPresent());
//		User user = optU.get();
//	//	User user = new User();
//		PointDetail pd = new PointDetail();
//		LocalDateTime date = LocalDateTime.now();
//		pd.setUserId(user);
//	//	pd.setCreatedDate(date);
//		pd.setAmount(2000);
//		pd.setSort(0);
//	    pd.setCreatedDate(date);		
//		pdr.save(pd);
//	
//	}
//	
}
	
	