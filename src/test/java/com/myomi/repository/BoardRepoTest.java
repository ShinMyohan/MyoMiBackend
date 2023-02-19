package com.myomi.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.myomi.board.entity.Board;
import com.myomi.board.repository.BoardRepository;
import com.myomi.user.entity.User;
import com.myomi.user.repository.UserRepository;

@SpringBootTest
class BoardRepoTest {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private BoardRepository br;

	@Autowired
	private UserRepository ur;
	
	@Test  //통과 
	void BoardTestSave() {
		Optional<User> optU = ur.findById("id1");
		for (int i=1; i<=3; i++) {
			Board board = new Board();
//			User user = new User();
			                                                       
			board.setBNum(i);
//			user.setId("id1");
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
	
//	@Test
//	void BoardTestFindAll() {
//		Iterable<Board> boards = br.findAll();
//		boards.forEach(board->{
//			logger.board("게시글 제목:"+board.getTitle()+"글쓴이:"+board.getUser().getId());
//		});
//	}
}

