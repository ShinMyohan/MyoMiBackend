package com.myomi.repository;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.myomi.product.entity.Product;
import com.myomi.product.repository.ProductRepository;
import com.myomi.seller.entity.Seller;
import com.myomi.seller.repository.SellerRepository;

@SpringBootTest
class ProductRepoTest {
	@Autowired
	private SellerRepository sr;
	
	@Autowired
	private ProductRepository pr;
	

	@Test
	@DisplayName("셀러가 상품추가") //성공
	void testProductSave() {
		Optional<Seller> optS = sr.findById("id1"); //셀러 아이디
		for(int i=1; i<=3; i++) {
			Product prod = new Product();
			prod.setPNum(i*1L);
			prod.setSeller(optS.get());
			prod.setCategory("샐러드");
			prod.setName("미미네 샐러드");
			prod.setOriginPrice(i*12000L);
			prod.setPercentage(i*10);
			prod.setWeek(i);
			prod.setStatus(0);
			prod.setDetail(i+"개의 재료소진으로 인해 재료 대체");
			prod.setReviewCnt(i*1L);
			prod.setStars(4);
			prod.setFee(9);
			
			pr.save(prod);
		}
	}

}
