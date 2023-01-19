package com.myomi.resource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

//DAO를 구현하는 객체
public class Factory {
	/** 데이터베이스 접속 객체*/
	private static SqlSessionFactory sqlSessionFactory;
	/** XML에 명시된 접속 정보를 읽어들인다.*/ 
	// 클래스 초기화 블록: 클래스 멤버 변수의 초기화에 사용된다.
	// 클래스가 처음 로딩될 때 한번만 수행된다. 
	static {
		// 접속 정보를 명시하고 있는 XML의 경로를 읽어주는 로직
		try {
			String resource = "com/myomi/resource/config.xml"; //DB와 관련된 config.xml (DB설정내용)
			// 위에서 String으로 받은 config.xml 파일의 경로 를 받아 읽어주자
			Reader reader = Resources.getResourceAsReader(resource); //문자 단위로 읽는 stream

			// 만약 sqlSessionFactory가 존재하지 않는다면 새로 만들어주는 로직
			if (sqlSessionFactory == null) {

				// 사용할 sqlSessionFactory 객체 생성
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

	// 데이터베이스 접속 객체를 통해 Database에 접속한 세션을 찾는다.
	public static SqlSessionFactory getSqlSessionFactory() {
		//sqlSessionFactory 객체를 return
		return sqlSessionFactory;
	}
}
