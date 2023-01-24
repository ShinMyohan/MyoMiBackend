package com.myomi.board.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.myomi.board.vo.BoardVo;
import com.myomi.exception.FindException;
import com.myomi.resource.Factory;
import com.myomi.user.vo.UserVo;

public class BoardDAOOracle implements BoardDAO {
	private SqlSessionFactory sqlSessionFactory;
	public BoardDAOOracle() {
		sqlSessionFactory = Factory.getSqlSessionFactory();
	}

	//------------리스트 전체 출력 selectAll ------------
	@Override
	public List<Map<String, Object>> selectAll() throws FindException {
		SqlSession session = sqlSessionFactory.openSession();
		//selectList() 안에 들어가는주소(?)는 namespace="com.myomi.product.dao.ProductDAO" 와동일해야합니다!
		//namespace 주소.<select> 에서 부여한 id   namespace.id
		List<Map<String, Object>> list = session.selectList("boardMapper.selectAll",null);

		if(list == null) {
			System.out.println("조회결과 없음");
		} else {
			for(Map<String, Object> board : list) {
				System.out.println(board.toString());
			}
		}
		session.close();
		return list;   
	}



	//--------------------제목으로 검색  ----------------------
	@Override
	public List<Map<String, Object>> selectByTitle(String title) throws FindException {
		SqlSession session = sqlSessionFactory.openSession();
		List<Map<String, Object>> list = session.selectList("boardMapper.selectByTitle", title);      

		if(list == null) {
			System.out.println("조회결과 없음");
		} else {
			for(Map<String, Object> board : list) {
				System.out.println(board.toString());
			}
		}
		session.close();
		return list;   
	}

	//-------------------카테고리로 검색  ----------------------

	@Override
	public List<Map<String, Object>> selectByCategory(String category, String title) throws FindException {
		SqlSession session = sqlSessionFactory.openSession();
		Map map = new HashMap();
		map.put("category",category);
		map.put("title",title);
		System.out.println(map);
		List<Map<String, Object>> list = session.selectList("boardMapper.selectByCategory",map);      
		System.out.println(list);
		if(list == null) {
			System.out.println("조회결과 없음");
		} else {
			for(Map<String, Object> board : list) {
				System.out.println(board.toString());
			}
		}
		session.close();
		return list;   
	}
	

	//---------------글 내용 + 댓글 조회 --------------
	@Override
	public List<Map<String, Object>> selectDetail(int num) throws FindException {
		SqlSession session = sqlSessionFactory.openSession();
		
		List<Map<String, Object>> list = session.selectList("boardMapper.selectDetail", num);      
		
		if(list == null) {
			System.out.println("조회결과 없음");
		} else {
			for(Map<String, Object> board : list) {
				System.out.println(board.toString());
			}
		}
		
		session.close();
		return list;   
	}


	//------------------글 작성-------------------
	@Override
	public void insertBoard(BoardVo bVo) throws FindException {
		SqlSession session = sqlSessionFactory.openSession();
		
		
		session.insert("boardMapper.insertBoard", bVo);
	
		System.out.println(bVo.toString());
		session.commit();
	    session.close();

	}

	//---------------글 수정----------------
	@Override	
	public void updateBoard(BoardVo bVo) throws FindException {
		SqlSession session = sqlSessionFactory.openSession();
	
		session.update("boardMapper.updateBoard",bVo);
		session.commit();
       
		System.out.println(bVo.toString());
		
	}

	//--------------글 삭제-----------------
	public void deleteBoard (int num)throws FindException {
		SqlSession session = sqlSessionFactory.openSession();
		session.delete("boardMapper.deleteBoard",num);
		session.commit();
	}
	
	public static void main(String[] args) throws FindException{
		BoardDAOOracle dao = new BoardDAOOracle();
		
		//---글 삭제---
		//dao.delBoard(10);	
		
		//---글 수정---
//      BoardVo bVo = new BoardVo ();
//		bVo.setNum(4);
//		bVo.setCategory("오늘의 식단");
//		bVo.setTitle("수정테스트 최종");
//		bVo.setContent("수정수정");
//	    dao.updateBoard(bVo);
		
		//---글 번호로 검색---
       dao.selectDetail(22);
		
	    //---카테고리+제목 키워드 검색 ---
		//dao.selectByCategory("잡담","%최종%");
		
		//---전체검색---
	//dao.selectAll();
		
        //---제목으로 검색 ---
		//dao.selectByTitle("%최종%");

		//---글쓰기---
//		BoardVo bVo = new BoardVo();
//		UserVo uVo = new UserVo();
//		bVo.setNum(0);
//	    bVo.setUser(uVo);
//	    uVo.setId("user3");
//		bVo.setCategory("잡담");
//		bVo.setTitle("제이슨");
//     	bVo.setContent("제이슨");
//        bVo.setCreatedDate(new Date());
//        bVo.setHits(0);
//        dao.insertBoard(bVo);

	}

}

