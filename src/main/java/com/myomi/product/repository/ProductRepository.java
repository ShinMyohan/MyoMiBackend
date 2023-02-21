package com.myomi.product.repository;

import org.springframework.data.repository.CrudRepository;

import com.myomi.product.entity.Product;

public interface ProductRepository extends CrudRepository<Product, Long>{

}
