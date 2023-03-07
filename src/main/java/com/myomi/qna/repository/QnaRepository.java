package com.myomi.qna.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;

import com.myomi.product.entity.Product;
import com.myomi.qna.dto.QnaUReadResponseDto;
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
	
	//회원 문의 상세 조회
	@Query("SELECT q FROM Qna q WHERE q.qnaNum =:qnaNum AND q.userId.id=:user")
	public Optional<Qna> findByUserIdAndQnaNum(@Param("user")String user,@Param("qnaNum")Long qnaNum);
	
	//셀러별 문의 조회
	@Query("SELECT q FROM Qna q join q.prodNum WHERE q.prodNum.seller.sellerId.id=:user")
	public List<Qna> findAllBySellerId(@Param("user")String user,Pageable pageable);
	
	//셀러별 문의 상세조회
	@Query("SELECT q FROM Qna q join q.prodNum WHERE q.prodNum.seller.sellerId.id =:user AND q.qnaNum =:qnaNum")
	public Optional<Qna> findBySellerId(@Param("user")String user,@Param("qnaNum")Long qnaNum);

	public List<Qna> findByProdNumOrderByQnaNumDesc(Product prodNum);
}
