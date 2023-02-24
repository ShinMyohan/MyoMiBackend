package com.myomi.seller.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.myomi.seller.entity.Seller;

public interface SellerRepository extends CrudRepository<Seller, String>{

	Optional<Seller> findById(String sellerId);
}
