package com.myomi.admin.control;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myomi.admin.service.AdminService;
import com.myomi.seller.dto.SellerDetailDto;
import com.myomi.seller.dto.SellerResponseDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/admin/*")
public class AdminController {
	private final AdminService service;
	
	//판매자 신청명단 목록 조회
	@GetMapping("seller")
	public List<SellerResponseDto> sellerList(
			){
		return service.getAllSeller();
		
	}
	
	//판매자 신청상태 status로 조회
	@GetMapping("seller/{status}")
	public List<SellerResponseDto> sellerListByStatus(@PathVariable int status){
		return service.getAllSellerByStatus(status);
	}
	
	//판매자 승인
	@PutMapping("seller/{sellerId}/{status}")
	public void sellerStatusModify(@PathVariable String sellerId,@PathVariable int status) {
		service.modifySellerStatus(status,sellerId);
	}
	
	//판매자 강제탈퇴
	//판매자 상세보기
	@GetMapping("seller/detail/{sellerId}")
	public SellerDetailDto sellerDetail(@PathVariable String sellerId)
	{
		return service.getOneSellerInfo(sellerId);
	}
	
}
