package com.myomi.seller.service;

import com.myomi.order.entity.OrderDetail;
import com.myomi.order.repository.OrderRepository;
import com.myomi.product.entity.Product;
import com.myomi.product.repository.ProductRepository;
import com.myomi.qna.entity.Qna;
import com.myomi.seller.dto.*;
import com.myomi.seller.entity.Seller;
import com.myomi.seller.repository.SellerRepository;
import com.myomi.user.entity.User;
import com.myomi.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SellerService {
	private final SellerRepository sr;
	private final UserRepository ur;
	private final OrderRepository or;
	private final ProductRepository pr;
	/* TODO : 1.판매자로 신청하기@
	 *        2.판매자 신청현황 조회하기(승인대기,승인완료,승인불가,신청상세) @
	 *        3.판매자 주문현황 조회 @
	 *        4.판매자 info(팔로우 수, 미답변 문의 건 수, 주문 건 수 조회)@
	 *        5.판매자 탈퇴(조건만족시)
	 */

	//판매자로 신청
	@Transactional
	public ResponseEntity<SellerAddRequestDto> addSeller(SellerAddRequestDto addDto, Authentication user){
		String userId = user.getName();
		Optional<User> optU = ur.findById(userId);
		Seller seller = Seller.builder()
				.sellerId(optU.get())
				.companyName(addDto.getCompanyName())
				.companyNum(addDto.getCompanyNum())
				.internetNum(addDto.getInternetNum())
				.addr(addDto.getAddr())
				.manager(addDto.getManager())
		        .bankAccount(addDto.getBankAccount())
				.build();
		sr.save(seller);

		return new ResponseEntity<>(HttpStatus.OK);
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
	public List<SellerProductResponseDto> getAllSellerProductList(Authentication user,Pageable pageable){
		String userId = user.getName();
		List<Product> list = sr.findProductBySellerId(userId, pageable);
		List<SellerProductResponseDto> prodlist = new ArrayList<>();
		if(list.size() == 0) {
			log.info("등록된 상품이 없습니다.");
		}else {
			for(Product prod : list) {
				SellerProductResponseDto dto = SellerProductResponseDto.builder()
						.prodNum(prod.getProdNum())
						.prodName(prod.getName())
				        .prodPrice(prod.getOriginPrice())
				        .prodPercentage(prod.getPercentage())
				        .reviewCnt(prod.getReviewCnt())						
						.build();
				prodlist.add(dto);
			}
		}
		return prodlist;
	}
}
