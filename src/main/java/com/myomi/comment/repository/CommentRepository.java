package com.myomi.comment.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;

import com.myomi.comment.entity.Comment;

public interface CommentRepository extends CrudRepository<Comment, Long> {
   public Optional<Comment> findBycommentNumAndBoard(Long commentNum, Long boardNum);

   @EntityGraph(attributePaths = {"user","board"})
   @Query("select c from Comment c join c.board where c.user.id=:username")
   public List<Comment> findAllByComments(@Param("username") String username,Pageable pageable);
}
