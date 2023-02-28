

package com.myomi.repotest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.myomi.board.repository.BoardRepository;
import com.myomi.user.repository.UserRepository;

@SpringBootTest
class BoardRepositoryTest {
	@Autowired
	private BoardRepository br;

	@Autowired
	private UserRepository ur;

//	@Test  //통과
//	void BoardTestSave() {
//
//		Optional<User> optU = ur.findById("id1");
//		for (int i=1; i<=5; i++) {
//			Board board = new Board();
//			//	board.setBNum();
//			board.setUser(optU.get());
//			board.setCategory("잡담"+i);
//			board.setTitle("제목" + i);
//			board.setContent("내용" + i);
//			LocalDateTime date = LocalDateTime.now();
//			board.setCreatedDate(date);
//			//board.setHits(i);
//			br.save(board);
//		}
//	}
//
//	@Test
//	void saveTest() {
//		Optional<User> optU = ur.findById("id1");
//		assertTrue(optU.isPresent());
//	}
//
//
//
//	@Test
//	void BoardUpdateTest() {
//		Optional<Board> optB = br.findById(1L);
//
//		Board board = new Board();
//		board.setBNum(optB.get().getBNum());
//		board.setCategory("수정테스트");
//		board.setTitle("제목수정");
//		board.setContent("내용수정");
//		br.save(board);
//
//	}
//
//	@Test
//	void boardFindTest() {
//		//		Iterable<Board> optB = br.findAll();
//		Optional<Board> optB = br.findById(5L);
//		assertTrue(optB.isPresent());
//	}
//
//	@Test
//	void deleteTest() {
//		Optional<Board> optB = br.findById(10L);
//		assertTrue(optB.isPresent());
//		String userId = optB.get().getUser().getId();
//		assertEquals("id1", userId);
//		Board board = optB.get();
//		br.delete(board);
//	}
}