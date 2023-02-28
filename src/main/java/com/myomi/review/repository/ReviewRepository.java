package com.myomi.review.repository;

import java.util.List;

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
}
