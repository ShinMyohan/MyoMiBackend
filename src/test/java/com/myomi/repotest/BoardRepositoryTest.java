
package com.myomi.repotest;

import java.time.LocalDateTime;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.myomi.board.entity.Board;
import com.myomi.board.repository.BoardRepository;
import com.myomi.user.entity.User;
import com.myomi.user.entity.UserRepository;

@SpringBootTest
class BoardRepositoryTest {
	@Autowired
	private BoardRepository br;
	
	@Autowired
	private UserRepository ur;
	
	@Test  //통과 
	void BoardTestSave() {
		Optional<User> optU = ur.findById("id1");
		for (int i=1; i<=3; i++) {
			Board board = new Board();                                             
			board.setBNum(i);
			board.setUser(optU.get());
			board.setCategory("잡담"+i);
			board.setTitle("제목" + i);
			board.setContent("내용" + i);
			LocalDateTime date = LocalDateTime.now();
			board.setCreatedDate(date);
			board.setHits(i);
			br.save(board);
		} 
	}
	
	@Test
	void saveTest() {
		Optional<User> optU = ur.findById("id1");
		assertTrue(optU.isPresent());
	}
}
