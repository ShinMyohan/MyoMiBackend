package com.myomi.product.repository;

import com.myomi.product.entity.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long>{
    public Optional<Product> findByProdNum(Long prodNum);

}
