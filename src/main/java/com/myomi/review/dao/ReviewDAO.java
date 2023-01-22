package com.myomi.review.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.myomi.exception.FindException;
import com.myomi.product.vo.ProductVo;
import com.myomi.review.vo.BestReviewVo;
import com.myomi.review.vo.ReviewVo;

public interface ReviewDAO {

	/**
	 * 리뷰 수정을 위해 리뷰를 상세보기(반환)
	 * 
	 * @return 리뷰번호에 해당하는 리뷰하나
	 * @throws Exception //DAOOracle
	 */
	public Map<String, Object> selectOneReview(int num) throws FindException;

	/**
	 * 리뷰 작성
	 * 
	 * @return 작성한 리뷰
	 * @throws Exception //DAOOracle
	 */
	public Map<String, Object> selectOneReviewByOrder(int num) throws FindException;

	public void insertReview(ReviewVo rvo) throws FindException;

	/**
	 * 최신순으로 리뷰목록 조회
	 * 
	 * @return 최신순으로 정렬된 리뷰목록
	 * @throws Exception //DAOOracle
	 */
	public List<Map<String, Object>> selectReviewByDate(int sort, int num) throws FindException;

	/**
	 * 별점순으로 리뷰목록 조회
	 * 
	 * @return 별점순으로 정렬된 리뷰목록
	 * @throws Exception //DAOOracle
	 */
	public List<Map<String, Object>> selectReviewByStars(int sort, int num) throws FindException;

	public void updateReview(ReviewVo rVo) throws FindException;

	public List<Map<String, Object>> selectBestReviewByMonth(int num) throws FindException;

	public Map<String, Object> selectOneBestReview(int num) throws FindException;

	public void insertBestReview(BestReviewVo bvo) throws FindException;

	public List<Map<String, Object>> selectAll(int num) throws FindException;

	public List<Map<String, Object>> selectAllByDate(int num) throws FindException;

	public List<Map<String, Object>> selectAllByStars(int num) throws FindException;
}
