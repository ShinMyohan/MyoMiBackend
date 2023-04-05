//package com.myomi.repository;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//import java.time.LocalDateTime;
//import java.util.Optional;
//
////import org.apache.log4j.spi.LoggerFactory;
//import org.junit.jupiter.api.Test;
////import org.slf4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import com.myomi.admin.entity.Admin;
//import com.myomi.admin.repository.AdminRepository;
//import com.myomi.notice.entity.Notice;
//import com.myomi.notice.repository.NoticeRepository;
//
//@SpringBootTest
//class NoticeRepoTest {
//	// private Logger logger = LoggerFactory.getLogger(getClass());
//	@Autowired
//	private AdminRepository ar;
//	@Autowired
//	private NoticeRepository nr;
//
//	@Test
//	void testNoticeSave() {
//		Optional<Admin> optA = ar.findById("admin3");
//		Notice n = new Notice();
//		n.setAdmin(optA.get());
//		n.setNNum(1L);
//		LocalDateTime date = LocalDateTime.now();
//		n.setCreatedDate(date);
//		n.setTitle("공지함");
//		n.setContent("공지합니다.");
//		nr.save(n);
//	}
//	@Test
//	void testUpdateNotice() {
//		Optional<Notice> optN = nr.findById(1L);
//		Notice n = new Notice();
//		n.setNNum(optN.get().getNNum());
//		n.setContent("수정테스트");
//		n.setTitle("수정공지");
//		nr.save(n);
//		
//	}
//	@Test
//	void testNoticeFindById() {
//		Optional<Notice> optN = nr.findById(1L);
//		assertTrue(optN.isPresent());
//		String expectedContent ="수정테스트";
//		assertEquals(expectedContent, optN.get().getContent());
//	}
//	@Test
//	void testDeleteNotice() {
//		Optional<Notice> optN = nr.findById(1L);
//		nr.deleteById(optN.get().getNNum());
//	}
//
//}
