package com.myomi.seller.service;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.myomi.order.entity.OrderDetail;
import com.myomi.order.repository.OrderRepository;
import com.myomi.product.entity.Product;
import com.myomi.product.repository.ProductRepository;
import com.myomi.qna.entity.Qna;
import com.myomi.s3.FileUtils;
import com.myomi.s3.S3UploaderSellerIntNum;
import com.myomi.s3.S3UploaderSellerNum;
import com.myomi.seller.dto.SellerAddRequestDto;
import com.myomi.seller.dto.SellerCheckRequestDto;
import com.myomi.seller.dto.SellerInfoResponseDto;
import com.myomi.seller.dto.SellerOrderDetailDto;
import com.myomi.seller.dto.SellerProductResponseDto;
import com.myomi.seller.dto.SellerReadResponseDto;
import com.myomi.seller.entity.Seller;
import com.myomi.seller.repository.SellerRepository;
import com.myomi.user.entity.User;
import com.myomi.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class SellerService {
    @Autowired
    private S3UploaderSellerNum s3UploaderSellerNum;
    @Autowired
    private S3UploaderSellerIntNum s3UploaderSellerIntNum;
	private final SellerRepository sr;
	private final UserRepository ur;
	private final OrderRepository or;
	private final ProductRepository pr;


	//판매자로 신청
	@Transactional
	public void addSeller(SellerAddRequestDto addDto, Authentication user) throws IOException{
		String userId = user.getName();
		Optional<User> optU = ur.findById(userId);
		MultipartFile file1 = addDto.getFile1();
		MultipartFile file2 = addDto.getFile2();
		
		if(file1 != null) {
			InputStream inputStream1 = file1.getInputStream();
			boolean isValid = FileUtils.validImgFile(inputStream1);
			if(!isValid) {
				throw new IOException("파일 형식 오류");
			}
			String fileUrl1 = s3UploaderSellerNum.upload(file1, "사업자등록증사본", user, addDto);
			String fileUrl2 = s3UploaderSellerIntNum.upload(file2, "통신판매업신고증사본", user, addDto);
			Seller seller = Seller.builder()
    				.sellerId(optU.get())
    				.companyName(addDto.getCompanyName())
    				.companyNum(addDto.getCompanyNum())
    				.internetNum(addDto.getInternetNum())
    				.addr(addDto.getAddr())
    				.manager(addDto.getManager())
    		        .bankAccount(addDto.getBankAccount())
    		        .companyImgUrl(fileUrl1)
    		        .internetImgUrl(fileUrl2)
    				.build();
    		sr.save(seller);
		}
		
	}
	
	//판매자 사업자등록번호 검증
	@Transactional
	public int getSellerCheck (SellerCheckRequestDto num) {
		Optional<Seller> seller = sr.findByCompanyNum(num.getCompanyNum());
		if(seller.isPresent()) {
			log.info("이미 등록된 사업자등록번호 입니다.");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}else {
			return 1;
		}
	}

	//판매자 신청현황 조회
	@Transactional
	public SellerReadResponseDto getDetailBySellerJoin(Authentication user) {
		String userId = user.getName();
		Optional<Seller> seller = sr.findById(userId);
		if(seller.isEmpty()) {
			log.info("신청내역이 없습니다.");
		} 
		SellerReadResponseDto dto = SellerReadResponseDto.builder()
				.companyName(seller.get().getCompanyName())
				.companyNum(seller.get().getCompanyNum())
				.internetNum(seller.get().getInternetNum())
				.addr(seller.get().getAddr())
				.manager(seller.get().getManager())
				.status(seller.get().getStatus())
				.bankAccount(seller.get().getBankAccount())
				.build();
		return dto;
	}
	
	//판매자 스토어 정보
	@Transactional
	public SellerInfoResponseDto getSellerStoreInfo(String seller) {
		Optional<Seller> optS = sr.findById(seller);
		SellerInfoResponseDto dto = SellerInfoResponseDto.builder()
				.companyName(optS.get().getCompanyName())
				.followCnt(optS.get().getFollowCnt())
				.build();
		return dto;
	}

	//판매자 주문현황 조회
	@Transactional
	public List<SellerOrderDetailDto> getAllSellerOrderList(Authentication user,Pageable pageable){
		String userId = user.getName();
		List<Product> list = sr.findAllBySellerId(userId);

		List<SellerOrderDetailDto> odList = new ArrayList<>();
		for(Product p : list) {
			p.getOrderDetails();
			for(OrderDetail od : p.getOrderDetails()) {
				SellerOrderDetailDto dto = SellerOrderDetailDto.builder()
						.prodNum(od.getProduct().getProdNum())
						.name(od.getProduct().getName())
						.orderNum(od.getOrder().getOrderNum())
						.user(od.getOrder().getUser().getName())
						.prodCnt(od.getProdCnt())
						.createdDate(od.getOrder().getCreatedDate())
						.build();

				odList.add(dto);
			}
		}
		return odList;
	}
	
	//판매자 주문현황 상세보기
	@Transactional
	public SellerOrderDetailDto getSellerOrderDetail(Long orderNum, Long prodNum) {
		Optional<OrderDetail> optO = sr.findByOrder(orderNum, prodNum);
		SellerOrderDetailDto dto = SellerOrderDetailDto.builder()
				.prodNum(optO.get().getProduct().getProdNum())
				.prodCnt(optO.get().getProdCnt())
		        .user(optO.get().getOrder().getUser().getName())
		        .name(optO.get().getProduct().getName())
		        .orderNum(optO.get().getOrder().getOrderNum())
		        .week(optO.get().getProduct().getWeek())
		        .receiveDate(optO.get().getOrder().getDelivery().getReceiveDate())
		        .msg(optO.get().getOrder().getMsg())
		        .totalPrice(optO.get().getOrder().getTotalPrice())
		        .createdDate(optO.get().getOrder().getCreatedDate())
		        .deliveryName(optO.get().getOrder().getDelivery().getName())
		        .deliveryTel(optO.get().getOrder().getDelivery().getTel())
		        .deliveryMsg(optO.get().getOrder().getDelivery().getDeliveryMsg())
		        .deliveryAddr(optO.get().getOrder().getDelivery().getAddr())
				.build();
		return dto;	
	}
	
	//판매자 info
	@Transactional
	public SellerInfoResponseDto getSellerInfo(Authentication user){
		String userId = user.getName();
		Optional<Seller> optS = sr.findById(userId);
		List<Product> list = sr.findAllBySellerId(userId);
		List<Qna> qList = new ArrayList<>();
		List<Product> pList = new ArrayList<>();
		LocalDate now = LocalDate.now();

		int qnaCnt = 0;
		Long orderCnt = 0L;
		//미답변 문의 건 수
		for(Product p : list) {
			p.getQnas();
			for(Qna q : p.getQnas()) {
				if(q.getAnsCreatedDate() == null) {
					qnaCnt++;
					qList.add(q);
				}
			}
		}
		//주문 건 수
		for(Product p : list) {
			p.getOrderDetails();
			for(OrderDetail od : p.getOrderDetails()) {
				od.getOrder().getCreatedDate().toLocalDate();
				if(od.getOrder().getCreatedDate().toLocalDate().isEqual(now)) {
					orderCnt++;
					pList.add(p);
				}
			}
		}
		SellerInfoResponseDto dto = SellerInfoResponseDto.builder()
				.companyName(optS.get().getCompanyName())
				.followCnt(optS.get().getFollowCnt())
				.qnaCnt(qnaCnt)
				.orderCnt(orderCnt)
				.build();
		return dto;
	}	

	//판매자 탈퇴
	@Transactional
	public void removeSeller(Authentication user) {
		String userId = user.getName();
		List<Product> prodList = sr.findAllBySellerId(userId);
		for(Product p : prodList) {
			if(p.getStatus() != 2 ) {
				log.info("판매중인 상품이 있습니다. 탈퇴신청 불가");
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
			} 	
		}
		sr.updateSellerId(3, userId);
	}
	
	//판매자 상품목록 조회
	@Transactional
	public List<SellerProductResponseDto> getAllSellerProductList(Authentication user){
		String userId = user.getName();
		List<Product> list = sr.findProductBySellerId(userId);
		List<SellerProductResponseDto> prodlist = new ArrayList<>();
		if(list.size() == 0) {
			log.info("등록된 상품이 없습니다.");
			throw new NoResourceException(ErrorCode.RESOURCE_NOT_FOUND, "NOT_FOUND_PRODUCT");
		}else {
			for(Product prod : list) {
				SellerProductResponseDto dto = SellerProductResponseDto.builder()
						.prodNum(prod.getProdNum())
						.prodName(prod.getName())
				        .prodPrice(prod.getOriginPrice())
				        .prodPercentage(prod.getPercentage())
				        .reviewCnt(prod.getReviewCnt())	
				        .prodImgUrl(prod.getProductImgUrl())
				        .status(prod.getStatus())
				        .modifiedDate(prod.getModifiedDate())
						.build();
				prodlist.add(dto);
			}
		}
		return prodlist;
	}
}
