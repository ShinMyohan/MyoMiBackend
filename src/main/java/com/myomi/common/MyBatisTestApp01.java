package com.myomi.common;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.myomi.board.vo.BoardVo;




public class MyBatisTestApp01 {

	public static void main(String[] args) throws IOException {
		Reader reader=Resources.getResourceAsReader("resources/mybatis-config.xml");
		SqlSessionFactory factory=new SqlSessionFactoryBuilder().build(reader);		
		SqlSession session=factory.openSession();
        
		List<BoardVo> list=session.selectList("boardmap.selectBoardList");
		
		//0. getUserList :: 모든 user 정보
		System.out.println(":: 0. all board(SELECT)  ? ");
		
		for (int i =0 ;  i < list.size() ; i++) {
			System.out.println( "<"+ ( i +1 )+"> 번째 회원.."+ list.get(i).toString() );
		}
	}

}
