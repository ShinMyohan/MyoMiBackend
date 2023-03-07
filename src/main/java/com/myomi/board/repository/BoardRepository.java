package com.myomi.board.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.myomi.board.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {

    @EntityGraph(attributePaths = "user")
    public Page<Board> findAll(Pageable pageable);

    @EntityGraph(attributePaths = "user")
    public Optional<Board> findById(Long boardNum);

    @EntityGraph(attributePaths = "user")
    public List<Board> findByTitleContaining(String keyword, Pageable pageable);

    @EntityGraph(attributePaths = "user")
    public List<Board> findByCategoryContainingAndTitleContaining(String category, String title, Pageable pageable);

    @Query("select b from Board b where b.user.id=:username")
    public List<Board> findAllByUser(@Param("username") String username, Pageable pageable);

    @Modifying //update쿼리 쓰려면 있어야함!
    @Query("UPDATE Board b set b.hits = b.hits+1 where b.boardNum=:boardNum")
    public void updateHits(Long boardNum);


}
 

 