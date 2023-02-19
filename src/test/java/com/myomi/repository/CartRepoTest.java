package com.myomi.repository;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.myomi.cart.entity.Cart;
import com.myomi.cart.repository.CartRepository;
import com.myomi.product.entity.Product;
import com.myomi.product.repository.ProductRepository;
import com.myomi.user.entity.User;
import com.myomi.user.repository.UserRepository;

@SpringBootTest
class CartRepoTest {
	@Autowired
	private CartRepository cr;
	
	@Autowired
	private UserRepository ur;
	
	@Autowired
	private ProductRepository pr;
	
	@Test
	@DisplayName("장바구니에 상품담기")
	void testSaveCart() {
		Optional<User> optU = ur.findById("id2");
		for(int i=1; i<=5; i+=2) {
			Optional<Product> optP = pr.findById(i*1L);
			
			Cart cart = new Cart();
			cart.setUser(optU.get());
			cart.setProduct(optP.get());
			cart.setProdCnt(i+1);
			
			cr.save(cart);
		}
	}

}
