package com.myomi.coupon.control;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.myomi.coupon.dto.CouponDto;
import com.myomi.coupon.service.CouponService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor

@Slf4j
public class CouponController {
	private final CouponService service;

	//마이페이지에서 쿠폰 리스트 조회 
	@GetMapping("mypage/couponList")
	public List<CouponDto> myCouponList(Authentication user,
			@PageableDefault(size=4) Pageable pageable){
		return service.findCouponList(user,pageable);
	}
	
	//쿠폰 사용 (status update, usedDate update)
	//임시로 만들어둠 ..
	@PutMapping("coupon/{couponNum}")
	public void couponModify (@RequestBody CouponDto dto,
							Authentication user,
							@PathVariable Long couponNum) {
		service.modifyCoupon(dto,user,couponNum);
	}
	
	
}
