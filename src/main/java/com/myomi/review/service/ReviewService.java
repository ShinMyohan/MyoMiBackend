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
	OrderVo order = new OrderVo();
	UserVo user = new UserVo();
	ReviewVo rVo = new ReviewVo();
	BestReviewVo bVo = new BestReviewVo();
	public void addReview (JSONObject jsonObject) throws FindException {
		
		rVo.setNum(0);
		order.setNum(Integer.parseInt(jsonObject.get("num").toString()));
		rVo.setOrder(order);
		user.setId(jsonObject.get("id").toString());
		rVo.setUser(user);
		rVo.setSort(Integer.parseInt(jsonObject.get("sort").toString()));
		rVo.setTitle(jsonObject.get("title").toString());
		rVo.setContent(jsonObject.get("content").toString());
		rVo.setCreatedDate(new Date());
		rVo.setStars(Integer.parseInt(jsonObject.get("stars").toString()));
		dao.insertReview(rVo);
	}

	public void addBestReview(JSONObject jsonObject) throws FindException {
		
		rVo.setNum(Integer.parseInt(jsonObject.get("num").toString()));
		bVo.setReview(rVo);
		bVo.setCreatedDate(new Date());
		dao.insertBestReview(bVo);
	}

	public void modifyReview(JSONObject jsonObject) throws FindException {
		rVo.setNum(Integer.parseInt(jsonObject.get("num").toString()));
		rVo.setOrder(order);
		rVo.setUser(user);
		rVo.setSort(0);
		rVo.setTitle(jsonObject.get("title").toString());
		rVo.setContent(jsonObject.get("content").toString());
		rVo.setCreatedDate(null);
		rVo.setStars(0);
		dao.updateReview(rVo);
	}

	public Map<String, Object> FindOneReview(int num) throws FindException {
		return dao.selectOneReview(num);
	}

	public Map<String, Object> FindOneReviewByOrder(int num) throws FindException {
		return dao.selectOneReviewByOrder(num);
	}

	public Map<String, Object> selectOneBestReview(int num) throws FindException {
		return dao.selectOneBestReview(num);
	}

	public List<Map<String, Object>> findAllReview(int num) throws FindException {
		return dao.selectAll(num);
	}

	public List<Map<String, Object>> findAllByDate(int num) throws FindException {
		return dao.selectAllByDate(num);
	}

	public List<Map<String, Object>> findAllByStars(int num) throws FindException {
		return dao.selectAllByStars(num);
	}

	public List<Map<String, Object>> findReviewByDate(int sort, int num) throws FindException {
		return dao.selectReviewByDate(sort, num);
	}

	public List<Map<String, Object>> findReviewByStars(int sort, int num) throws FindException {
		return dao.selectReviewByStars(sort, num);
	}

	public List<Map<String, Object>> findBestReviewByMonth(int num) throws FindException {
		return dao.selectBestReviewByMonth(num);
	}

	public static void main(String[] args) throws FindException {
		ReviewService service = new ReviewService();
		
		

	}
}
