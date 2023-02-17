package com.myomi.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.myomi.order.entity.OrderDetail;
import com.myomi.order.entity.OrderDetailEmbedded;
import com.myomi.review.entity.BestReview;
import com.myomi.review.entity.Review;
import com.myomi.review.repository.BestReviewRepository;
import com.myomi.review.repository.ReviewRepository;
import com.myomi.user.entity.User;
import com.myomi.user.repository.UserRepository;

@SpringBootTest
class ReviewRepoTest {
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private UserRepository ur;
	
	@Autowired
	private ReviewRepository rr;
	
	@Autowired
	private BestReviewRepository brr;
	
//	@Autowired
//	private OrderRepository or;
	
	
//	@Test
//	void testReviewSave() {
//		for(int i=1; i<=2; i++) {
//			Optional<User> optU = ur.findById("id"+i);
//			
//			Review review = new Review();
//			review.setRNum(i*1L);
//			review.setUser(optU.get());
//			review.setSort(0);
//			review.setTitle(i+"후후후후 "+i);
//			review.setContent(i+"제발"+i);
//			LocalDateTime date = LocalDateTime.now();
//			review.setCreatedDate(date);
//			review.setStars(i);
//			review.setPNum(2L);
//			
//			rr.save(review);
//		}
//	}
	
//	@Test
//	void testBestReview() {
//		Optional<Review> optR = rr.findById(2L);
//		
//		BestReview br = new BestReview();
////		BestReviewId bri = new BestReviewId();
//		br.setRNum(optR.get().getRNum());
//		br.setPNum(optR.get().getPNum());
//		LocalDateTime date = LocalDateTime.now();
//		br.setCreatedDate(date);
////		bri.setRNum(br.getReview().getRNum());
////		bri.setPNum(br.getReview().getPNum());
//		
//		brr.save(br);
//	}
	
	@Test
	void testSaveReview() {
		Optional<User> optU = ur.findById("id7");
		OrderDetailEmbedded ode = new OrderDetailEmbedded();
		ode.setONum(1L);
		ode.setPNum(1L);
		OrderDetail od = new OrderDetail();
		od.setId(ode);
		
		Review r = new Review();
		r.setRNum(1L);
		r.setUser(optU.get());
		r.setSort(0);
		r.setTitle("짱입니다요");
		r.setContent("완전 맛있어요 최고");
		LocalDateTime date = LocalDateTime.now();
		r.setCreatedDate(date);
		r.setStars(2.5F);
		r.setOrderDetail(od);
		
		rr.save(r);
	}
	
	@Test
	void testBestReviewSave() {
		Optional<Review> optR = rr.findById(1L);
		
		BestReview br = new BestReview();
		br.setRNum(optR.get().getRNum());
		br.setReview(optR.get());
		LocalDateTime date = LocalDateTime.now();
		br.setCreatedDate(date);
		
		brr.save(br);
	}
}
