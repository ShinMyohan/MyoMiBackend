package com.myomi.review.repository;

import org.springframework.data.repository.CrudRepository;

import com.myomi.review.entity.Review;

public interface ReviewRepository extends CrudRepository<Review, Long>{

}
