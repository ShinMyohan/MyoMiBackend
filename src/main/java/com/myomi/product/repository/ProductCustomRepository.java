package com.myomi.product.repository;

import java.util.List;

import com.myomi.product.dto.ProductReadOneDto;

public interface ProductCustomRepository {
	List<ProductReadOneDto> findProdInfo(Long prodNum);
}
