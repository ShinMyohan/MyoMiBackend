package com.myomi.repository;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.myomi.seller.entity.Seller;
import com.myomi.seller.repository.SellerRepository;
import com.myomi.user.entity.User;
import com.myomi.user.repository.UserRepository;

@SpringBootTest
class SellerRepoTest {
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private SellerRepository sr;
	
	@Autowired
	private UserRepository ur;
	
	@Test
	@DisplayName("판매자 신청")
	void testSellerSave() {
		Optional<User> optU = ur.findById("id1");

		Seller seller = new Seller();
		seller.setId("id1");
		seller.setSellerId(optU.get());
		seller.setCompanyName("미미네 도시락");
		seller.setCompanyNum("02호38572");
		seller.setInternetNum("29허하485너");
		seller.setFollowCnt(0);
		seller.setAddr("강원도 어딘가");
		seller.setManager("장세리");
		seller.setStatus(1);
		
		sr.save(seller);
	}
}
