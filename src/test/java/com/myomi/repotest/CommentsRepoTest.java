package com.myomi.repotest;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.myomi.board.entity.Board;
import com.myomi.comment.entity.Comment;
import com.myomi.comment.repository.CommentRepository;
import com.myomi.user.entity.User;
import com.myomi.user.entity.UserRepository;

@SpringBootTest
class CommentsRepoTest {
	@Autowired
	 private CommentRepository cr;
	
	@Autowired
	 private UserRepository ur;
	
	@Test
	void CommentsTestSave() {
		Optional<User> optU = ur.findById("id1");
		for(int i=1; i<=3; i++ ) {
        LocalDateTime date = LocalDateTime.now();
		Comment cm = new Comment();
		Board b = new Board();
		User u = new User();
		cm.setCNum(i);
		b.setBNum(i);
		cm.setBoard(b);
		cm.setUser(optU.get());
		cm.setContent("댓글내용"+i);
		cm.setCreatedDate(date);
		
		cr.save(cm);
		
		}
	}
}
