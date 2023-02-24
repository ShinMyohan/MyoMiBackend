package com.myomi.comment.repository;


import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.myomi.comment.entity.Comment;

public interface CommentRepository extends CrudRepository<Comment, Long> {
   public Optional<Comment> findBycNumAndBoard(Long cNum, Long bNum);

}
