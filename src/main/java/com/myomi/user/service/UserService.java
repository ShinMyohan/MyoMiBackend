package com.myomi.user.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.myomi.exception.AddException;
import com.myomi.exception.FindException;
import com.myomi.user.dao.UserDAO;
import com.myomi.user.dao.UserDAOOracle;
import com.myomi.user.vo.UserVo;

public class UserService {
	//회원 전체 조회
	public List<UserVo> findAllUser() throws FindException{
		UserDAO dao = new UserDAOOracle();
		return dao.selectAllUser();
	}
	//회원가입
	public void addUser (String id, String pwd, String name, String tel, String email, String addr, int role, int membership, Date createdDate, Date signoutDate)throws AddException{
		UserDAO dao = new UserDAOOracle();
		dao.insertUser(id, pwd, name, tel, email, addr, role, membership, createdDate, signoutDate);
	}
	//로그인하기
	public UserVo findLoginUser (UserVo uVo) throws FindException{
		UserDAO dao = new UserDAOOracle();
		return dao.selectLoginUser(uVo);

	}
	//아이디 찾기
	public String findIdUser(String email) throws FindException{
		UserDAO dao = new UserDAOOracle();
		return dao.selectFindIdUser(email);
	}

	//비밀번호 찾기
	public Map<String, String> findPwdUser (String id, String email) throws FindException{
		UserDAO dao = new UserDAOOracle();
		return dao.selectFindPwdUser(id, email);
	}

	//회원 탈퇴일자 업데이트
	public void modifySignoutUser(String id) throws AddException{
		UserDAO dao = new UserDAOOracle();
		dao.updateSignoutUser(id);
	}
	//내정보 변경하기
	public void modifyEditUser(UserVo uVo) throws AddException{
		UserDAO dao = new UserDAOOracle();
		dao.updateEditUser(uVo);		
	}
	//내가쓴 상품리뷰 목록 조회하기
	public List<Map<String,Object>> findReviewByUser(String id) throws FindException{
		UserDAO dao = new UserDAOOracle();
		return dao.selectReviewByUser(id);
	}

	//내가쓴 상품리뷰 목록 제목으로 조회하기 (확인필요)
	public List<Map<String,Object>> findReviewFindTitleUser(String title, String id) throws FindException{
		UserDAO dao = new UserDAOOracle();
		return dao.selectReviewFindTitleUser(title, id);
	}

}
