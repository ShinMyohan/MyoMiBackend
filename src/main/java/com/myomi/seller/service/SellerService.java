package com.myomi.seller.service;

import org.json.simple.JSONObject;

import com.myomi.exception.AddException;
import com.myomi.seller.dao.SellerDAO;
import com.myomi.seller.dao.SellerDAOOracle;
import com.myomi.seller.vo.SellerVo;
import com.myomi.user.vo.UserVo;

public class SellerService {
	SellerDAO dao = new SellerDAOOracle();
	SellerVo sVo = new SellerVo();
	UserVo uVo = new UserVo();
	
	// 판매자로 등록
	public void addSellerInfo(JSONObject jsonObject) throws AddException {		
		uVo.setId(jsonObject.get("id").toString());
		sVo.setUser(uVo);
		sVo.setCompanyName(jsonObject.get("companyName").toString());
		sVo.setCompanyNum(jsonObject.get("companyNum").toString());
		sVo.setInternetNum(jsonObject.get("internetNum").toString());
		sVo.setAddr(jsonObject.get("addr").toString());
		sVo.setManager(jsonObject.get("manager").toString());
		
		dao.insertSellerInfo(sVo);
	}
	
	// 판매자의 상태를 변경
	public void modifySellerStatus(JSONObject jsonObject) throws AddException {
		uVo.setId(jsonObject.get("id").toString());
		sVo.setUser(uVo);
		sVo.setStatus(Integer.parseInt(jsonObject.get("status").toString()));
		
		dao.updateSellerStatus(sVo);
	}
}
