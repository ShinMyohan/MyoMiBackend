package com.myomi.qna.repository;


import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.myomi.qna.entity.Qna;


public interface QnaRepository extends CrudRepository<Qna, Long> {
	
	//상품별 문의 조회
	@EntityGraph(attributePaths = "userId")
	@Query("SELECT q FROM Qna q join q.prodNum WHERE q.prodNum.prodNum=:pd")
	public List<Qna> findAllByProdNum(@Param("pd")Long pd,Pageable pageable);
	
	//회원별 문의 조회
	@EntityGraph(attributePaths = "userId")
	@Query("SELECT q FROM Qna q join q.userId join q.prodNum WHERE q.userId.id=:user")
	public List<Qna> findAllByUserId(@Param("user")String user, Pageable pageable);
	
	//셀러별 문의 조회
	@Query("SELECT q From Qna q join q.prodNum WHERE q.prodNum.seller.sellerId.id=:user")
	public List<Qna> findAllBySellerId(@Param("user")String user,Pageable pageable);

	
}
