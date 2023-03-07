package com.myomi.coupon.control;

import com.myomi.coupon.dto.CouponDto;
import com.myomi.coupon.service.CouponService;
import com.myomi.exception.FindException;
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
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@Api(tags = "쿠폰")

public class CouponController {
	private final CouponService service;

    @ApiOperation(value = "마이페이지 | 나의 쿠폰 리스트 ")
	@GetMapping("mypage/couponList")
	public ResponseEntity<?> myCouponList(Authentication user,
			@PageableDefault(sort = { "createdDate" }, direction = Direction.DESC) Pageable pageable) throws FindException{
		 List<CouponDto> list = service.findCouponList(user,pageable);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

    @GetMapping("coupon")
	@ApiOperation(value = "쿠폰 | 쿠폰 갯수 ")
	public ResponseEntity<?> getCouponCount (Authentication user) {
		Long coupon = service.getTotalCoupon(user);
		return new ResponseEntity<>(coupon, HttpStatus.OK);
	}
}
