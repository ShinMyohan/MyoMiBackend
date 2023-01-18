package com.myomi.product.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.myomi.exception.FindException;
import com.myomi.product.vo.ProductVo;
import com.myomi.resource.Factory;

public class ProductDAOOracle implements ProductDAO {
	private SqlSessionFactory sqlSessionFactory;

	public ProductDAOOracle() {
		sqlSessionFactory = Factory.getSqlSessionFactory();
	}

	@Override
	public List<Map<String, Object>> selectAll() throws FindException {
		SqlSession session = sqlSessionFactory.openSession();
		List<Map<String, Object>> list = session.selectList("ProductMapper.selectAllProds", null);

		if (list == null) {
			System.out.println("조회결과 없음");
		} else {
			for (Map<String, Object> prod : list) {
				System.out.println(prod.toString());
			}
		}

		session.close();
		return list;
	}

	@Override
	public List<Map<String, Object>> selectByCategory(String category) throws FindException {
		SqlSession session = sqlSessionFactory.openSession();
		List<Map<String, Object>> output = session.selectList("ProductMapper.selectProdByCategory", category);

		if (output == null) {
			System.out.println("조회결과 없음");
		} else {
			for (Map<String, Object> prod : output) {
				System.out.println(prod.toString());
			}
		}

		session.close();
		return output;
	}

	@Override
	public List<Map<String, Object>> selectByWeek(int week) throws FindException {
		SqlSession session = sqlSessionFactory.openSession();
		List<Map<String, Object>> output = session.selectList("ProductMapper.selectProdByWeek", week);

		if (output == null) {
			System.out.println("조회결과 없음");
		} else {
			for (Map<String, Object> prod : output) {
				System.out.println(prod.toString());
			}
		}

		session.close();
		return output;
	}

	@Override
	public List<Map<String, Object>> selectOne(@Param("num") int num) throws FindException {
		SqlSession session = sqlSessionFactory.openSession();
		List<Map<String, Object>> output = session.selectList("ProductMapper.selectOne", num);

		if (output == null) {
			System.out.println("조회결과 없음");
		} else {
			for (Map<String, Object> prod : output) {
				System.out.println(prod.toString());
			}
		}

		session.close();
		return output;
	}
	
	@Override
	public int selectCntBySeller(String seller) throws FindException {
		SqlSession session = sqlSessionFactory.openSession();
		int cnt = session.selectOne("ProductMapper.selectCntProds", seller);
		
		System.out.println("판매자 " + seller + "님의 상품 갯수는 " + cnt + "개 입니다.");
		return cnt;
	}

	@Override
	public void insert(ProductVo pVo) throws FindException {
		SqlSession session = sqlSessionFactory.openSession();
		
		session.insert("ProductMapper.insertProd", pVo);
		
		System.out.println(pVo.toString());
		session.commit();
		session.close();
	}
	
	@Override
	public void update(ProductVo pVo) throws FindException {
		SqlSession session = sqlSessionFactory.openSession();
		
		session.update("ProductMapper.updateProd", pVo);
		
		System.out.println(pVo.toString());
		session.commit();
		session.close();
	}

	public static void main(String[] args) throws FindException {
		ProductDAOOracle dao = new ProductDAOOracle();
		//필요하신거 하나씩 주석풀고 실행하시면 됩니다.
		
		//상품 목록
		//dao.selectAll();
		
		//상품 카테고리별 목록 
		//dao.selectByCategory("도시락");

		//상품 배송주차별 목록 
		//dao.selectByWeek(1);
		
		//상품 상세 조회 + 리뷰 + 베스트리뷰
		//dao.selectOne(1);
		
		//insert
//		ProductVo pVo = new ProductVo();
//		SellerVo sVo = new SellerVo();
//		UserVo uVo = new UserVo();
//		uVo.setId("user1"); //나중에 세션에 들어감
//		sVo.setUser(uVo);
//		pVo.setSeller(sVo);
//		pVo.setCategory("테카테고리3");
//		pVo.setName("테스트상품명3");
//		pVo.setOriginPrice(51200);
//		pVo.setPercentage(20);
//		pVo.setWeek(2);
//		pVo.setStatus(0);
//		pVo.setDetail("");
//		pVo.setFee(9);
//		dao.insert(pVo);
		
		//update
//		ProductVo pVo = new ProductVo();
//		pVo.setNum(5);
//		pVo.setName("완전 맛있는 도시락");
//		pVo.setCategory("도시락");
//		pVo.setDetail("새우 완전 통통");
//		dao.update(pVo);
//		dao.selectCntBySeller("user1");
	}


}