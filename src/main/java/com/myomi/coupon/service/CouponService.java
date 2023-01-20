package com.myomi.coupon.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import java.util.List;
import java.util.Map;

import com.myomi.coupon.dao.CouponDAO;
import com.myomi.coupon.dao.CouponDAOOracle;
import com.myomi.coupon.vo.CouponVo;
import com.myomi.exception.FindException;
import com.myomi.user.vo.UserVo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CouponService {
	CouponDAO dao = new CouponDAOOracle();

	// 본인 쿠폰 목록 조회 (마이페이지, 주문시)
	public List<Map<String, Object>> findCouponList(String userId) throws FindException {
		List<Map<String, Object>> couponList = dao.selectCouponByUserId(userId);
		// 사용가능한 쿠폰만 담을 map list
		List<Map<String, Object>> availableCoupon = new ArrayList<Map<String, Object>>();
		
		// oracle에서 뽑아오는형식 그대로 가져와야함.. 그냥 yyyy-MM-dd나 HH:mm:ss.SSS 처럼 내가 원하는 형식으로는 안받아와짐..
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S"); 
		String now = LocalDateTime.now().format(formatter);
		LocalDateTime today = LocalDateTime.parse(now, formatter);

		// dao에서 받아온 쿠폰 리스트 중에 만료된 쿠폰은 status = 2 로 변경해주는 메서드를 불러와서 처리해줌
		for (int i = 0; i <= couponList.size()-1; i++) {
			String date = couponList.get(i).get("CREATED_DATE").toString();
			LocalDateTime couponDate = LocalDateTime.parse(date, formatter);

			LocalDateTime couponExpired = couponDate.plusDays(7);
			System.out.println("couponExpiredDate : " + couponExpired);
			if (couponExpired.isBefore(today)) {
				int couponNum = Integer.parseInt(couponList.get(i).get("NUM").toString());
				System.out.println(couponNum + "번 쿠폰이 만료됨");
				modifyCouponStauts(userId, couponNum, 2);
			}

			// status = 0 인것만 새로운 객체에 담아주기
			int status = Integer.parseInt(couponList.get(i).get("STATUS").toString());
			if (status == 0) {
				Map<String, Object> coupon = couponList.get(i);
				availableCoupon.add(coupon);
			}
		}
		return availableCoupon;
	}


	// 쿠폰 status 변경
	public void modifyCouponStauts(String userId, int num, int status) throws FindException {
		CouponVo cVo = new CouponVo();
		UserVo uVo = new UserVo();
		uVo.setId(userId);
		cVo.setUser(uVo);
		cVo.setNum(num);
		cVo.setStatus(status);
		dao.updateCouponStatus(cVo);
	}
}
