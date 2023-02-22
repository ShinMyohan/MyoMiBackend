package com.myomi.repotest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.myomi.board.entity.Board;
import com.myomi.board.repository.BoardRepository;
import com.myomi.comment.entity.Comment;
import com.myomi.comment.repository.CommentRepository;
import com.myomi.user.entity.User;
import com.myomi.user.repository.UserRepository;

@SpringBootTest
class CommentsRepoTest {
	@Autowired
	 private CommentRepository cr;
	
	@Autowired
	 private UserRepository ur;
	
	@Autowired
	private BoardRepository br;
	
	@Test
	void CommentsTestSave() {
		Optional<User> optU = ur.findById("id1");
		Optional<Board> optB = br.findById(1);
		for(int i=1; i<=3; i++ ) {
        LocalDateTime date = LocalDateTime.now();
		Comment cm = new Comment();
		Board b = new Board();
		User u = new User();
		
		b.setBNum(optB.get().getBNum());
		cm.setBoard(b);
		cm.setUser(optU.get());
		cm.setContent("댓글내용"+i);
		cm.setCreatedDate(date);
	
		cr.save(cm);
		
		}
	}
	
	@Test
	void testReCommentSave() {
		Optional<Board> optB = br.findById(1);
		Optional<User> optU = ur.findById("id1");
		
		Comment comment = new Comment();
		comment.setUser(optU.get());
		comment.setBoard(optB.get());
		comment.setContent("대댓글 내용2");
		LocalDateTime date = LocalDateTime.now();
		comment.setCreatedDate(date);
		comment.setParent(1);
		cr.save(comment);
	}
	
	@Test
	void cmtUpdateTest() {
		Optional<Comment> optC = cr.findById(1);
		Optional<Board> optB = br.findById(1);
		Optional<User> optU = ur.findById("id1");
		
		Comment cmt = new Comment();
		cmt.setContent("수정테스트");
		cmt.setCNum(optC.get().getCNum());
		cmt.setBoard(optB.get());
	    cmt.setUser(optU.get());
	    
		cr.save(cmt);
	}
	
	@Test
	void deleteTest() {
    Optional<Comment> optC = cr.findById(13);
    assertTrue(optC.isPresent());
    String userId = optC.get().getUser().getId();
    assertEquals("id1", userId);
    Comment comment = optC.get();
    cr.delete(comment);
}
}
