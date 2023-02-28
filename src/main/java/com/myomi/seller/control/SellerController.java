package com.myomi.seller.control;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.myomi.seller.dto.SellerAddRequestDto;
import com.myomi.seller.dto.SellerInfoResponseDto;
import com.myomi.seller.dto.SellerOrderDetailDto;
import com.myomi.seller.dto.SellerReadResponseDto;
import com.myomi.seller.service.SellerService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RestController
@RequiredArgsConstructor
public class SellerController {
	private final SellerService sellerService;
	
	//판매자로 신청하기
	@PostMapping("mypage/partner")
	public ResponseEntity<SellerAddRequestDto> sellerAdd(@RequestBody SellerAddRequestDto sellerAddRequestDto,Authentication user){
		return sellerService.addSeller(sellerAddRequestDto,user);
	}
	
	//판매자 신청현황 조회하기
	@GetMapping("mypage/partner")
	public SellerReadResponseDto sellerJoinDetail(Authentication user) {
		return sellerService.getDetailBySellerJoin(user);
	}
	
	//판매자 주문 현황 조회(상세포함)
	@GetMapping("sellerPage/order")
	public List<SellerOrderDetailDto> sellerOrderList(Authentication user,@PageableDefault(size=5) Pageable pageable){
		return sellerService.getAllSellerOrderList(user,pageable);
	}
	
	//판매자 info
	@GetMapping("sellerPage/")
	public SellerInfoResponseDto SellerInfo(Authentication user){
		return sellerService.getSellerInfo(user);
	}
	
	
	//판매자 탈퇴
	@PutMapping("sellerPage/")
	public void sellerDelete(Authentication user){
		sellerService.removeSeller(user);
	}
	
	

}
