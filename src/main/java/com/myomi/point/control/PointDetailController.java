package com.myomi.point.control;

import com.myomi.point.dto.MyPageDto;
import com.myomi.point.dto.PointDetailDto;
import com.myomi.point.dto.PointDto;
import com.myomi.point.service.PointDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@Api(tags = "포인트")
public class PointDetailController {

	private final PointDetailService service;
	@ApiOperation(value = "마이페이지 | 포인트 목록 보기 ")
	@GetMapping("mypage/pointDetail")
	public ResponseEntity<?> myPointList(Authentication user,
			@PageableDefault(sort = "pointEmbedded.createdDate", direction = Direction.DESC)Pageable pageable) {
		List<PointDetailDto> list = service.findMyPointList(user, pageable);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@ApiOperation(value = "포인트 | 포인트 적립")
	@PostMapping("point")
	public ResponseEntity<?> pointAdd(@RequestBody PointDetailDto pDto,
			Authentication user) {
		ResponseEntity<PointDetailDto> dto = service.addPoint(pDto, user);
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	@GetMapping("point")
	@ApiOperation(value = "포인트 | 총 포인트 보기 ")
	public ResponseEntity<?> getTotalpoint (Authentication user) {
		PointDto dto = service.findTotalPoint(user);
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	@ApiOperation(value = "마이페이지 | 상단메뉴 정보")
	@GetMapping("/headerinfo")
	public MyPageDto mypageInfo (Authentication user) {
		return service.getMyPageInfo(user);
	}

}
