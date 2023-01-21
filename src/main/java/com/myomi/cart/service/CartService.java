package com.myomi.cart.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.json.simple.JSONObject;

import com.myomi.cart.dao.CartDAOOracle;
import com.myomi.cart.vo.CartVo;
import com.myomi.exception.FindException;
import com.myomi.product.vo.ProductVo;
import com.myomi.user.vo.UserVo;

public class CartService {
	CartDAOOracle cDao = new CartDAOOracle();
	UserVo uVo = new UserVo();
	ProductVo pVo = new ProductVo();
	CartVo cVo = new CartVo();
	
	// ------ 장바구니에 상품 추가 -------
	public void addCart(JSONObject jsonObject) throws FindException {
		uVo.setId(jsonObject.get("id").toString());
		cVo.setUser(uVo);
		pVo.setNum(Integer.parseInt(jsonObject.get("prodNum").toString()));
		cVo.setProduct(pVo);
		cVo.setProdCnt(Integer.parseInt(jsonObject.get("prodCnt").toString()));
		
		cDao.insertCart(cVo);
	}
	
	// ------ 장바구니 상품 목록 조회 -------
	public List<Map<String, Object>> findAllCart(String userId) throws FindException {
		return cDao.selectAllCart(userId);
	}
	
	// ------ 장바구니 상품 수량 수정 -------
	public void modifyCart(JSONObject jsonObject) throws FindException {
		cVo.setNum(Integer.parseInt(jsonObject.get("num").toString()));
		cVo.setProdCnt(Integer.parseInt(jsonObject.get("prodCnt").toString()));
		
		cDao.updateCart(cVo);
	}
	
	// ------ 장바구니에서 상품 한개 또는 여러개 삭제 -------
	public void removeCart(String str) throws FindException {
		//RemoveController에서 넘어온 str 문자열을 , 기준으로 나누어 문자열 -> int 배열로 변환해줍니다.
		int[] digits = Stream.of(str.split(",")).mapToInt(Integer::parseInt).toArray();
		
		//Integer 타입을 담는 리스트를 객체를 하나 생성하여
		List<Integer> list = new ArrayList<>();
		//digits 정수 배열에서 값을 하나씩 뽑아
		for(int n : digits) {
			// 리스트 객체 list에 하나씩 추가해줍니다.
			list.add(n);
		}
		//그걸 delete!
		cDao.deleteCart(list);
	}
}
