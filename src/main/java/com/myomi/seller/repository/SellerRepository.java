package com.myomi.seller.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.myomi.seller.entity.Seller;

public interface SellerRepository extends CrudRepository<Seller, String>{
	
	public List<Seller> findAll();
	@Query(value="Select s.* from Seller_info s where status=:status",nativeQuery = true)
	public List<Seller> findAllByStatus(@Param(value="status")int status);
	
	@Query(value="SELECT s.* from seller_info s join users u on s.seller_id=u.id WHERE s.seller_id=:sellerId",nativeQuery = true)
	public Seller findSellerBySellerId(@Param(value="sellerId")String sellerId);
}
