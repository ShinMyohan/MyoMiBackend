package com.myomi.qna.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.myomi.product.entity.Product;
import com.myomi.qna.entity.Qna;


public interface QnaRepository extends CrudRepository<Qna, Long> {
	public List<Qna> findByProdNum(Product prodNum);
}
