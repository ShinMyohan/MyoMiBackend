package com.myomi.repotest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.myomi.coupon.entity.Coupon;
import com.myomi.coupon.repository.CouponRepository;
import com.myomi.user.entity.User;
import com.myomi.user.repository.UserRepository;

@SpringBootTest
class CouponRepoTest {
    @Autowired
    private CouponRepository cr;
    
    @Autowired
    private UserRepository ur;
    
	@Test //성공 
	void CouponSaveTest() {
		Optional<User> optU = ur.findById("id1");
		assertTrue(optU.isPresent());
		LocalDateTime date = LocalDateTime.now();
		User user = optU.get();
		Coupon cp = new Coupon();
		cp.setUser(user);
		cp.setSort(0);
		
		cp.setPercentage(10);
		cp.setCreatedDate(date);
        //cp.setStatus(0);
        
        cr.save(cp);
	}
	
	@Test
	void deleteTest() {
	Optional<Coupon> optC = cr.findById(3);
	assertTrue(optC.isPresent());
	String userId = optC.get().getUser().getId();
    assertEquals("id1", userId);
    Coupon c = optC.get();
    cr.delete(c);
}
	
	@Test
	void couponUpdateTest () {
		Optional<Coupon> optC = cr.findById(1);
		Optional<User> optU = ur.findById("id1");
		
		Coupon cp = new Coupon();
		LocalDateTime date = LocalDateTime.now();
		cp.setCpNum(optC.get().getCpNum());
		cp.setUser(optU.get());
		cp.setStatus(1); // status 1 : 사용됨 
		cp.setUsedDate(date);
		
		cr.save(cp);
	}
	
}

