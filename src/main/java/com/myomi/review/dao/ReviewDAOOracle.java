package com.myomi.review.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.myomi.exception.FindException;
import com.myomi.resource.Factory;
import com.myomi.review.vo.ReviewVo;

public class ReviewDAOOracle implements ReviewDAO {
	private SqlSessionFactory sqlSessionFactory;
	public ReviewDAOOracle() {
		sqlSessionFactory = Factory.getSqlSessionFactory();
	}
	@Override
	public ReviewVo selectOneReview(int num) throws FindException {
		// 1. 데이터 베이스 접속
		SqlSession session = sqlSessionFactory.openSession();
		//selectList() 안에 들어가는주소(?)는 namespace="com.myomi.product.dao.ProductDAO" 와동일해야합니다!
		//namespace 주소.<select> 에서 부여한 id   namespace.id
		
		// 2. 조회할 데이터 (null로 사용할 값은 설정하지 않는다.)
//		ProductVo input = new ProductVo();
//		input.setNum(1);
		
		// 3. 데이터 조회
		ReviewVo output = session.selectOne("ReviewMapper.selectOneReview", num);      
		
		
		// 4. 결과 판별
		if(output == null) {
			System.out.println("조회결과 없음");
		} else {
			System.out.println(output.toString());
		}
		
		// 4. DB 접속 해제
		session.close();
		return output;  
	}
	@Override
	public void insertReview (int num, int order_num, String user_id, int sort, String title, String content,Date created_date,int stars) throws FindException{
		SqlSession session = sqlSessionFactory.openSession();
		Map<String, Object> insertReview = new HashMap<>();
		insertReview.put("num", num);
		insertReview.put("order_num", order_num);
		insertReview.put("user_id", user_id);
		insertReview.put("sort", sort);
		insertReview.put("title", title);
		insertReview.put("content", content);
		insertReview.put("created_date", created_date);
		insertReview.put("stars", stars);
		
		session.insert("ReviewMapper.insertReview", insertReview);
		session.commit();
		
		if(insertReview.isEmpty()) {
			System.out.println("조회결과 없음");
		} else {
				System.out.println(insertReview);
			}
		

	}
	@Override
	public  List<Map<String, Object>> selectReviewByDate(int sort,int num) throws FindException{
		SqlSession session = sqlSessionFactory.openSession();
		Map map = new HashMap();
		map.put("sort",sort);
		map.put("num",num);
	
		
		List<Map<String, Object>> list = session.selectList("ReviewMapper.selectReviewByDate",map);      

		if(list == null) {
			System.out.println("조회결과 없음");
		} else {
			for(Map<String, Object> review : list) {
				System.out.println(review.toString());
			}
		}
		session.close();
		return list;  
	}
	@Override
	public List<Map<String, Object>> selectReviewByStars(int sort,int num) throws FindException{
		SqlSession session = sqlSessionFactory.openSession();
		Map map = new HashMap();
		map.put("sort",sort);
		map.put("num",num);
	
		
		List<Map<String, Object>> list = session.selectList("ReviewMapper.selectReviewByStars",map);      

		if(list == null) {
			System.out.println("조회결과 없음");
		} else {
			for(Map<String, Object> review : list) {
				System.out.println(review.toString());
			}
		}
		session.close();
		return list;  
	}
	@Override
	public void updateReview(String title,String content, int num) throws FindException{
		SqlSession session = sqlSessionFactory.openSession();
		Map<String, Object> updateReview = new HashMap<>();
		
		updateReview.put("title", title);
		updateReview.put("content", content);
		updateReview.put("num", num);
		
		
		session.insert("ReviewMapper.updateReview", updateReview);
		session.commit();
		
		if(updateReview.isEmpty()) {
			System.out.println("조회결과 없음");
		} else {
				System.out.println(updateReview);
			}
		
	}
	@Override
	public List<Map<String, Object>> selectBestReviewByMonth(int num) throws FindException{
		// 1. 데이터 베이스 접속
				SqlSession session = sqlSessionFactory.openSession();

				List<Map<String, Object>> reviewList = session.selectList("ReviewMapper.selectBestReviewByMonth", num);      
				
				// 3. 결과 판별
				if(reviewList == null) {
					System.out.println("조회결과 없음");
				} else {
					for(Map<String, Object> review : reviewList) {
						System.out.println(review.toString());
					}
				}
				
				// 4. DB 접속 해제
				session.close();
				return reviewList;   
	}
	@Override
	public Map<String, Object> selectOneBestReview(int num) throws FindException{
		// 1. 데이터 베이스 접속
				SqlSession session = sqlSessionFactory.openSession();
				
				Map<String, Object> bestReview = session.selectOne("ReviewMapper.selectOneBestReview", num);
				// 3. 데이터 조회
				//System.out.println(map);
				if(bestReview == null) {
					System.out.println("조회결과 없음");
				} else {
						System.out.println(bestReview+"잘 됩니다!");
					}
				session.close();
				return bestReview;
	}
	@Override
	public void insertBestReview (int num,Date created_date) throws FindException{
		SqlSession session = sqlSessionFactory.openSession();
		Map<String, Object> insertBestReview = new HashMap<>();
		insertBestReview.put("num", num);
		insertBestReview.put("created_date", created_date);
		session.insert("ReviewMapper.insertBestReview", insertBestReview);
		session.commit();
		
		if(insertBestReview.isEmpty()) {
			System.out.println("조회결과 없음");
		} else {
				System.out.println(insertBestReview+"잘 됩니다!");
			}
		
		
	}
	public static void main(String[] args) throws FindException{
		ReviewDAOOracle dao = new ReviewDAOOracle();
		//dao.selectOneReview(1);
		//dao.insertReview(0, 6, "user3", 3, "이게맞나2", "이게 맞아2", new Date(), 3);
		//ReviewVo rVo = new ReviewVo();
		//BestReviewVo bVo = new BestReviewVo();
		
		//rVo.setNum(1);
		//bVo.setReview(rVo);
		//dao.selectOneBestReview(bVo);
		//dao.insertBestReview(5,new Date());
		//dao.selectOneBestReview(5);
		dao.selectReviewByDate(3, 3);
		
	}
}
