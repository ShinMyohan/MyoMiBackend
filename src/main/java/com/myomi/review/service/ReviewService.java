package com.myomi.review.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;

import com.myomi.exception.FindException;
import com.myomi.order.vo.OrderVo;
import com.myomi.review.dao.ReviewDAO;
import com.myomi.review.dao.ReviewDAOOracle;
import com.myomi.review.vo.BestReviewVo;
import com.myomi.review.vo.ReviewVo;
import com.myomi.user.vo.UserVo;

public class ReviewService {
	ReviewDAO dao = new ReviewDAOOracle();
	OrderVo oVo = new OrderVo();
	UserVo uVo = new UserVo();
	ReviewVo rVo = new ReviewVo();
	BestReviewVo bVo = new BestReviewVo();
	//리뷰작성
	public void addReview (JSONObject jsonObject) throws FindException {
		rVo.setNum(0);
		oVo.setNum(Integer.parseInt(jsonObject.get("num").toString()));
		rVo.setOrder(oVo);
		uVo.setId(jsonObject.get("id").toString());
		rVo.setUser(uVo);
		rVo.setSort(Integer.parseInt(jsonObject.get("sort").toString()));
		rVo.setTitle(jsonObject.get("title").toString());
		rVo.setContent(jsonObject.get("content").toString());
		rVo.setCreatedDate(new Date());
		rVo.setStars(Integer.parseInt(jsonObject.get("stars").toString()));
		dao.insertReview(rVo);
	}
//베스트리뷰선정
	public void addBestReview(JSONObject jsonObject) throws FindException {
		rVo.setNum(Integer.parseInt(jsonObject.get("num").toString()));
		bVo.setReview(rVo);
		bVo.setCreatedDate(new Date());
		dao.insertBestReview(bVo);
	}
//리뷰수정
	public void modifyReview(JSONObject jsonObject) throws FindException {
		rVo.setNum(Integer.parseInt(jsonObject.get("num").toString()));
		rVo.setOrder(oVo);
		rVo.setUser(uVo);
		rVo.setSort(0);
		rVo.setTitle(jsonObject.get("title").toString());
		rVo.setContent(jsonObject.get("content").toString());
		rVo.setCreatedDate(null);
		rVo.setStars(0);
		dao.updateReview(rVo);
	}
//리뷰상세조회
	public Map<String, Object> FindOneReview(int num) throws FindException {
		return dao.selectOneReview(num);
	}
//주문번호로 리뷰상세조회
	public Map<String, Object> FindOneReviewByOrder(int num) throws FindException {
		return dao.selectOneReviewByOrder(num);
	}
//베스트리뷰상세조회
	public Map<String, Object> selectOneBestReview(int num) throws FindException {
		return dao.selectOneBestReview(num);
	}
//리뷰전체조회
	public List<Map<String, Object>> findAllReview(int num) throws FindException {
		return dao.selectAll(num);
	}
//날짜순으로 리뷰전체조회
	public List<Map<String, Object>> findAllByDate(int num) throws FindException {
		return dao.selectAllByDate(num);
	}
//별점순으로 리뷰전체조회
	public List<Map<String, Object>> findAllByStars(int num) throws FindException {
		return dao.selectAllByStars(num);
	}
//날짜순 리뷰(포토 혹은 일반)목록 조회
	public List<Map<String, Object>> findReviewByDate(int sort, int num) throws FindException {
		return dao.selectReviewByDate(sort, num);
	}
//별점순 리뷰(포토 혹은 일반)목록 조회
	public List<Map<String, Object>> findReviewByStars(int sort, int num) throws FindException {
		return dao.selectReviewByStars(sort, num);
	}
//베스트리뷰상세조회
	public List<Map<String, Object>> findBestReviewByMonth(int num) throws FindException {
		return dao.selectBestReviewByMonth(num);
	}

	public static void main(String[] args) throws FindException {
		ReviewService service = new ReviewService();
		ReviewDAOOracle dao = new ReviewDAOOracle();
		dao.selectAll(1);
		
		

	}
}
