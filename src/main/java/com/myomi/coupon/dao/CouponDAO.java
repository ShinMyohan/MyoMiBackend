package com.myomi.coupon.dao;

import java.util.List;
import java.util.Map;

import com.myomi.coupon.vo.CouponVo;
import com.myomi.exception.FindException;

public interface CouponDAO {
	public List<Map<String,Object>> selectCouponByUserId(String userId) throws FindException;
	
	public void updateCouponStatus(CouponVo cVo) throws FindException;
}
