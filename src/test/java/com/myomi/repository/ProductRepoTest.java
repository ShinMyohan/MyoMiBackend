//package com.myomi.repository;
//
//import java.util.Optional;
//
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import com.myomi.product.entity.Product;
//import com.myomi.product.repository.ProductRepository;
//import com.myomi.seller.entity.Seller;
//import com.myomi.seller.repository.SellerRepository;
//
//@SpringBootTest
//class ProductRepoTest {
//	private Logger logger = LoggerFactory.getLogger(getClass());
//	
//	@Autowired
//	private SellerRepository sr;
//	
//	@Autowired
//	private ProductRepository pr;
//	
//
//	@Test
//	@DisplayName("셀러가 상품추가") //성공
//	void testProductSave() {
//		Optional<Seller> optS = sr.findById("id3"); //셀러 아이디
//		for(int i=1; i<=2; i++) {
//			Product prod = new Product();
//			prod.setPNum((i*1L)+3L);
//			prod.setSeller(optS.get());
//			prod.setCategory("도시락");
//			prod.setName("성언이네 도시락");
//			prod.setOriginPrice(i*13000L);
//			prod.setPercentage(i*11);
//			prod.setWeek(i);
//			prod.setStatus(0);
//			prod.setDetail(i+"개의 재료소진으로 인해 재료 대체");
//			prod.setReviewCnt(i*1L);
//			prod.setStars(4);
//			prod.setFee(9);
//			
//			pr.save(prod);
//		}
//	}
//
//}
