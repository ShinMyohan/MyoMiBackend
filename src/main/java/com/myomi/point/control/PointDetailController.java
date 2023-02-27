package com.myomi.point.control;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.myomi.point.dto.PointDetailDto;
import com.myomi.point.dto.PointDto;
import com.myomi.point.service.PointDetailService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PointDetailController {

	private final PointDetailService service;
	
	//마이페이지에서 포인트 내역 조회 
	@GetMapping("mypage/pointDetail")
	public List<PointDetailDto> myPointList(Authentication user,
			@PageableDefault(size=4) Pageable pageable) {
		return service.findMyPointList(user, pageable);
	}
	
	@PostMapping("point")
	public ResponseEntity<PointDetailDto> pointAdd(@RequestBody PointDetailDto pDto,
			 Authentication user){
		return service.addPoint(pDto, user);
	}
	
	@GetMapping("point")
	public PointDto findTotalpoint (Authentication user) {
		return service.findTotalPoint(user);
	}
	
}
