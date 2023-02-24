package com.myomi.repository;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.myomi.product.entity.Product;
import com.myomi.product.repository.ProductRepository;
import com.myomi.seller.entity.Seller;
import com.myomi.seller.repository.SellerRepository;
import com.myomi.user.entity.User;
import com.myomi.user.repository.UserRepository;

@SpringBootTest
class ProductRepositoryTest {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ProductRepository pr;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private SellerRepository sr;

	@Test
	void testProductSave() {
		Optional<User> u = userRepository.findById("id1");
		Seller seller = u.get().getSeller();
		
		Product product = Product.builder()
				.seller(seller)
				.category("도시락")
				.name("완맛도시락")
				.originPrice(40000L)
				.percentage(23)
				.week(2)
				.status(0)
				.detail("굿")
				.build();
		
		seller.addProduct(product);
		pr.save(product);
	}

	
	@Test
	void testProdList() {
		Optional<Seller> seller = sr.findById("id1");
		List<Product> prods = pr.findAllBySellerId(seller.get().getId());
		for(Product p : prods) {
			logger.info("상품명: " + p.getName() + "상품 가격:" + p.getOriginPrice());
			
		}
		logger.info("--------------------------");
	}
}
