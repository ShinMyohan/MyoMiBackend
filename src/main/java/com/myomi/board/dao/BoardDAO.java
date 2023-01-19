package com.myomi.board.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.myomi.board.vo.BoardVo;
import com.myomi.exception.FindException;



/*
 *  상품목록을 검색한다
 *  @param startRow 시작행
 *  @param endRow 끝행
 *  @return 상품목록
 *  @throws 상품목록 검색시 FindException 예외 발생 (오타, 아무것도 안적음 등등...)
 */
public interface BoardDAO {
	public List<BoardVo> selectAll() throws FindException; //사용자 정의 익셉션을 사용하자!
	//만약 throws SQL익셉션이나 최상위 익셉션을 쓰는 경우 바람직하지 않다.


	public List<Map<String, Object>> selectDetail(int num) throws FindException;



	public List<Map<String, Object>> selectByTitle(String title) throws FindException;


	
	public List<Map<String, Object>> selectByCategory(String category, String title) throws FindException;


	public void insertBoard(int num, String userId, String category, String title, String content, Date createdDate,int hits) throws FindException;


	public void updateBoard(String category,String title, String content, int num) throws FindException;



	
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
