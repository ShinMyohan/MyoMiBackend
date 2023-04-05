package com.myomi.admin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.myomi.admin.entity.Admin;
import com.myomi.seller.entity.Seller;

public interface AdminRepository extends CrudRepository<Admin, String> {
	
}