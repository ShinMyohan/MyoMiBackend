package com.myomi.board.repository;

import org.springframework.data.repository.CrudRepository;

import com.myomi.board.entity.Board;

public interface BoardRepository extends CrudRepository<Board, Long> {

}
