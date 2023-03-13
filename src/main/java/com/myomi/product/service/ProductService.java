package com.myomi.product.service;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.myomi.common.status.AddException;
import com.myomi.common.status.ErrorCode;
import com.myomi.common.status.ExceedMaxUploadSizeException;
import com.myomi.common.status.NoResourceException;
import com.myomi.common.status.UnqualifiedException;
import com.myomi.product.dto.ProductDto;
import com.myomi.product.dto.ProductReadOneDto;
import com.myomi.product.dto.ProductSaveDto;
import com.myomi.product.dto.ProductUpdateDto;
import com.myomi.product.entity.Product;
import com.myomi.product.repository.ProductRepository;
import com.myomi.qna.dto.QnaPReadResponseDto;
import com.myomi.qna.entity.Qna;
import com.myomi.qna.repository.QnaRepository;
import com.myomi.review.dto.ReviewDetailResponseDto;
import com.myomi.review.dto.ReviewReadResponseDto;
import com.myomi.review.entity.Review;
import com.myomi.review.repository.ReviewRepository;
import com.myomi.s3.FileUtils;
import com.myomi.s3.S3Uploader;
import com.myomi.seller.entity.Seller;
import com.myomi.seller.repository.SellerRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
	@Autowired
	private S3Uploader s3Uploader;
	private final ProductRepository productRepository;
	private final SellerRepository sellerRepository;
	private final ReviewRepository reviewRepository;
	private final QnaRepository qnaRepository;
	private LocalDateTime date = LocalDateTime.now();
	/**
	 * TODO:
	 * 1. 셀러 등록 후 상품 등록하기
	 * 2. 등록된 상품 리스트 조회
	 * 3. 셀러별 상품 리스트 조회
	 * 4. 상품 상세 조회
	 * 5. 상품 수정
	 * 6. 상품 삭제
	 * @throws IOException 
	 */
	
	@Transactional
	public ResponseEntity<?> addProduct(ProductSaveDto productSaveDto, Authentication seller) throws NoResourceException,IOException,UnqualifiedException,ExceedMaxUploadSizeException,AddException {
		Seller s = sellerRepository.findById(seller.getName())
				.orElseThrow(() -> new NoResourceException(ErrorCode.RESOURCE_NOT_FOUND, "NOT_FOUND_SELLER"));
		
		if(s.getStatus() == 3) {
			log.error("탈퇴를 신청한 판매자입니다.");
			throw new UnqualifiedException(ErrorCode.BAD_REQUEST, "UNQUALIFIED_SELLER");
		}
		
		if(productSaveDto.getName().length() > 30) {
			throw new AddException(ErrorCode.BAD_REQUEST, "EXCEED_MAX_CHAR");
		}
		MultipartFile file = productSaveDto.getFile();
		
		
		if(file != null) {
			InputStream inputStream = file.getInputStream();
			
			boolean isValid = FileUtils.validImgFile(inputStream);
			if(!isValid) {
				throw new ExceedMaxUploadSizeException(ErrorCode.BAD_REQUEST,"EXCEED_FILE_SIZE");
			}
		} else {
			log.info("첨부된 이미지 파일이 없습니다.");
			throw new NoResourceException(ErrorCode.RESOURCE_NOT_FOUND, "NOT_FOUND_FILE");
		}

		String fileUrl = s3Uploader.upload(file, "상품이미지", seller, productSaveDto);
		
		Product product = productSaveDto.toEntity(productSaveDto, s, fileUrl);
		
		//상품등록
		productRepository.save(product);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@Transactional
	public List<ProductDto> getProductBySellerId(String sellerId) throws NoResourceException{
		List<Product> prods = productRepository.findAllBySellerIdOrderByReviewCntDesc(sellerId);
		List<ProductDto> list = new ArrayList<>();
		if (prods.size() == 0) {
		        log.info("등록된 상품이 없습니다.");
		        throw new NoResourceException(ErrorCode.RESOURCE_NOT_FOUND, "PRODUCT_NOT_FOUND");
		} else {
		    for (Product p : prods) {
		    	ProductDto dto = new ProductDto();
		        list.add(dto.toDto(p));
		    }
		}
		return list;
	}
	
	
	@Transactional
	public ResponseEntity<?> getOneProd(Long prodNum) {
		Product product = productRepository.findProdInfo(prodNum)
				.orElseThrow(() -> new NoResourceException(ErrorCode.RESOURCE_NOT_FOUND, "PRODUCT_NOT_FOUND"));
		
//		List<Qna> qnas = product.get().getQnas(); //이방법이 훨씬 좋아
		List<Qna> qnas = qnaRepository.findByProdNumOrderByQnaNumDesc(product);
		//리뷰 DTO 받으면 태리님 방식처럼 해보기 
		List<Review> reviews = reviewRepository.findAllReviewByProd(prodNum);
		List<Review> bestReviews = reviewRepository.findAllBestReviewByProd(prodNum);
		if(product.getQnas().size() == 0) {	
			log.info("상품관련 문의가 없습니다.");
		}
		
		if(reviews.size() == 0) {	
			log.info("상품에 대한리뷰가 없습니다.");
		}
		if(bestReviews.size()==0) {
			log.info("상품에 대한 베스트리뷰가 없습니다.");
		}
		
		List<QnaPReadResponseDto> qDto = new ArrayList<>();
		for(Qna q : qnas) {
			QnaPReadResponseDto qnaDto = new QnaPReadResponseDto();
			qDto.add(qnaDto.toDto(q));
		}
		
		List<ReviewReadResponseDto> rDto = new ArrayList<>();
		for(Review r : reviews) {
			ReviewReadResponseDto reviewDto = new ReviewReadResponseDto();
			rDto.add(reviewDto.toDto(r));
		}
		
		List<ReviewDetailResponseDto> bDto = new ArrayList<>();
		for(Review b : bestReviews) {
			ReviewDetailResponseDto bestReviewDto = new ReviewDetailResponseDto();
			bDto.add(bestReviewDto.toDto(b));
		}
		
		ProductReadOneDto dto = new ProductReadOneDto();
		return new ResponseEntity<>(dto.toDto(product, rDto, qDto,bDto) , HttpStatus.OK);
	}
	
	@Transactional //성공
	public ResponseEntity<ProductUpdateDto> modifyProduct(Long prodNum, ProductUpdateDto productUpdateDto, Authentication seller) {
		Product p = productRepository.findBySellerIdAndProdNum(seller.getName(), prodNum)
				.orElseThrow(() -> new NoResourceException(ErrorCode.RESOURCE_NOT_FOUND, "PRODUCT_NOT_FOUND"));
		
		if(p != null) {
			p.update(productUpdateDto.getDetail(), productUpdateDto.getStatus(), date);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@Transactional //성공
	public ResponseEntity<?> removeProduct(Long prodNum, Authentication seller) {
		Product p = productRepository.findById(prodNum)
				.orElseThrow(() -> new NoResourceException(ErrorCode.RESOURCE_NOT_FOUND, "PRODUCT_NOT_FOUND"));
		Seller s = sellerRepository.findById(seller.getName())
				.orElseThrow(() -> new NoResourceException(ErrorCode.RESOURCE_NOT_FOUND, "NOT_FOUND_SELLER"));
		if(p.getSeller().getId() != s.getId()) {
			throw new UnqualifiedException(ErrorCode.BAD_REQUEST, "DISCORD_SELLER");
		}
		productRepository.deleteById(prodNum);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	//상품 모든 리스트 뿌려주기. 단, status가 1인걸 기본으로 뿌려줌. <- 프론트에서 설정해야함.
//	@Transactional
//	public ResponseEntity<?> getAllProduct(int status) { 
//		List<Product> pList = productRepository.findAllByStatusOrderByWeek(status);
//		List<ProductDto> list = new ArrayList<>();
//		for(Product p : pList) {
//			ProductDto dto = new ProductDto();
//	        list.add(dto.toDto(p));
//		}
//		return new ResponseEntity<>(list, HttpStatus.OK);
//	}
	@Transactional
	public ResponseEntity<?> getAllProduct() { 
		List<Product> pList = productRepository.findAll();
		if(pList.size() == 0) {
			
		}
		List<ProductDto> list = new ArrayList<>();
		for(Product p : pList) {
			ProductDto dto = new ProductDto();
	        list.add(dto.toDto(p));
		}
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@Transactional
	public ResponseEntity<?> getAllProduct(String keyword) {
		List<Product> pList = productRepository.findAllByNameContaining(keyword);
		List<ProductDto> list = new ArrayList<>();
		if(pList.size() == 0) {
			log.error("해당 키워드가 포함된 상품이 없습니다.");
		}
		for(Product p : pList) {
			ProductDto dto = new ProductDto();
			list.add(dto.toDto(p));
		}
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	// 셀러페이지
	@Transactional
	public ResponseEntity<?> getOneProdBySeller(Long prodNum, Authentication seller) throws NoResourceException {
//		Optional<Product> product = productRepository.findBySellerIdAndProdNum(seller.getName(),prodNum);
		Product product = productRepository.findBySellerIdAndProdNum(seller.getName(),prodNum)
				.orElseThrow(()-> new NoResourceException(ErrorCode.RESOURCE_NOT_FOUND,"PRODUCT_NOT_FOUND"));

		ProductDto dto = new ProductDto();
		
		return new ResponseEntity<>(dto.toDto(product) , HttpStatus.OK);
	}
}
