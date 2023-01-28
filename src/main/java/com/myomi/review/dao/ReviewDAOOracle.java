package com.myomi.review.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import com.myomi.exception.FindException;
import com.myomi.resource.Factory;
import com.myomi.review.vo.BestReviewVo;
import com.myomi.review.vo.ReviewVo;


public class ReviewDAOOracle implements ReviewDAO {
	private SqlSessionFactory sqlSessionFactory;
	public ReviewDAOOracle() {
		sqlSessionFactory = Factory.getSqlSessionFactory();
	}
	//리뷰상세보기
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
	//주문번호로 리뷰상세보기
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
	//리뷰작성
	@Override
	public void insertReview (ReviewVo rVo) throws FindException{
		SqlSession session = sqlSessionFactory.openSession();
		session.insert("ReviewMapper.insertReview", rVo);
		session.commit();
		session.close();
	}
	//날짜순으로 리뷰(일반 혹은 포토)목록보기
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
	//별점순으로 리뷰(일반 혹은 포토)목록보기
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
	//리뷰수정
	@Override
	public void updateReview(ReviewVo rVo) throws FindException{
		SqlSession session = sqlSessionFactory.openSession();
		session.update("ReviewMapper.updateReview",rVo);
		session.commit();
		session.close();
	}
	//이번달 베스트리뷰 목록보기
	@Override
	public List<Map<String, Object>> selectBestReviewByMonth(int num) throws FindException{
				SqlSession session = sqlSessionFactory.openSession();
				List<Map<String, Object>> reviewList = session.selectList("ReviewMapper.selectBestReviewByMonth", num);      
				if(reviewList == null) {
					System.out.println("조회결과 없음");
				} else {
					for(Map<String, Object> review : reviewList) {
						System.out.println(review.toString());
					}
				}
				session.close();
				return reviewList;   
	}
	//베스트리뷰 상세보기
	@Override
	public Map<String, Object> selectOneBestReview(int num) throws FindException{
				SqlSession session = sqlSessionFactory.openSession();
				Map<String, Object> bestReview = session.selectOne("ReviewMapper.selectOneBestReview", num);
				if(bestReview == null) {
					System.out.println("조회결과 없음");
				} else {
						System.out.println(bestReview+"잘 됩니다!");
					}
				session.close();
				return bestReview;
	}
	//베스트리뷰선정하기
	@Override
	public void insertBestReview (BestReviewVo bVo) throws FindException{
		SqlSession session = sqlSessionFactory.openSession();
		session.insert("ReviewMapper.insertBestReview", bVo);
		session.commit();
		session.close();
	}
	//리뷰 전체보기
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
		session.close();
		return reviewList;
	}
	//전체리뷰 날짜순으로보기
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
		session.close();
		return reviewList;
	}
	//전체리뷰 별점순으로 보기
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
		session.close();
		return reviewList;
	}
	public static void main(String[] args) throws FindException{
		ReviewDAOOracle dao = new ReviewDAOOracle();
	dao.selectAll(1);
	}
}
