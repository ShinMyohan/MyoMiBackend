package com.myomi.repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.myomi.admin.entity.Admin;
import com.myomi.admin.repository.AdminRepository;
import com.myomi.notice.entity.Notice;
import com.myomi.notice.repository.NoticeRepository;
@SpringBootTest
class AdminRepoTest {
	@Autowired
	private AdminRepository ar;
	@Autowired
	private NoticeRepository nr;
	private List<Notice>notices= new ArrayList<Notice>();
	@Test
	void testAdminSave() {
		Admin a = new Admin();
		a.setAdminId("admin2");
		a.setPwd("1234");
		Optional<Admin> optA = ar.findById("admin1");
		Notice n = new Notice();
		n.setNNum(2L);
		n.setAdmin(optA.get());
		n.setContent("제발되라");
		LocalDateTime date = LocalDateTime.now();
		n.setCreatedDate(date);
		n.setTitle("될거야");
		nr.save(n);
		notices.add(n);
		a.setNotices(notices);		
		ar.save(a);
		
}
}
