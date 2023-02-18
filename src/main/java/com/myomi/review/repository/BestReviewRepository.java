package com.myomi.review.repository;

import org.springframework.data.repository.CrudRepository;

import com.myomi.review.entity.BestReview;
import com.myomi.review.entity.Review;

public interface BestReviewRepository extends CrudRepository<BestReview, Review> {

}
