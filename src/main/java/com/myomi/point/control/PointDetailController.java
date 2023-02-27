package com.myomi.point.control;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
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
	public ResponseEntity<?> myPointList(Authentication user,
			@PageableDefault(size=4) Pageable pageable) {
		 List<PointDetailDto> list = service.findMyPointList(user, pageable);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@PostMapping("point")
	public ResponseEntity<?> pointAdd(@RequestBody PointDetailDto pDto,
			 Authentication user) {
		ResponseEntity<PointDetailDto> dto = service.addPoint(pDto, user);
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}
	
	@GetMapping("point")
	public ResponseEntity<?> findTotalpoint (Authentication user) {
		PointDto dto = service.findTotalPoint(user);
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}
	
}
