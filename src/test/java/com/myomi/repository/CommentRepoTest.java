package com.myomi.repository;

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
class CommentRepoTest {
	@Autowired
	private CommentRepository cr;
	
	@Autowired
	private BoardRepository br;
	
	@Autowired
	private UserRepository ur;
	
	@Test //성공
	void testCommentSave() {
		Optional<Board> optB = br.findById(1);
		for(int i=1; i<=2; i++ ) {
			Comment comment = new Comment();
			Optional<User> optU = ur.findById("id"+i);

			comment.setUser(optU.get());
			comment.setBoard(optB.get());
			comment.setContent("댓글 내용"+i);
			LocalDateTime date = LocalDateTime.now();
			comment.setCreatedDate(date);
			comment.setCNum(i);
			cr.save(comment);
		}
	}
	
	@Test
	void testReCommentSave() {
		Optional<Board> optB = br.findById(1);
		
		Optional<User> optU = ur.findById("id4");
		
		Comment comment = new Comment();
		comment.setUser(optU.get());
		comment.setBoard(optB.get());
		comment.setContent("대댓글 내용1");
		LocalDateTime date = LocalDateTime.now();
		comment.setCreatedDate(date);
		comment.setCNum(3);
		comment.setParent(1);
		cr.save(comment);
	}
}
