package com.myomi.seller.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.myomi.order.entity.OrderDetail;
import com.myomi.order.repository.OrderRepository;
import com.myomi.product.entity.Product;
import com.myomi.product.repository.ProductRepository;
import com.myomi.qna.entity.Qna;
import com.myomi.seller.dto.SellerAddRequestDto;
import com.myomi.seller.dto.SellerInfoResponseDto;
import com.myomi.seller.dto.SellerOrderDetailDto;
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
				.bank_account(addDto.getBankAccount())
				.build();
		sr.save(seller);

		return new ResponseEntity<>(HttpStatus.OK);
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
				.companyNum(seller.get().getCompanyName())
				.internetNum(seller.get().getInternetNum())
				.addr(seller.get().getAddr())
				.manager(seller.get().getManager())
				.status(seller.get().getStatus())
				.bankAccount(seller.get().getBank_account())
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
			 			.user(od.getOrder().getUser().getId())
						.week(od.getProduct().getWeek())
						.receiveDate(od.getOrder().getDelivery().getReceiveDate())
						.msg(od.getOrder().getMsg())
						.prodCnt(od.getProdCnt())
			            .createdDate(od.getOrder().getCreatedDate())
			            .deliveryName(od.getOrder().getDelivery().getName())
			            .deliveryTel(od.getOrder().getDelivery().getTel())
			            .deliveryAddr(od.getOrder().getDelivery().getAddr())
			            .deliveryMsg(od.getOrder().getDelivery().getDeliveryMsg())
						.build();
			
				odList.add(dto);
			}
		}
		return odList;
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
			if(p.getStatus() == 0) {
				log.info("판매중인 상품이 있습니다. 탈퇴신청 불가");
			} else {
				sr.updateSellerId(userId);
			}
		}	
	}
	
	
}
