package com.myomi.repository;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
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
class QnaRepoTest {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private UserRepository ur;
	
	@Autowired
	private ProductRepository pr;
	
	@Autowired
	private QnaRepository qr;
	
	@Test //성공
	@DisplayName("문의등록")
	void testQnaSave() {
		Optional<User> optU = ur.findById("id2");
		Optional<Product> optP = pr.findById(1L);
		
		Qna qna = new Qna();
		qna.setQNum(2);
		qna.setUserId(optU.get());
		qna.setProdNum(optP.get());
		LocalDateTime date = LocalDateTime.now();
		qna.setQueCreatedDate(date);
		qna.setQueTitle("에엥");
		qna.setQueContent("고등어 싫은데 삼치로 대체되나요");
		
		qr.save(qna);
	}
	
	@Test //성공
	@DisplayName("문의 찾아 삭제")
	void testDeleteQna() {
		Optional<Qna> optQ = qr.findById(1);
		assertTrue(optQ.isPresent());
		qr.deleteById(1);
	}
}
