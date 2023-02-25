package com.myomi.product.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.myomi.order.repository.OrderRepository;
import com.myomi.product.dto.ProdReadOneDto;
import com.myomi.product.dto.ProductDto;
import com.myomi.product.dto.ProductSaveDto;
import com.myomi.product.entity.Product;
import com.myomi.product.repository.ProductRepository;
import com.myomi.qna.entity.Qna;
import com.myomi.qna.repository.QnaRepository;
import com.myomi.review.entity.Review;
import com.myomi.review.repository.ReviewRepository;
import com.myomi.seller.entity.Seller;
import com.myomi.seller.repository.SellerRepository;
import com.myomi.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
//	@Autowired
	private final ProductRepository productRepository;

//	@Autowired
	private final SellerRepository sellerRepository;
	
//	@Autowired
	private final UserRepository userRepository;
	private final ReviewRepository reviewRepository;
	private final OrderRepository orderRepository;
	private final QnaRepository qnaRepository;
	/**
	 * 1. 셀러 등록 후 상품 등록하기
	 * 2. 등록된 상품 리스트 조회
	 * 3. 셀러별 상품 리스트 조회
	 * 4. 상품 상세 조회
	 * 5. 상품 수정
	 * 6. 상품 삭제
	 */
	
	@Transactional
	public ResponseEntity<ProductSaveDto> addProduct(ProductSaveDto productSaveDto, Authentication user) {		
		String username = user.getName();
		Optional<Seller> s = sellerRepository.findById(username); // 로그인한 유저의 id
		
		Product product = Product.builder()
				.seller(s.get()) //seller
				.category(productSaveDto.getCategory())
				.name(productSaveDto.getName())
				.originPrice(productSaveDto.getOriginPrice())
				.percentage(productSaveDto.getPercentage())
				.week(productSaveDto.getWeek())
				.detail(productSaveDto.getDetail())
				.build();
		
		productRepository.save(product);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@Transactional
	public List<ProductDto> selectBySellerId(String sellerId) {
		Optional<Seller> seller = sellerRepository.findById(sellerId);
	
		log.info("셀러아이디는:" + sellerId);
		List<Product> prods = productRepository.findAllBySellerId(seller.get().getId());
		List<ProductDto> list = new ArrayList<>();
		if (prods.size() == 0) {
		        log.info("등록된 상품이 없습니다.");
		} else {
		    for (Product p : prods) {
		    	ProductDto dto = ProductDto.builder()
			            .prodNum(p.getProdNum())
			            .seller(p.getSeller())
//			            .sellerDto()
			            .category(p.getCategory())
			            .name(p.getName())
			            .originPrice(p.getOriginPrice())
			            .percentage(p.getPercentage())
			            .week(p.getWeek())
			            .status(p.getStatus())
			            .detail(p.getDetail())
		                .build();
		        list.add(dto);
		    }
		}
		return list;
	}
	
	
	@Transactional
	public ResponseEntity<ProdReadOneDto> sellectOneProd(Long prodNum) {
		Optional<Product> optP = productRepository.findProdInfo(prodNum);
		List<Qna> qnas = qnaRepository.findByProdNum(optP.get());
		List<Review> reviews = reviewRepository.findAllReviewByProd(prodNum);
//		List<Review> reviews = reviewRepository.findByOrderDetail_Product_ProdNum(prodNum);
		if(qnas.size() == 0) {
			log.info("상품관련 문의가 없습니다.");
		}
		//빌터 패턴을 이용한 객체 보내기는 실패...! -> 빈배열로 들어감
//		List<Qna> list = new ArrayList<>();
//		if(list.size() == 0) {
//			log.info("상품 관련 문의가 없습니다.");
//		} else {
//			for(Qna q : list) {
//				Qna qna = Qna.builder()
//						.qNum(q.getQNum())
//						.userId(q.getUserId())
//						.prodNum(optP.get())
//						.queTitle(q.getQueTitle())
//						.queContent(q.getQueContent())
//						.queCreatedDate(q.getQueCreatedDate())
//						.ansContent(q.getAnsContent())
//						.ansCreatedDate(q.getAnsCreatedDate())
//						.build();
//				list.add(qna);
//			}
//		}
				
		return new ResponseEntity<>(
				ProdReadOneDto.builder()
					.prodNum(prodNum)
					.seller(optP.get().getSeller())
					.category(optP.get().getCategory())
					.name(optP.get().getName())
					.originPrice(optP.get().getOriginPrice())
		            .percentage(optP.get().getPercentage())
		            .week(optP.get().getWeek())
		            .status(optP.get().getStatus())
		            .detail(optP.get().getDetail())
		            .reviews(reviews)
		            .qnas(qnas)
//		            .qnas(list) //빌더로 받아오는거 실패 ㅠ
					.build(), HttpStatus.OK);
	}
}
