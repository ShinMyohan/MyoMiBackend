package com.myomi.review.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.myomi.exception.FindException;
import com.myomi.order.vo.OrderVo;
import com.myomi.resource.Factory;
import com.myomi.review.vo.BestReviewVo;
import com.myomi.review.vo.ReviewVo;
import com.myomi.user.vo.UserVo;

public class ReviewDAOOracle implements ReviewDAO {
	private SqlSessionFactory sqlSessionFactory;
	public ReviewDAOOracle() {
		sqlSessionFactory = Factory.getSqlSessionFactory();
	}
	@Override
	public Map<String, Object> selectOneReview(int num) throws FindException {
	
		SqlSession session = sqlSessionFactory.openSession();
		Map<String, Object> review= session.selectOne("ReviewMapper.selectOneReview",num);
		if(review == null) {
			System.out.println("조회결과 없음");
		}else {
			System.out.println(review);
		}
		session.close();
		return review;
	}
	@Override
	public Map<String, Object> selectOneReviewByOrder(int num) throws FindException{
		SqlSession session = sqlSessionFactory.openSession();
		Map<String, Object> review= session.selectOne("ReviewMapper.selectOneReviewByOrder",num);
		if(review == null) {
			System.out.println("조회결과 없음");
		}else {
			System.out.println(review);
		}
		session.close();
		return review;
	}
	@Override
	public void insertReview (ReviewVo rVo) throws FindException{
		SqlSession session = sqlSessionFactory.openSession();
		session.insert("ReviewMapper.insertReview", rVo);
		session.commit();
		session.close();
		

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
	public void updateReview(ReviewVo rVo) throws FindException{
		SqlSession session = sqlSessionFactory.openSession();
		
		session.update("ReviewMapper.updateReview",rVo);
		session.commit();
		session.close();
		
		
		
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
	public void insertBestReview (BestReviewVo bVo) throws FindException{
		SqlSession session = sqlSessionFactory.openSession();
		
		session.insert("ReviewMapper.insertBestReview", bVo);
		session.commit();
		session.close();
		
		
		
	}
	@Override
	public List<Map<String, Object>> selectAll(int num) throws FindException{
		SqlSession session = sqlSessionFactory.openSession();
		List<Map<String, Object>> reviewList = session.selectList("ReviewMapper.selectAll", num);
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
	public List<Map<String, Object>> selectAllByDate(int num) throws FindException{
		SqlSession session = sqlSessionFactory.openSession();
		List<Map<String, Object>> reviewList = session.selectList("ReviewMapper.selectAllByDate", num);
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
	public List<Map<String, Object>> selectAllByStars(int num) throws FindException{
		SqlSession session = sqlSessionFactory.openSession();
		List<Map<String, Object>> reviewList = session.selectList("ReviewMapper.selectAllByStars", num);
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
	
	public static void main(String[] args) throws FindException{
		ReviewDAOOracle dao = new ReviewDAOOracle();
	
		
		
		
	}
}
