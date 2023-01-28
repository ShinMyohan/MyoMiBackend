package com.myomi.point.dao;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import com.myomi.exception.FindException;
import com.myomi.resource.Factory;

public class PointDAOOracle implements PointDAO {
	private SqlSessionFactory sqlSessionFactory;
	public PointDAOOracle() {
		sqlSessionFactory = Factory.getSqlSessionFactory();
	}
//회원별 포인트목록조회하기
	@Override
	public List<Map<String, Object>> selectPoint(String userId) throws FindException {
		SqlSession session = sqlSessionFactory.openSession();
		List<Map<String, Object>> list = session.selectList("PointMapper.selectPoint", userId);
		if (list == null) {
			System.out.println("조회결과 없음");
		} else {
			for (Map<String, Object> review : list) {
				System.out.println(review.toString());
			}
		}
		session.close();
		return list;
	}
	public static void main(String[] args) throws FindException {
		PointDAOOracle dao = new PointDAOOracle();
	}
}
