package com.myomi.user.service;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;

import com.myomi.exception.AddException;
import com.myomi.exception.FindException;
import com.myomi.user.dao.UserDAO;
import com.myomi.user.dao.UserDAOOracle;
import com.myomi.user.vo.UserVo;

public class UserService {
	UserDAO dao = new UserDAOOracle();
	UserVo uVo = new UserVo();
	
	//회원 전체 조회
	public List<UserVo> findAllUser() throws FindException{
		UserDAO dao = new UserDAOOracle();
		return dao.selectAllUser();
	}
	
	//회원가입
	public void addUser (JSONObject jsonObject) throws FindException{
		uVo.setId(jsonObject.get("id").toString());
		uVo.setPwd(jsonObject.get("pwd").toString());
		uVo.setName(jsonObject.get("name").toString());
		uVo.setTel(jsonObject.get("tel").toString());
		uVo.setEmail(jsonObject.get("email").toString());
		uVo.setAddr(jsonObject.get("addr").toString());
		dao.insertUser(uVo);
	}	
	
	//로그인하기(
	public UserVo findLoginUser (UserVo vo) throws FindException{
		UserDAO dao = new UserDAOOracle();
			return dao.selectLoginUser(vo);
		

	}
	//아이디 찾기
	public String findIdUser(String email) throws FindException{
		UserDAO dao = new UserDAOOracle();
		return dao.selectFindIdUser(email);
	}

	//비밀번호 찾기
	public String findPwdUser (UserVo vo) throws FindException{
		UserDAO dao = new UserDAOOracle();
		return dao.selectFindPwdUser(vo);
	}
	
	//회원 탈퇴일자 업데이트 (보류)
	public void modifySignoutUser(String id) throws AddException{
		UserDAO dao = new UserDAOOracle();
		dao.updateSignoutUser(id);
	}
	//내정보 변경하기
	public void modifyEditUser(JSONObject jsonObject) throws FindException{
		uVo.setPwd(jsonObject.get("pwd").toString());
		uVo.setEmail(jsonObject.get("email").toString());
		uVo.setTel(jsonObject.get("tel").toString());
		uVo.setId(jsonObject.get("id").toString());
		dao.updateEditUser(uVo);
		
	}
	
	
	//내가 작성한 상품리뷰 목록 조회하기
	public List<Map<String,Object>> findReviewByUser(String id) throws FindException{
		UserDAO dao = new UserDAOOracle();
		return dao.selectReviewByUser(id);
	}

	//내가 작성한 상품리뷰 목록 제목으로 조회하기 (보류)
	public List<Map<String,Object>> findReviewTitleByUser(String title, String id) throws FindException{
		UserDAO dao = new UserDAOOracle();
		return dao.selectReviewFindTitleUser(title, id);
	}
	
	//마이페이지 상단정보, 주문목록 주문상세 조회하기
    public List<Map<String,Object>> findMypageOrderInfoByUser(String id) throws FindException{
    	UserDAO dao = new UserDAOOracle();
    	return dao.selectMypageOrderInfoByUser(id);
    }
	
	
    //테스트
//	public static void main(String[] args) throws FindException {
//		UserDAO dao = new UserDAOOracle();
//		UserVo vo = new UserVo();
//		vo.setId("apple");
//		vo.setEmail("apple@email.com");
//		dao.selectFindPwdUser(vo);
//	}


}
