package com.myomi.common;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.myomi.product.ProductVo;

public class MyBatisTestApp01 {

	public static void main(String[] args) throws IOException {
		Reader reader=Resources.getResourceAsReader("com/myomi/product/product-config.xml");
		SqlSessionFactory factory=new SqlSessionFactoryBuilder().build(reader);		
		SqlSession session=factory.openSession();
        
		List<ProductVo> list=session.selectList("ProdMapper.getProdList");
		
		//0. getUserList :: 모든 user 정보
		System.out.println(":: 0. all Product(SELECT)  ? ");
		
		for (int i =0 ;  i < list.size() ; i++) {
			System.out.println( "<"+ ( i +1 )+"> 번째 회원.."+ list.get(i).toString() );
		}
	}

}
