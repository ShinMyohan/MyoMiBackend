package com.myomi.user.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.myomi.exception.AddException;
import com.myomi.exception.FindException;
import com.myomi.resource.Factory;
import com.myomi.user.vo.UserVo;

public class UserDAOOracle implements UserDAO {
	private SqlSessionFactory sqlSessionFactory;
	public UserDAOOracle() {
		sqlSessionFactory = Factory.getSqlSessionFactory();
	}

	@Override
	public List<UserVo> selectAllUser() throws FindException {
		SqlSession session = sqlSessionFactory.openSession();
		//selectList() 안에 들어가는주소(?)는 namespace="com.myomi.product.dao.ProductDAO" 와동일해야합니다!
		//namespace 주소.<select> 에서 부여한 id   namespace.id
		//mapper 변수를 사용하지 않고 있으므로 파라미터 전달할 필요 없음.
		List<UserVo> list = session.selectList("UserMapper.selectAllUser"); 

		if(list == null) {
			System.out.println("조회결과 없음");
		} else {
			for(UserVo user : list) {
				System.out.println(user.toString());
			}
		}
		session.close();
		return list;   
	}

	@Override
	public void insertUser (String id, String pwd, String name, String tel, String email, String addr, int role, int membership, Date createdDate, Date signoutDate)throws AddException {
		SqlSession session = sqlSessionFactory.openSession();
		Map<String, Object> insertUser = new HashMap<>();
		insertUser.put("id", id);
		insertUser.put("pwd", pwd);
		insertUser.put("name", name);
		insertUser.put("tel", tel);
		insertUser.put("email", email);
		insertUser.put("addr", addr);
		insertUser.put("role", role);
		insertUser.put("membership_num", membership);
		insertUser.put("created_date", createdDate);
		insertUser.put("signout_date", signoutDate);

		session.insert("UserMapper.insertUser", insertUser);
		session.commit();

		if(insertUser.isEmpty()) {
			System.out.println("조회결과 없음");
		}else {
			System.out.println(insertUser);
		}
		session.commit();
		session.close();
	}
	
	
	@Override
	public String selectFindIdUser(String email) throws FindException {
		SqlSession session = sqlSessionFactory.openSession();
		String id = session.selectOne("UserMapper.selectFindIdUser",email);
	
		if(id == "") {
			System.out.println("조회결과 없음");
		} else {
			System.out.println("아이디는 " + id + " 입니다");
		}
		
		session.close();
		return id;
	}
	

	@Override
	public Map<String, String> selectFindPwdUser(String id, String email) throws FindException {
		SqlSession session = sqlSessionFactory.openSession();
		Map<String, String> map = new HashMap<>();
		map.put("id",id);
		map.put("email", email);
		
		Map<String, String> pwd = session.selectOne("UserMapper.selectFindPwdUser",map); 
		
		System.out.println(pwd);

		session.close();
		return pwd;
		
	}
	
	
	@Override
	public void updateSignoutUser(String id) throws AddException {
		SqlSession session = sqlSessionFactory.openSession();
		
		int date = session.update("UserMapper.updateSignoutUser", id);
		
		System.out.println(date + "의 정보 업데이트됨");
		
		session.commit();
		session.close();	
		
	}
	
	@Override
	public UserVo selectLoginUser(UserVo uVo) throws FindException {
		SqlSession session = sqlSessionFactory.openSession();
		
		UserVo vo = session.selectOne("UserMapper.selectLoginUser", uVo);
		System.out.println(vo.toString());
		
		session.close();
		
		
		return vo;
	}
	
	@Override
	public void updateEditUser(UserVo uVo) throws AddException {
		SqlSession session = sqlSessionFactory.openSession();
		session.update("UserMapper.updateEditUser", uVo);

		System.out.println(uVo.toString());
		session.commit();
		session.close();
		
	}
	
	//내가쓴 리뷰 목록 확인하기
	@Override
	public List<Map<String, Object>> selectReviewByUser(String id) throws FindException {
		SqlSession session = sqlSessionFactory.openSession();
		List<Map<String, Object>> list = session.selectList("UserMapper.selectReviewByUser", id);
		
		if(list == null) {
			System.out.println("조회된 목록 없음");
		} else {
			for(Map<String, Object> review : list ) {
				System.out.println(review.toString());
			}
		}
		session.close();
		return list;
	}
	
	//내가쓴 리뷰 제목으로 검색하기
	@Override
	public List<Map<String, Object>> selectReviewFindTitleUser(String title, String id) throws FindException {
		SqlSession session = sqlSessionFactory.openSession();
		Map map = new HashMap();
		map.put("title", title);
		map.put("id", id);
		System.out.println(map);
		List<Map<String, Object>> list = session.selectList("UserMapper.selectReviewFindTitleUser",map);
		
		System.out.println(list);
		if(list == null) {
			System.out.println("조회된 목록 없음");
		}else {
			for(Map<String,Object> list2 : list) {
				System.out.println(list2.toString());
			}
		}
		session.close();
		
		return list;

	}

	

	
//	@Override
//	public void joinUser(UserVo uservo) throws AddException {
//		// TODO Auto-generated method stub
//		SqlSession session = sqlSessionFactory.openSession();
//		session.insert("com.myomi.user.dao.UserDAO.insertUser", uservo);
//		session.commit();
//
//	}
	
	public static void main(String[] args) throws FindException, AddException{
		UserDAOOracle dao = new UserDAOOracle();
		//회원전체 조회
//  	dao.selectAllUser();
		
		//회원가입
//  	dao.insertUser("user11","user11","user11", "1313-1313-1212", "user11@email.com", "부산광역시 해운대구", 0, 0, new Date(), null);
//		UserVo uservo = new UserVo();
//		uservo.setId("user10");
//		uservo.setPwd("100");
//		uservo.setName("유성");
//		uservo.setTel("101-101-1010");
		
//		dao.joinUser(uservo);
		
		//아이디 찾기
		//dao.selectFindIdUser("user10@email.com");
		
		//비밀번호 찾기
		//dao.selectFindPwdUser("user1", "user1@email.com");
		
		//회원 탈퇴
		//dao.updateSignoutUser("user10");
		
		//회로그인 (로그인시 내정보 보기)
//		UserVo vo = new UserVo();
//		vo.setId("user1");
//		vo.setPwd("user1");
//		dao.selectLoginUser(vo);
		
		//내정보 변경
//		UserVo uVo = new UserVo();
//		uVo.setPwd("9999");
//		uVo.setEmail("user99@email.com");
//		uVo.setTel("098-098-098");
//		uVo.setId("user9");
//		dao.updateEditUser(uVo);
		
		//내가쓴 리뷰 목록 조회하기
//		dao.selectReviewByUser("user2");
		
		//내가쓴 리뷰 제목으로 검색
		dao.selectReviewFindTitleUser("%1%","user1");
		
		
	
	}


















}


