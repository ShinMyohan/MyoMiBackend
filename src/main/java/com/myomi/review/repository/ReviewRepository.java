package com.myomi.review.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.myomi.review.entity.Review;

public interface ReviewRepository extends CrudRepository<Review, Long> {
	Optional<Review> findById(Long num);
}
