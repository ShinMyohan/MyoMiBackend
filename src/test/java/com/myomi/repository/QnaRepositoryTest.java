package com.myomi.repository;

import com.myomi.product.entity.Product;
import com.myomi.product.repository.ProductRepository;
import com.myomi.qna.entity.Qna;
import com.myomi.qna.repository.QnaRepository;
import com.myomi.user.entity.User;
import com.myomi.user.repository.UserRepository;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.myomi.product.entity.Product;
import com.myomi.product.repository.ProductRepository;
import com.myomi.qna.entity.Qna;
import com.myomi.qna.repository.QnaRepository;
import com.myomi.user.entity.User;
import com.myomi.user.repository.UserRepository;


@SpringBootTest
class QnaRepositoryTest {
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private QnaRepository qr;
	
	@Autowired
	private UserRepository ur;
	
	@Autowired
	private ProductRepository pr;

//	@Test
//	void testQnaSave() {
//		Optional<User> optU = ur.findById("id7");
//		Optional<Product> optP = pr.findById(1L);
//
//		Qna qna = new Qna();
//
//		qna.setUserId(optU.get());
//		qna.setProdNum(optP.get());
//		qna.setQueTitle("문의드립니다44");
//		qna.setQueContent("배송언제되나여44");
//		LocalDateTime date = LocalDateTime.now();
//		qna.setQueCreatedDate(date);
//		qna.setAnsContent("2년입니다");
//
//		LocalDateTime date2 = LocalDateTime.now();
//		qna.setAnsCreatedDate(date2);
//
//		qr.save(qna);
//
//	}
//
//
//	@Test
//	void testQnaFindById() {
//		Optional<Qna> optQ = qr.findById(5L);
//		assertTrue(optQ.isPresent());
//		String expectedName = "배송언제되나여22";
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
//	@Test
//	void testUpdate() {
//		Optional<Qna> optQ = qr.findById(8L);
//		Optional<Product> optP = pr.findById(1L);
//		Optional<User> optU = ur.findById("id7");
//
//		assertTrue(optQ.isPresent());
//		Qna q = optQ.get();
//		q.setQNum(q.getQNum());
//		q.setUserId(optU.get());
//		q.setProdNum(optP.get());
//		q.setQueTitle("냐냐냐냐냐");
//		q.setQueContent("냐냐냐냐냐???");
//
//		qr.save(q);
//
//	}
//
//	@Test
//	void testDelete() {
//		Optional<Qna> optQ = qr.findById(5L);
//		assertTrue(optQ.isPresent());
//		Qna q = optQ.get();
//		qr.delete(q);
//
//	}
}