//package com.myomi.repository;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//import java.time.LocalDateTime;
//import java.util.Optional;
//
//import javax.sound.sampled.Line;
//
//import org.junit.jupiter.api.Test;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import com.myomi.qna.entity.Qna;
//import com.myomi.qna.repository.QnaRepository;
//import com.myomi.user.entity.Product;
//import com.myomi.user.entity.User;
//import com.myomi.user.repository.ProductRepository;
//import com.myomi.user.repository.UserRepository;
//
//@SpringBootTest
//class QnaRepositoryTest {
//	private Logger logger = LoggerFactory.getLogger(getClass());
//	@Autowired
//	private QnaRepository qr;
//	
//	@Autowired
//	private UserRepository ur;
//	
//	@Autowired
//	private ProductRepository pr;
//
//	@Test
//	void testQnaSave() {
//		Optional<User> optU = ur.findById("id7");
//		Optional<Product> optP = pr.findById(1);
//		
//		Qna qna = new Qna();
//		
//		qna.setQNum(3);
//		qna.setUserId(optU.get());
//		qna.setProdNum(optP.get());	
//		qna.setQueTitle("문의드립니다222");
//		qna.setQueContent("배송언제되나여2222");
//		LocalDateTime date = LocalDateTime.now();
//		qna.setQueCreatedDate(date);
////		qna.setAnsContent("1년입니다");
////		
////		LocalDateTime date2 = LocalDateTime.now();
////		qna.setAnsCreatedDate(date2);
//		
//		qr.save(qna);
//		
//	}
//	
//	@Test
//	void testQnaFindById() {
//		Optional<Qna> optQ = qr.findById(1);
//		assertTrue(optQ.isPresent());
//		String expectedName = "유통기한언제까지에요";
//		assertEquals(expectedName,optQ.get().getQueContent());
//		
//	}
//	
//	@Test
//	void testQnaFindAll() {
//		Iterable<Qna> qnas = qr.findAll();
//		qnas.forEach(qna ->{
//			logger.info("질문자:" + qna.getUserId() + ", 상품번호:" + qna.getProdNum() + ", 문의제목:" + qna.getQueTitle());
//		});
//	}
//	
//
//
//}
