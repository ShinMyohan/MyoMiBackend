package com.myomi.review.repository;

import com.myomi.review.entity.Review;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends CrudRepository<Review, Long> {

    @Query(value = "select r.* from review r join orders_detail o on r.prod_num=o.prod_num\r\n"
            + "and r.order_num = o.order_num\r\n"
            + "join users u on r.user_id=u.id where r.prod_num=? and r.created_date between (select to_char(sysdate,'yyyy-mm') || '-01' as 이번달시작일 from dual)\r\n"
            + "and (select to_char(LAST_DAY(sysdate),'yyyy-mm-dd') as 이번달마지막일 from dual )", nativeQuery = true)
    public List<Review> findAllByProdNum(@Param("prodNum") Long prodNum);

    @Query("select r from Review r join r.orderDetail.order join r.orderDetail.product where r.reviewNum=:reviewNum")
    public Review findReviewByUserId(@Param("reviewNum") Long reviewNum);

    @Query("select r from Review r join r.orderDetail.order join r.orderDetail.product join r.user where r.user.id=:id ORDER BY r.reviewNum DESC")
    public List<Review> findAllByUserIdOrderByReviewNumDesc(@Param("id") String userId);

    @Query("select r from Review r join r.orderDetail.order join r.orderDetail.product join r.user where r.reviewNum=:reviewNum and r.user.id=:id")
    public Review findReviewByReviewNumAndUserId(@Param("reviewNum") Long reviewNum, @Param("id") String userId);

    public Optional<Review> findById(Long num);

    List<Review> findByOrderDetail_Product_ProdNum(Long prodNum);

    @Query(value = "select distinct r.* from review r, orders_detail od where r.prod_num = od.prod_num\n"
            + "and r.prod_num = ?", nativeQuery = true)
    public List<Review> findAllReviewByProd(Long prodNum);
    
    @Query(value = "select distinct r.* from review r, orders_detail od,best_review b where r.prod_num = od.prod_num\n"
            + " and r.num=b.review_num and r.prod_num = ?", nativeQuery = true)
    public List<Review>findAllBestReviewByProd(Long prodNum);
}