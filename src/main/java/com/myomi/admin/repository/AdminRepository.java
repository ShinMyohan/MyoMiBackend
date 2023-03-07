package com.myomi.admin.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.myomi.admin.entity.Admin;
import com.myomi.seller.entity.Seller;

public interface AdminRepository extends CrudRepository<Admin, String> {
	
	@Query(value="Select s.* from Seller_info s where status=:status",nativeQuery = true)
	public List<Seller> findAllByStatus(@Param(value="status")int status);
}