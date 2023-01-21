package com.myomi.product.dao;

import java.util.List;
import java.util.Map;

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
	public int totalCntProduct() throws FindException {
		SqlSession session = sqlSessionFactory.openSession();
		int totalCnt = session.selectOne("ProductMapper,totalCntProduct");
		
		System.out.println("총 상품 갯수는 " + totalCnt + "개 입니다.");
		return totalCnt;
		
	}

	@Override
	public List<Map<String, Object>> selectAllProduct() throws FindException {
		SqlSession session = sqlSessionFactory.openSession();
		List<Map<String, Object>> list = session.selectList("ProductMapper.selectAllProduct", null);

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
	public List<Map<String, Object>> selectProductByCategory(String category) throws FindException {
		SqlSession session = sqlSessionFactory.openSession();
		List<Map<String, Object>> output = session.selectList("ProductMapper.selectProductByCategory", category);

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
	public List<Map<String, Object>> selectProductByWeek(int week) throws FindException {
		SqlSession session = sqlSessionFactory.openSession();
		List<Map<String, Object>> output = session.selectList("ProductMapper.selectProductByWeek", week);

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
	public List<Map<String, Object>> selectOneProduct(int num) throws FindException {
//	public List<Map<String, Object>> selectOneProduct(@Param("num") int num) throws FindException {
		SqlSession session = sqlSessionFactory.openSession();
		List<Map<String, Object>> output = session.selectList("ProductMapper.selectOneProduct", num);

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
	public int selectCntProductBySeller(String seller) throws FindException {
		SqlSession session = sqlSessionFactory.openSession();
		int cnt = session.selectOne("ProductMapper.selectCntProductBySeller", seller);
		
		System.out.println("판매자 " + seller + "님의 상품 갯수는 " + cnt + "개 입니다.");
		
		session.close();
		return cnt;
	}

	@Override
	public void insertProduct(ProductVo pVo) throws FindException {
		SqlSession session = sqlSessionFactory.openSession();
		
		session.insert("ProductMapper.insertProduct", pVo);
		
		System.out.println(pVo.toString());
		session.commit();
		session.close();
	}
	
	@Override
	public void updateProduct(ProductVo pVo) throws FindException {
		SqlSession session = sqlSessionFactory.openSession();
		
		session.update("ProductMapper.updateProduct", pVo);
		
		System.out.println(pVo.toString());
		session.commit();
		session.close();
	}

//	public static void main(String[] args) throws FindException {
//		ProductDAOOracle dao = new ProductDAOOracle();
		//필요하신거 하나씩 주석풀고 실행하시면 됩니다.
		
		//상품 목록
//		dao.selectAllProduct();
		
		//상품 카테고리별 목록 
		//dao.selectProductByCategory("도시락");

		//상품 배송주차별 목록 
		//dao.selectProductByWeek(1);
		
		//상품 상세 조회 + 리뷰 + 베스트리뷰
		//dao.selectOneProduct(1);
		
		//insert
//		ProductVo pVo = new ProductVo();
//		SellerVo sVo = new SellerVo();
//		UserVo uVo = new UserVo();
//		uVo.setId("user3"); //나중에 세션에 들어감
//		sVo.setUser(uVo);
//		pVo.setSeller(sVo);
//		pVo.setCategory("샐러드");
//		pVo.setName("토마토맛 샐러드");
//		pVo.setOriginPrice(66400);
//		pVo.setPercentage(32);
//		pVo.setWeek(6);
//		pVo.setStatus(0);
//		pVo.setDetail("");
//		pVo.setFee(9);
//		dao.insertProduct(pVo);
		
		//update
//		ProductVo pVo = new ProductVo();
//		pVo.setNum(5);
//		pVo.setName("완전 맛있는 도시락");
//		pVo.setCategory("도시락");
//		pVo.setDetail("새우 완전 통통");
//		dao.updateProduct(pVo);
//		dao.selectCntProductBySeller("user1");
		//dao.totalCntProduct();
//	}
}
