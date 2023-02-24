package com.myomi.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

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
class SellerRepositoryTest {
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private SellerRepository sr;
	
	@Autowired
	private UserRepository ur;
	
	@Test
	void testSellerSave() {
		Optional<User> optU = ur.findById("id1");

		Seller seller = new Seller();
		User user = optU.get();
		seller.setId(user.getId());
		seller.setSellerId(optU.get());
		seller.setCompanyName("태리네 붕어빵");
		seller.setCompanyNum("90호38512");
		seller.setInternetNum("30허하889너");
		seller.setAddr("경기도 용인시 보정동");
		seller.setManager("호호호");
//		seller.setBank_account("국민 234235-23523");
		sr.save(seller);
	}
	
	@Test
	void testSellerFindById() {
		Optional<Seller> optS = sr.findById("id6");
		assertTrue(optS.isPresent());
		String expectedName = "미미네 도시락";
		assertEquals(expectedName, optS.get().getCompanyName());
		
	}
	
	//셀러 승인완료시 회원role업데이트(트리거)
	@Test
	void testUpdate() {
		Optional<Seller> optS = sr.findById("id1");
		Seller seller = optS.get();
		seller.setStatus(1);
		sr.save(seller);
	}
	

	//셀러 탈퇴시 회원탈퇴일 업데이트(트리거)
	@Test
	void testSellOutUpdate() {
		Optional<Seller> optS = sr.findById("id2");
		Seller seller = optS.get();
		seller.setStatus(9);
		sr.save(seller);
	}
	
	
	@Test
	void testDelete() {
		Optional<Seller> optS = sr.findById("id7");
		assertTrue(optS.isPresent());
		Seller s = optS.get();
		sr.delete(s);
		
	}
	
	
}
