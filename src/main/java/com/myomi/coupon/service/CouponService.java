package com.myomi.coupon.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.myomi.coupon.dto.CouponDto;
import com.myomi.coupon.entity.Coupon;
import com.myomi.coupon.repository.CouponRepository;
import com.myomi.exception.AddException;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Getter
public class CouponService {
	private final CouponRepository cr;

	//마이페이지에서 쿠폰 조회 
	@Transactional
	public List<CouponDto> findCouponList (Authentication user, Pageable pageable){
		LocalDateTime date = LocalDateTime.now();
		String username = user.getName();
		List <Coupon> list = cr.findAllByUser(username,pageable);
		List<CouponDto> couponList = new ArrayList<>();
		for(Coupon c : list) {
			CouponDto dto = CouponDto.builder()
					.userId(username)
					.couponNum(c.getCouponNum())
					.sort(c.getSort())
					.percentage(c.getPercentage())
					.createdDate(date)
					.status(c.getStatus())
					.usedDate(c.getUsedDate())
					.build();
			couponList.add(dto);
		}
		return couponList;
	}

	//쿠폰 사용시 status update 
	@Transactional
	public void modifyCoupon (CouponDto dto,
			Long couponNum) throws AddException{
		LocalDateTime date = LocalDateTime.now();
		Optional<Coupon> cp = cr.findById(couponNum);
		Coupon coupon = cp.get();
		if (coupon.getUsedDate()==null && dto.getStatus() == 1) {
				//쿠폰은 status 1(사용됨) 으로만 변경가능, 2(만료)는 프로시저
				coupon.update(date, dto.getStatus());
			}else {
				throw new AddException("권한이 없습니다");
			}
		}
	}
