package com.myomi.repository;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.myomi.follow.entity.Follow;
import com.myomi.follow.repository.FollowRepository;
import com.myomi.seller.entity.Seller;
import com.myomi.seller.repository.SellerRepository;
import com.myomi.user.entity.User;
import com.myomi.user.repository.UserRepository;

@SpringBootTest
class FollowRepoTest {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private FollowRepository fr;
	
	@Autowired
	private UserRepository ur;
	
	@Autowired
	private SellerRepository sr;
	
	@Test //성공
	@DisplayName("셀러 팔로우하기")
	void testFollowSave() {
		Optional<User> optU = ur.findById("id4");
		Optional<Seller> optS = sr.findById("id3");
		
		
		Follow follow = new Follow();
		follow.setSellerId(optS.get());
		follow.setUserId(optU.get());
		//셀러 팔로우 수 칼럼 + 1
		Seller seller = optS.get();
		seller.setFollowCnt(seller.getFollowCnt()+1);
		sr.save(seller); //팔로우수 증가한거 update를 위한 save
		
		fr.save(follow);
	}
	
//	@Test
//	void testFollowsByUser() {
//		Iterable<Follow> follows = fr.findByUserId("id2");
//		follows.forEach(follow -> { 
//			logger.follow("회원 : "+ follow.getUserId().getId(), "팔로우한 셀러: " + follow.getSellerId().getId());
//		});
//	}
}
