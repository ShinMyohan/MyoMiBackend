package com.myomi.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.myomi.admin.entity.Admin;
import com.myomi.admin.repository.AdminRepository;
import com.myomi.notice.entity.Notice;
import com.myomi.notice.repository.NoticeRepository;

@SpringBootTest
class NoticeRepoTest {
	// private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private AdminRepository ar;
	@Autowired
	private NoticeRepository nr;

	@Test
	void testNoticeSave() {
		Optional<Admin> optA = ar.findById("admin1");
		Notice n = new Notice();
		n.setAdmin(optA.get());
		n.setNNum(1L);
		LocalDateTime date = LocalDateTime.now();
		n.setCreatedDate(date);
		n.setTitle("공지함");
		n.setContent("공지합니다.");
		nr.save(n);
	}

}
