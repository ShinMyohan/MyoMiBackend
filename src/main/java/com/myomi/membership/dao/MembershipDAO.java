package com.myomi.membership.dao;

import java.util.List;

import com.myomi.exception.FindException;
import com.myomi.membership.vo.MembershipVo;

/*
 *  상품목록을 검색한다
 *  @param startRow 시작행
 *  @param endRow 끝행
 *  @return 상품목록
 *  @throws 상품목록 검색시 FindException 예외 발생 (오타, 아무것도 안적음 등등...)
 */
public interface MembershipDAO {
	public List<MembershipVo> selectAll() throws FindException; //사용자 정의 익셉션을 사용하자!
	//만약 throws SQL익셉션이나 최상위 익셉션을 쓰는 경우 바람직하지 않다.
	
	/**
	 * 총 상품수를 검색한다(반환)
	 * @return
	 * @throws Exception //DAOOracle 오버라이딩하러 고고
	 */
	
//	public int totalCnt() throws FindException;
//	
//	/**
//	 * 
//	 * @param prodNo 상품번호
//	 * @return 상품번호
//	 * @throws FindException
//	 */
//	public ProductVo selectByProdNo(int num) throws FindException;
}
