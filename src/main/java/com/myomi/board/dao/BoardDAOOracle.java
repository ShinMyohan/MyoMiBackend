package com.myomi.board.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.myomi.board.vo.BoardVo;
import com.myomi.exception.FindException;
import com.myomi.resource.Factory;

public class BoardDAOOracle implements BoardDAO {
	private SqlSessionFactory sqlSessionFactory;
	public BoardDAOOracle() {
		sqlSessionFactory = Factory.getSqlSessionFactory();
	}

	//------------리스트 전체 출력 selectAll ------------
	@Override
	public List<BoardVo> selectAll() throws FindException {
		SqlSession session = sqlSessionFactory.openSession();
		//selectList() 안에 들어가는주소(?)는 namespace="com.myomi.product.dao.ProductDAO" 와동일해야합니다!
		//namespace 주소.<select> 에서 부여한 id   namespace.id
		List<BoardVo> list = session.selectList("boardMapper.selectAll",null);

		if(list == null) {
			System.out.println("조회결과 없음");
		} else {
			for(BoardVo board : list) {
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

		List<Map<String, Object>> list = session.selectList("boardMapper.selectByCategory",map);      

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
	public void insertBoard(int num, String userId, String category, String title, String content, Date createdDate,int hits) throws FindException {
		SqlSession session = sqlSessionFactory.openSession();
		Map<String, Object> insertBoard = new HashMap<>();
		insertBoard.put("num", num);
		insertBoard.put("user_id", userId);
		insertBoard.put("category", category);
		insertBoard.put("title", title);
		insertBoard.put("content", content);
		insertBoard.put("created_date", createdDate);
		insertBoard.put("hits", 0);

		session.insert("boardMapper.insertBoard", insertBoard);
		session.commit();

		if(insertBoard.isEmpty()) {
			System.out.println("조회결과 없음");
		} else {
			System.out.println(insertBoard);
		}

	}

	//---------------글 수정----------------
	@Override	
	public void updateBoard(String category, String title ,String content, int num) throws FindException {
		SqlSession session = sqlSessionFactory.openSession();
		Map<String, Object> edit = new HashMap<>();
		edit.put("category", category);
		edit.put("title", title);
		edit.put("content", content);
		edit.put("num", num);

		session.insert("boardMapper.updateBoard",edit);
		session.commit();

		if(edit.isEmpty()) {
			System.out.println("조회결과 없음");
		} else {
			System.out.println(edit);
		}

	}

	//--------------글 삭제-----------------
	public void delBoard (int num)throws FindException {
		SqlSession session = sqlSessionFactory.openSession();
		session.delete("boardMapper.deleteBoard",num);
		session.commit();
	}

	//		public void insertBoard(BoardVo vo) throws FindException {
	//			SqlSession session = sqlSessionFactory.openSession();
	//			session.insert("boardMapper.insertBoard", vo);
	//			session.commit();
	//			
	//			if(vo == null) {
	//				System.out.println("조회결과 없음");
	//			} else {
	//					System.out.println(vo);
	//			}
	//		}
	//		
	//		ProductVo vo = session.selectOne("com.relo.mybatis.orders.OrdersDao.selectOrdersDetail", oNum);

	//	session.insert("com.relo.mybatis.orders.OrdersDao.insertOrders", map);
	//
	//	session.update("com.relo.mybatis.product.ProductDao.update8", aNum);
	//
	//	session.delete("com.relo.mybatis.catch.CatchDao.deleteCatch", aNum);

	//	
	public static void main(String[] args) throws FindException{
		BoardDAOOracle dao = new BoardDAOOracle();
		//dao.delBoard(10);	
		//dao.updateBoard("베스트 조합","수정테스트","수정테스트",9);
		dao.selectDetail(1);
		//dao.insertBoard(0, "user1", "잡담", "글작성 테스트","어이없어@@@@@2",new Date(),0);
		//dao.selectByCategory("베스트 조합","%보리%");
		//dao.selectAll();

	}







}
//	

