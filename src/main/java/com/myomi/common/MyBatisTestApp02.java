package com.myomi.common;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.myomi.product.ProductVo;
import com.myomi.qna.QnaVo;

public class MyBatisTestApp02 {

	public static void main(String[] args) throws IOException {
		Reader reader=Resources.getResourceAsReader("com/myomi/qna/qna-config.xml");
		SqlSessionFactory factory=new SqlSessionFactoryBuilder().build(reader);		
		SqlSession session=factory.openSession();
        
		List<QnaVo> list=session.selectList("QnaMapper.getQnaList");
		
		//0. getUserList :: 모든 user 정보
		System.out.println(":: 0. all Qna(SELECT)  ? ");
		
		for (int i =0 ;  i < list.size() ; i++) {
			System.out.println( "<"+ ( i +1 )+"> 번째 회원.."+ list.get(i).toString() );
		}
	}

}
