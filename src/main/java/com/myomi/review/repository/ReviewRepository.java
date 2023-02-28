package com.myomi.review.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.myomi.review.entity.Review;

public interface ReviewRepository extends CrudRepository<Review, Long> {
	public Optional<Review> findById(Long num);
//	List<Review> findAllByProdNum(Long prodNum);
	List<Review> findByOrderDetail_Product_ProdNum(Long prodNum);
	
//	@Query(value = "SELECT r.*, od.prod_num FROM review r"
//			+ "JOIN orders_detail od ON r.order_num = od.order_num"
//			+ "WHERE od.prod_num = prodNum", nativeQuery = true)
//	List<Review> findAllReviewByProd(@Param("od.prod_num") Long prodNum);
//	@Query(value = "select r.* from review r where r.prod_num = ?", nativeQuery = true)
	@Query(value = "select distinct r.* from review r, orders_detail od where r.prod_num = od.prod_num\n"
			+ "and r.prod_num = ?", nativeQuery = true)
	public List<Review> findAllReviewByProd(Long prodNum);
}
