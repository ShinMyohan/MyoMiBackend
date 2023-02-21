package com.myomi.comment.repository;

import org.springframework.data.repository.CrudRepository;

import com.myomi.comment.entity.Comment;

public interface CommentRepository extends CrudRepository<Comment, Integer> {

}
