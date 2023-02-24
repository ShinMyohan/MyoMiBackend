package com.myomi.board.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.myomi.board.entity.Board;
import com.myomi.user.entity.User;

public interface BoardRepository extends CrudRepository<Board, Long> {

	@EntityGraph(attributePaths = "user")
	public List<Board> findAll(Pageable pageable);
	 
//	@EntityGraph(attributePaths = "user")
//	public List<Board> findAll();
	//네이티브 쿼리 쓰는 것 고려,,
	//패치조인,,,,
	//시큐리티가 세션별로 유지되는 것 찾아보기~
	
	@EntityGraph(attributePaths = "user")
	public Optional<Board> findById(Long bNum);
	
	@EntityGraph(attributePaths = "user")
	public List<Board> findByTitleContaining(String keyword, Pageable pageable);
	
	@EntityGraph(attributePaths = "user")
	public List<Board> findByCategoryAndTitleContaining(String category, String keyword, Pageable pageable);

	public List<Board> findByUserName(String username);
	
}


