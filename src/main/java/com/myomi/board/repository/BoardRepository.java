package com.myomi.board.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.myomi.board.entity.Board;
import com.myomi.comment.entity.Comment;

public interface BoardRepository extends CrudRepository<Board, Long> {

	@EntityGraph(attributePaths = "user")
	public List<Board> findAll(Pageable pageable);
	
	@EntityGraph(attributePaths = "user")
	public Optional<Board> findById(Long boardNum);
	
	@EntityGraph(attributePaths = "user")
	public List<Board> findByTitleContaining(String keyword, Pageable pageable);
	
	@EntityGraph(attributePaths = "user")
	public List<Board> findByCategoryContainingAndTitleContaining(String category, String title, Pageable pageable);

	@Query("select b from Board b where b.user.id=:username")
	public List<Board> findAllByUser(@Param("username")String username,Pageable pageable);

	@EntityGraph(attributePaths = "user")
	@Query("select b from Board b join b.comments where b.boardNum=:boardNum")
	public Board findBoardById(@Param("boardNum")Long boardNum);
}
 

 