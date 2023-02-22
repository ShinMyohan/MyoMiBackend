package com.myomi.board.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.myomi.board.entity.Board;
import com.myomi.user.entity.User;

public interface BoardRepository extends CrudRepository<Board, Long> {

	public List<Board> findAll(Pageable pageable);
	//public Optional<User> findById (String userId);
}


