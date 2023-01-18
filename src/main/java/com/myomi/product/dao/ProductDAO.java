package com.myomi.product.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.myomi.exception.FindException;
import com.myomi.product.vo.ProductVo;

/*
 *  상품목록을 검색한다
 *  @param startRow 시작행
 *  @param endRow 끝행
 *  @return 상품목록
 *  @throws 상품목록 검색시 FindException 예외 발생 (오타, 아무것도 안적음 등등...)
 */
public interface ProductDAO {
	public List<Map<String, Object>> selectAll() throws FindException; //사용자 정의 익셉션을 사용하자!
	//만약 throws SQL익셉션이나 최상위 익셉션을 쓰는 경우 바람직하지 않다.
	
	/**
	 * 카테고리로 상품 목록을 본다(반환)
	 * @return 카테고리별 상품 목록
	 * @throws Exception //DAOOracle 오버라이딩하러 고고
	 */
	public List<Map<String, Object>> selectByCategory(String category) throws FindException;
	
	/**
	 * 배송주차로 상품 목록을 본다(반환)
	 * @return 주차별 상품 목록
	 * @throws Exception //DAOOracle 오버라이딩하러 고고
	 */
	public List<Map<String, Object>> selectByWeek(int week) throws FindException;
	
	/**
	 * 상품 하나 상세보기
	 * @param num
	 * @return
	 * @throws FindException
	 */
//	public List<Map<String, Object>> selectOne(int num) throws FindException;
	public List<Map<String, Object>> selectOne(@Param("num")int num) throws FindException;
	
	/**
	 * 셀러당 상품 갯수
	 * @param seller
	 * @return 상품 갯수
	 * @throws FindException
	 */
	public int selectCntBySeller(String seller) throws FindException;
	
	
	/**
	 * 상품 등록
	 * @param pVo 
	 * @throws FindException
	 */
	public void insert(ProductVo pVo) throws FindException;
	
	/**
	 * 상품 정보 업데이트 (상품명, 상품 주문가능상태, 상품 특이사항)
	 * @param pVo
	 * @throws FindException
	 */
	public void update(ProductVo pVo) throws FindException;
}
