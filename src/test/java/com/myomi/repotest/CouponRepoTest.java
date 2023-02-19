package com.myomi.repotest;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.myomi.coupon.entity.Coupon;
import com.myomi.coupon.repository.CouponRepository;
import com.myomi.user.entity.User;
import com.myomi.user.entity.UserRepository;

@SpringBootTest
class CouponRepoTest {
    @Autowired
    private CouponRepository cr;
    
    @Autowired
    private UserRepository ur;
    
	
	
	@Test //성공 
	void CouponSaveTest() {
		Optional<User> optU = ur.findById("id2");
		assertTrue(optU.isPresent());
		LocalDateTime date = LocalDateTime.now();
		User user = optU.get();
		Coupon cp = new Coupon ();
		cp.setCpNum(1);
		cp.setUser(user);
		cp.setSort(1);
		cp.setPercentage(10);
		cp.setCreatedDate(date);
        cp.setStatus(1);
        
        cr.save(cp);
	}
}
