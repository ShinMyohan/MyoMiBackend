package com.myomi.seller.dao;

import com.myomi.exception.AddException;
import com.myomi.seller.vo.SellerVo;

public interface SellerDAO {
	
	public void insertSellerInfo(SellerVo sVo) throws AddException;

	public void updateSellerStatus(SellerVo sVo) throws AddException;
	
}
