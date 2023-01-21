package com.myomi.board.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.myomi.board.vo.BoardVo;
import com.myomi.exception.FindException;

public interface BoardDAO {
	//리스트 전체 출력 
	public List<BoardVo> selectAll() throws FindException; 
	//사용자 정의 익셉션을 사용하자!
	//만약 throws SQL익셉션이나 최상위 익셉션을 쓰는 경우 바람직하지 않다.

	//제목으로 검색 
	public List<Map<String, Object>> selectByTitle(String title) throws FindException;

	
	//카테고리+ 제목 일부키워드로 검색 
	public List<Map<String, Object>> selectByCategory(String category, String title) throws FindException;
	
	
	//글 상세내용 조회 
	public List<Map<String, Object>> selectDetail(int num) throws FindException;

	
	//글 작성 
	public void insertBoard(BoardVo bVo) throws FindException;


	//글 삭제 
	public void deleteBoard (int num)throws FindException;

	
	//글 수정 
	public void updateBoard(BoardVo vo) throws FindException;

	
}
