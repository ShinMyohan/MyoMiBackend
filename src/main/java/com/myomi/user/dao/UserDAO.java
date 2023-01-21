package com.myomi.user.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.myomi.exception.AddException;
import com.myomi.exception.FindException;
import com.myomi.user.vo.UserVo;

/*
 *  상품목록을 검색한다
 *  @param startRow 시작행
 *  @param endRow 끝행
 *  @return 상품목록
 *  @throws 상품목록 검색시 FindException 예외 발생 (오타, 아무것도 안적음 등등...)
 */

public interface UserDAO {
	
	//회원 전체 조회
	public List<UserVo> selectAllUser() throws FindException;
	//회원가입
	public void insertUser (String id, String pwd, String name, String tel, String email, String addr, int role, int membership, Date createdDate, Date signoutDate)throws AddException;
	
	//로그인 하기
	public UserVo selectLoginUser (UserVo uVo) throws FindException;
	
	//아이디 찾기
	public String selectFindIdUser (String email) throws FindException;
	
	//비밀번호 찾기
	public Map<String, String> selectFindPwdUser (String id, String email) throws FindException;
	
	//회원 탈퇴일자 업데이트
	public void updateSignoutUser(String id) throws AddException;
	
	//내정보 변경하기
	public void updateEditUser(UserVo uVo) throws AddException;
	
	//내가쓴 상품리뷰 목록 조회하기
	public List<Map<String,Object>> selectReviewByUser(String id) throws FindException;
	
	//내가쓴 상품리뷰 목록 제목으로 조회하기 (확인필요)
	public List<Map<String,Object>> selectReviewFindTitleUser(String title, String id) throws FindException;
	
	//마이페이지 상단,주문목록,주문상세
//    public List<Map<String,Object>> select
	

	

	
}
