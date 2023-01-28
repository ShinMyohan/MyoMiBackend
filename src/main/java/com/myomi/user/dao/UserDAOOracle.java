package com.myomi.user.dao;

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

	//회원전체 조회
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

	//회원가입
//	@Override
//	public void insertUser (String id, String pwd, String name, String tel, String email, 
//			String addr)throws AddException {
//		SqlSession session = sqlSessionFactory.openSession();
//		Map<String, Object> insertUser = new HashMap<>();
//		insertUser.put("id",id);
//		insertUser.put("pwd",pwd);
//		insertUser.put("name",name);
//		insertUser.put("tel",tel);
//		insertUser.put("email",email);
//		insertUser.put("addr",addr);
//		session.insert("UserMapper.insertUser", insertUser);
//		session.commit();
//
//		if(insertUser.isEmpty()) {
//			System.out.println("조회결과 없음");
//		}else {
//			System.out.println(insertUser);
//		}
//		session.close();
//	}
//	
	//회원가입
	public void insertUser (UserVo uVo)throws FindException{
		SqlSession session = sqlSessionFactory.openSession();
		session.insert("UserMapper.insertUser",uVo);
		session.commit();
		session.close();

	}
		
	//아이디 찾기
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
	
	
	//비밀번호 찾기
	@Override
	public String selectFindPwdUser(UserVo uVo) throws FindException {
		SqlSession session = sqlSessionFactory.openSession();
		
		String pwd = session.selectOne("UserMapper.selectFindPwdUser",uVo);

		if(pwd == "") {
			System.out.println("조회결과 없음");
		} else {
			System.out.println("비밀번호는 " + pwd + " 입니다");
		}
		
		session.close();
		return pwd;
		
	}
	
	//회원탈퇴
	@Override
	public void updateSignoutUser(String id) throws AddException {
		SqlSession session = sqlSessionFactory.openSession();
		
		int date = session.update("UserMapper.updateSignoutUser", id);
		
		System.out.println(date + "의 정보 업데이트됨");
		
		session.commit();
		session.close();	
		
	}
	
	
	//회원 로그인
	@Override
	public UserVo selectLoginUser(UserVo vo) throws FindException {
		SqlSession session = sqlSessionFactory.openSession();
		
		UserVo uVo = session.selectOne("UserMapper.selectLoginUser", vo);
		if(uVo == null) {
			System.out.println("조회결과 없음");
		} else {
			System.out.println("조회됨");
		}
		session.close();
		
		return uVo;
	}
	
	//회원 정보수정
	@Override
	public void updateEditUser(UserVo uVo) throws FindException {
		SqlSession session = sqlSessionFactory.openSession();
		session.insert("UserMapper.updateEditUser",uVo);
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
		Map<String, String> map = new HashMap<>();
		map.put("title", title);
		map.put("id", id);
		List<Map<String, Object>> list = session.selectList("UserMapper.selectReviewFindTitleUser", map);
		
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
	
	//마이페이지 상단정보, 주문목록, 주문상세
	@Override
	public List<Map<String, Object>> selectMypageOrderInfoByUser(String id) throws FindException {
		SqlSession session = sqlSessionFactory.openSession();
		List<Map<String, Object>> list = session.selectList("UserMapper.selectMypageOrderInfoByUser",id);
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

	
	
	//테스트
	public static void main(String[] args) throws FindException, AddException{
		UserDAOOracle dao = new UserDAOOracle();
		//회원전체 조회
//  	dao.selectAllUser();
		
		//회원가입
//		UserVo vo = new UserVo();
//		vo.setId("heeyoung");
//		vo.setPwd("hee");
//		vo.setName("희영");
//		vo.setTel("010-1000-2000");
//		vo.setEmail("hee@email.com");
//		vo.setAddr("서울 강남구 논현동");
//		dao.insertUser("toto","111","미지","011-2222-3333","mizi@email.com","광주광역시 봉선동");
		
		//아이디 찾기
		//dao.selectFindIdUser("user10@email.com");
		
		//비밀번호 찾기
//		UserVo vo = new UserVo();
//		vo.setId("apple");
//		vo.setEmail("apple@email.com");
//		dao.selectFindPwdUser("apple", "apple@email.com");
		
		//회원 탈퇴
//		dao.updateSignoutUser("user10");
		
		//회로그인 (로그인시 내정보 보기)
//		UserVo vo = new UserVo();
//		vo.setId("user12");
//		vo.setPwd("user13");
//		dao.selectLoginUser("user12","user13");
		
		//내정보 변경
//		UserVo uVo = new UserVo();
//		uVo.setPwd("9999");
//		uVo.setEmail("user99@email.com");
//		uVo.setTel("098-098-098");
//		uVo.setId("user9");
//		dao.updateEditUser(uVo);
		
		//내가쓴 리뷰 목록 조회하기
//		dao.selectReviewByUser("user1");
		
		//내가쓴 리뷰 제목으로 검색
//		dao.selectReviewFindTitleUser("맛있다1.", "user1");
		
		//마이페이지 상단정보, 주문목록, 주문상세
//		dao.selectMypageOrderInfoByUser("user1");
	
	}
		

}


