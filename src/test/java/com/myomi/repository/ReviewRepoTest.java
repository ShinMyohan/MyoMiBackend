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
//import com.myomi.review.entity.BestReview;
import com.myomi.review.entity.Review;
import com.myomi.review.repository.BestReviewRepository;
import com.myomi.review.repository.ReviewRepository;
import com.myomi.user.entity.User;
import com.myomi.user.repository.UserRepository;

@SpringBootTest
class ReviewRepoTest {
	private Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private UserRepository ur;
	@Autowired
	private ReviewRepository rr;
	@Autowired
	private BestReviewRepository brr;

	@Test
	void reviewSaveTest() {
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
	void BestReviewSaveTest() {
		Optional<Review> optR = rr.findById(1L);
		BestReview b = new BestReview();
		b.setRNum(optR.get().getRNum());
		b.setReview(optR.get());
		LocalDateTime date = LocalDateTime.now();
		b.setCreatedDate(date);
		brr.save(b);

	}

}
