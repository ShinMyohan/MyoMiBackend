package com.myomi.cart.dao;

import java.util.List;
import java.util.Map;

import com.myomi.cart.vo.CartVo;
import com.myomi.exception.FindException;

public interface CartDAO {
	//--------- 장바구니에 상품 등록 ---------
	public void insertCart(CartVo cVo) throws FindException;
	
	//--------- 장바구니에 상품 목록 조회 ---------
	public List<Map<String, Object>> selectAllCart(String userId) throws FindException;
	
	//--------- 장바구니에 상품 수량 수정 ---------
	public void updateCart(CartVo cVo) throws FindException;

	//--------- 장바구니에서 상품 삭제 ---------
	public void deleteCart(List<Integer> numbers) throws FindException;
}
