//package com.myomi.repository;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//import java.time.LocalDateTime;
//import java.util.Optional;
//
//import org.junit.jupiter.api.Test;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import com.myomi.order.entity.Order;
//import com.myomi.order.entity.OrderDetail;
//import com.myomi.order.entity.OrderDetailEmbedded;
//import com.myomi.order.repository.OrderRepository;
//import com.myomi.product.repository.ProductRepository;
//import com.myomi.review.dto.ReviewSaveRequestDto;
//import com.myomi.review.entity.BestReview;
////import com.myomi.review.entity.BestReview;
//import com.myomi.review.entity.Review;
//import com.myomi.review.repository.BestReviewRepository;
//import com.myomi.review.repository.ReviewRepository;
//import com.myomi.user.entity.User;
//import com.myomi.user.repository.UserRepository;
//
//@SpringBootTest
//class ReviewRepoTest {
//	private Logger log = LoggerFactory.getLogger(getClass());
//
//	@Autowired
//	private UserRepository ur;
//	@Autowired
//	private ReviewRepository rr;
//	@Autowired
//	private BestReviewRepository brr;
//	@Autowired
//	private ProductRepository pr;
//	@Autowired
//	private OrderRepository or;
//	
//	@Test
//	void reviewSaveTest() {
//		ReviewSaveRequestDto reviewSaveDto = ReviewSaveRequestDto.builder()
//																.title("제발")
//																.content("이젠되라")
//																.stars(2.5F)
//																.build();
//		User user = User.builder()
//						.id("id7")
//						.build();
//		LocalDateTime date = LocalDateTime.now();
//		OrderDetailEmbedded ode = OrderDetailEmbedded.builder()
//								  .oNum(1L)
//								  .pNum(2L)
//								  .build();
//		OrderDetail od = OrderDetail.builder()
//						 .id(ode)
//						 .build();
//			Review review = Review.builder()
//							.rNum(2L)
//							.user(user)
//							.title(reviewSaveDto.getTitle())
//							.content(reviewSaveDto.getContent())
//							.sort(0)
//							.createdDate(date)
//							.stars(reviewSaveDto.getStars())
//							.orderDetail(od)
//							.build();
//			
//			rr.save(review);
//	}
//	
//	@Test
//  void testReviewFindById() {
//     Optional<Review> optR = rr.findById(1L);
//     assertTrue(optR.isPresent());
// 	String expectedContent = "수정테스트";
//	assertEquals(expectedContent, optR.get().getContent());
//  }
//	
//	@Test
//	void ReviewUpdateTest() {
//		Optional<Review> optR = rr.findById(1L);
//		Review r = new Review();
//		r.setContent("수정테스트");
//		r.setRNum(optR.get().getRNum());
//		r.setTitle("수정리뷰");
//		rr.save(r);
//	}
//	
//
//	@Test
//	void BestReviewSaveTest() {
//		Optional<Review> optR = rr.findById(3L);
//		BestReview b = new BestReview();
//		b.setRNum(optR.get().getRNum());
//		b.setReview(optR.get());
//		LocalDateTime date = LocalDateTime.now();
//		b.setCreatedDate(date);
//		brr.save(b);
//
//	}
//
//}
