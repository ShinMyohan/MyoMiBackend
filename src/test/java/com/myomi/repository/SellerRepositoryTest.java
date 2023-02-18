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
		Optional<User> optU = ur.findById("id6");

		Seller seller = new Seller();
		seller.setId("id6");
		seller.setSellerId(optU.get());
		seller.setCompanyName("미미네 도시락");
		seller.setCompanyNum("03호38572");
		seller.setInternetNum("29허하485너");
		seller.setManager("장세리");
		seller.setStatus(1);
		
		sr.save(seller);
	}
	
	@Test
	void testSellerFindById() {
		Optional<Seller> optS = sr.findById("id6");
		assertTrue(optS.isPresent());
		String expectedName = "미미네 도시락";
		assertEquals(expectedName, optS.get().getCompanyName());
		
	}
	
	
}