package com.myomi.review.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.myomi.review.entity.Review;

public interface ReviewRepository extends CrudRepository<Review, Long> {

	public List<Review> findAllByUser(String user);
	
	@Query("select r from Review r join r.orderDetail.order join r.orderDetail.product where r.reviewNum=:reviewNum")
	public Review findReviewById(@Param("reviewNum")Long reviewNum);
	
	@Query("select r from Review r join r.orderDetail.order join r.orderDetail.product join r.user where r.orderDetail.product.prodNum=:prodNum")
	public List<Review> findAllByProdNum(@Param("prodNum")Long prodNum);
	
	@Query("select r from Review r join r.orderDetail.order join r.orderDetail.product join r.user where r.user.id=:id")
	public List<Review> findAllByUserId(@Param("id")String userId);
	
	@Query("select r from Review r join r.orderDetail.order join r.orderDetail.product where r.orderDetail.product.seller.sellerId.id=:user")
	public List<Review>findAllBySeller(@Param("user")String user);
	
	@Query("select r from Review r join r.orderDetail.order join r.orderDetail.product where r.orderDetail.product.seller.sellerId.id=:user and r.orderDetail.product.prodNum=:prodNum")
	public List<Review>findAllByUserandProdNum(@Param("user")String user,@Param("prodNum")Long prodNum);
	
	@Query("select r from Review r join r.orderDetail.order join r.orderDetail.product join r.user join r.bestReview where r.orderDetail.product.prodNum=:prodNum")
	public List<Review>findAllByprodNumandReviewNum(@Param("prodNum")Long prodNum);

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
