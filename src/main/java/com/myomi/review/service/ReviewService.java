package com.myomi.review.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.myomi.exception.FindException;
import com.myomi.review.dao.ReviewDAOOracle;
import com.myomi.review.vo.ReviewVo;

public class ReviewService {

	public void addReview (int num, int order_num, String user_id, int sort, String title, String content,Date created_date,int stars) throws FindException{
	ReviewDAOOracle dao = new ReviewDAOOracle();
	dao.insertReview(num, order_num, user_id, sort, title, content, created_date, stars);
	}
	
	public void addBestReview (int num,Date created_date) throws FindException{
		ReviewDAOOracle dao = new ReviewDAOOracle();
		dao.insertBestReview(num, created_date);
	}
	public void modifyReview(String title,String content, int num) throws FindException{ 
		ReviewDAOOracle dao = new ReviewDAOOracle();
		dao.updateReview(title, content, num);
	}
	public ReviewVo FindOneReview(int num) throws FindException {
		ReviewDAOOracle dao = new ReviewDAOOracle();
		return dao.selectOneReview(num);
	}
	public Map<String, Object> selectOneBestReview(int num) throws FindException{
		ReviewDAOOracle dao = new ReviewDAOOracle();
		return dao.selectOneBestReview(num);
	}
	public  List<Map<String, Object>> findReviewByDate(int sort,int num) throws FindException{
		ReviewDAOOracle dao = new ReviewDAOOracle();
		return dao.selectReviewByDate(sort, num);
	}
	
	public List<Map<String, Object>> findReviewByStars(int sort,int num) throws FindException{
		ReviewDAOOracle dao = new ReviewDAOOracle();
		return dao.selectReviewByStars(sort, num);
	}
	public List<Map<String, Object>> findBestReviewByMonth(int num) throws FindException{
		ReviewDAOOracle dao = new ReviewDAOOracle();
		return dao.selectBestReviewByMonth(num);
	}
	public static void main(String[] args) throws FindException{
		ReviewService service = new ReviewService();
		//service.findBestReviewByMonth(1);
		
		
	}
	}
