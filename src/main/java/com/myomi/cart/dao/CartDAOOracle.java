package com.myomi.cart.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.myomi.cart.vo.CartVo;
import com.myomi.exception.FindException;
import com.myomi.resource.Factory;

public class CartDAOOracle implements CartDAO{
	private SqlSessionFactory sqlSessionFactory;
	
	public CartDAOOracle() {
		sqlSessionFactory = Factory.getSqlSessionFactory();
	}
	
	//------- 장바구니에 상품 등록 -------
	@Override
	public void insertCart(CartVo cVo) throws FindException {
		SqlSession session = sqlSessionFactory.openSession();
		
		session.insert("CartMapper.insertCart", cVo);
		
		System.out.println(cVo.toString());
		session.commit();
		session.close();
	}
	
	//------- 장바구니에 있는 상품 목록 조회 -------
	@Override
	public List<Map<String, Object>> selectAllCart(String userId) throws FindException {
		SqlSession session = sqlSessionFactory.openSession();
		List<Map<String, Object>> output = session.selectList("CartMapper.selectAllCart", userId);
		
		if(output == null) {
			System.out.println("조회결과 없음");
		} else {
			for(Map<String, Object> cart : output) {
				System.out.println(cart.toString());
			}
		}
		
		session.close();
		return output;
	}
	
	//------- 장바구니 상품 수량 수정 -------
	@Override
	public void updateCart(CartVo cVo) throws FindException {
		SqlSession session = sqlSessionFactory.openSession();
		
		session.update("CartMapper.updateCart", cVo);
		System.out.println(cVo.toString());
		session.commit();
		session.close();
	}
	
	//------- 장바구니 상품 삭제 -------
	@Override
	public void deleteCart(List<Integer> numbers) throws FindException {
//	public void deleteCart(String numbers) throws FindException {
		SqlSession session = sqlSessionFactory.openSession();
		
		int i = session.delete("CartMapper.deleteCart", numbers);
		System.out.println(i+"개 상품이 삭제되었습니다");
		
		session.commit();
		session.close();
	}
	
	public static void main(String[] args) throws FindException {
		CartDAOOracle dao = new CartDAOOracle();
//		
		//------insert
//		CartVo cVo = new CartVo();
//		UserVo uVo = new UserVo();
//		ProductVo pVo = new ProductVo();
//		
//		uVo.setId("user2");
//		cVo.setUser(uVo);
//		
//		pVo.setNum(1);
//		cVo.setProduct(pVo);
//		
//		cVo.setProdCnt(2);
//		
//		dao.insertCart(cVo);
		
		//------selectAllCart
		dao.selectAllCart("user4");
		
		//------updateCart
//		CartVo cVo = new CartVo();
//		cVo.setNum(3);
//		cVo.setProdCnt(3);
//		
//		dao.updateCart(cVo);
		
		//------deleteCart
//		List<Integer> numbers = new ArrayList<>();
//		String str = "4,5";
//		int[] digits = Stream.of(str.split(",")).mapToInt(Integer::parseInt).toArray();
//		
//		for(int d : digits) {
//			numbers.add(d);
//		}
//		dao.deleteCart(numbers); 
	}
}
