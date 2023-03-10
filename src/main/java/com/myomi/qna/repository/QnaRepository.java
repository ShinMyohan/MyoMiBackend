package com.myomi.qna.repository;


import com.myomi.product.entity.Product;
import com.myomi.qna.entity.Qna;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface QnaRepository extends CrudRepository<Qna, Long> {
	//상품별 문의 조회
	@EntityGraph(attributePaths = "userId")
	@Query("SELECT q FROM Qna q join q.prodNum WHERE q.prodNum.prodNum=:pd")
	public List<Qna> findAllByProdNum(@Param("pd")Long pd);

	//회원별 문의 조회
	@Query(value = "SELECT * FROM (SELECT rownum rn, a. *\n"
			+ "            FROM(SELECT q.num, q.que_created_date, p.name, q.que_title, q.ans_content, q.user_id\n"
			+ "			FROM qna q, product p\n"
			+ "			WHERE q.prod_num = p.num\n"
			+ "            AND q.user_id = :user\n"
			+ "			ORDER BY q.que_created_date DESC) a)\n"
			+ "            WHERE rn between :startRow and :endRow", nativeQuery = true)
	public List<Object[]> findAllByUserId(@Param(value = "user")String user, @Param(value = "startRow")int startRow,@Param(value = "endRow")int endRow);


	//회원 문의 상세 조회
	@Query("SELECT q FROM Qna q WHERE q.qnaNum =:qnaNum AND q.userId.id=:user")
	public Optional<Qna> findByUserIdAndQnaNum(@Param("user")String user,@Param("qnaNum")Long qnaNum);

	//셀러별 문의 조회
	//	@Query("SELECT q FROM Qna q join q.prodNum WHERE q.prodNum.seller.sellerId.id=:user")
	//	public List<Qna> findAllBySellerId(@Param("user")String user,Pageable pageable);

	//셀러별 문의 조회(SQL)
	@Query(value ="SELECT q.*, p.name,p.product_img_url, u.name "
			+ "FROM qna q "
			+ "RIGHT JOIN product p ON q.prod_num = p.num "
			+ "RIGHT JOIN seller_info s ON p.seller_id = s.seller_id "
			+ "RIGHT JOIN users u ON u.id = q.user_id "
			+ "WHERE s.seller_id = :user ORDER BY q.num DESC",nativeQuery = true)
	public List<Qna> findAllBySellerId(@Param(value = "user")String user);


	//셀러별 문의 상세조회
	@Query("SELECT q FROM Qna q join q.prodNum WHERE q.prodNum.seller.sellerId.id =:user AND q.qnaNum =:qnaNum")
	public Optional<Qna> findBySellerId(@Param("user")String user,@Param("qnaNum")Long qnaNum);

	public List<Qna> findByProdNumOrderByQnaNumDesc(Product prodNum);
}
