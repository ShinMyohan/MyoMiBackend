//package com.myomi.repository;
//
//import java.util.Optional;
//
//import org.junit.jupiter.api.Test;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import com.myomi.follow.entity.Follow;
//import com.myomi.follow.entity.FollowEmbedded;
//import com.myomi.follow.repository.FollowRepository;
//import com.myomi.seller.entity.Seller;
//import com.myomi.seller.repository.SellerRepository;
//import com.myomi.user.entity.User;
//import com.myomi.user.repository.UserRepository;
//
//@SpringBootTest
//class FollowRepositoryTest {
//	private Logger logger = LoggerFactory.getLogger(getClass());
//	
//	@Autowired
//	private FollowRepository fr;
//	
//	@Autowired
//	private SellerRepository sr;
//	
//	@Autowired
//	private UserRepository ur;
//	
//	//트리거 사용
//	@Test
//	void testFollowSave() {
//		Optional<User> optU = ur.findById("id8");
//		Optional<Seller> optS = sr.findById("id7");
//		Follow follow = new Follow();
//		follow.setUserId(optU.get());
//		follow.setSellerId(optS.get());
//		
//		fr.save(follow);
//		
//	}
//	
//	
//	
//	@Test
//	void testDelete() {
//		Optional<User> optU = ur.findById("id8");
//		Optional<Seller> optS = sr.findById("id7");
//		
//		FollowEmbedded fe = new FollowEmbedded();
//		fe.setUId(optU.get().getId());
//		fe.setSId(optS.get().getId());
//
//		fr.deleteById(fe);
//	}
//	
//	
//	
//	@Test
//	void testFindAll() {
//		Iterable<Follow> fs = fr.findAll();
//		fs.forEach(f ->{
//			logger.info("회원아이디:" + f.getUserId() + ", 셀러아이디:" + f.getSellerId());
//		});
//		
//	}
//	
//
//}
