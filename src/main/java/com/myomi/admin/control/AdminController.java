package com.myomi.admin.control;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	@Autowired
	private final AdminService service;
	
	//판매자 신청명단 목록 조회
	@GetMapping("seller/list")
	public List<SellerResponseDto> sellerAllList(
			){
		return service.getAllSeller();
		
	}
	
	//판매자 신청상태 status로 조회
	@GetMapping("seller/list/{status}")
	public List<SellerResponseDto> sellerAllByStatus(@PathVariable int status){
		return service.getAllSellerStatus(status);
	}
	
	//판매자 강제탈퇴
	
	//판매자 승인,승인 반려 혹은 거절
//	public void sellerModify()
//	
	//판매자 상세보기
	@GetMapping("detail/{sellerId}")
	public SellerDetailDto sellerDetails(@PathVariable String sellerId){
		return service.detailSellerInfo(sellerId);
	}
	
}
