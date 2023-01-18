package com.myomi.resource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

//DAO를 구현하는 객체
public class Factory {
	private static SqlSessionFactory sqlSessionFactory;
	// 초기화 블록:멤버 변수 초기화하는 블록
	static {
		try {
			String resource = "com/myomi/resource/config.xml"; //DB와 관련된 config.xml (DB설정내용)
			Reader reader = Resources.getResourceAsReader(resource); //문자 단위로 읽는 stream

			if (sqlSessionFactory == null) {
				//사용할 sqlSessionFactory 객체 생성
				//	Builder :  객체 생성에 필요한 설정이 가능하고, 설정한 내용으로 객체를 생성해줌
				//	build(): config.xml 의 내용을 설정에 활용하여 설정
				sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
				
			}
		} catch (FileNotFoundException fileNotFoundException) {
			fileNotFoundException.printStackTrace();
		} catch (IOException iOException) {
			iOException.printStackTrace();
		}
	}

	public static SqlSessionFactory getSqlSessionFactory() {
		return sqlSessionFactory; //sqlSessionFactory 객체를 return
	}
}
