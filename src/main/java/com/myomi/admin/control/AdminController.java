package com.myomi.admin.control;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myomi.admin.dto.SellerDetailDto;
import com.myomi.admin.dto.SellerResponseDto;
import com.myomi.admin.service.AdminService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/adminpage/")
public class AdminController {
	@Autowired
	private final AdminService service;
	
	//판매자 신청명단 목록 조회
	@GetMapping("sellerlist")
	public List<SellerResponseDto> SellerList(
			){
		return service.findSeller();
		
	}
	
	//판매자 신청상태 status로 조회
	@GetMapping("sellerlist/{status}")
	public List<SellerResponseDto> sellerListByStatus(@PathVariable int status){
		return service.findSellerListByStatus(status);
	}
	//판매자 승인
	
	//판매자 승인 반려 혹은 거절

	
	//판매자 상세보기
	@GetMapping("detail/{sellerId}")
	public SellerDetailDto sellerDetail(@PathVariable String sellerId){
		return service.detailSellerInfo(sellerId);
	}
	
}
