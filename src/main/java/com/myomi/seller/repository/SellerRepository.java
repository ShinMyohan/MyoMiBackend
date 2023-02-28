package com.myomi.seller.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.myomi.product.entity.Product;
import com.myomi.seller.entity.Seller;

public interface SellerRepository extends CrudRepository<Seller, String>{
	

	public List<Seller> findAll();
	@Query(value="Select s.* from Seller_info s where status=:status",nativeQuery = true)
	public List<Seller> findAllByStatus(@Param(value="status")int status);
	
	@Query(value="SELECT s.* from seller_info s join users u on s.seller_id=u.id WHERE s.seller_id=:sellerId",nativeQuery = true)
	public Seller findSellerBySellerId(@Param(value="sellerId")String sellerId);

	//주문별 조회
	@Query("SELECT p FROM Product p WHERE p.seller.sellerId.id =:userId")
	List<Product> findAllBySellerId(@Param("userId")String userId);

	//판매자 탈퇴신청(status 변경)
	@Modifying
	@Query("UPDATE Seller s SET s.status =3 WHERE s.sellerId.id =:userId")
	public void updateSellerId(@Param("userId")String userId);

	//특정 셀러 찾기
	Optional<Seller> findById(String sellerId);

}

