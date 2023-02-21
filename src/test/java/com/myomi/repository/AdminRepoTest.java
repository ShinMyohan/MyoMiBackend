package com.myomi.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
	//private List<ChatRoom>rooms= new ArrayList<ChatRoom>();
	@Test
	void testAdminSave() {
		Admin a = new Admin();
		a.setAdminId("admin3");
		a.setPwd("1234");
		a.setNotices(notices);
		//a.setRooms(rooms);
		ar.save(a);
		
}
	@Test
	void testAdminFindById() {
		Optional<Admin> optA = ar.findById("admin1");
		assertTrue(optA.isPresent());
		String expectedPwd = "1234";
		assertEquals(expectedPwd, optA.get().getPwd());
	}
	
	
}
