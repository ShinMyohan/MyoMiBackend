package com.myomi.coupon.repository;

import org.springframework.data.repository.CrudRepository;

import com.myomi.coupon.entity.Coupon;

public interface CouponRepository extends CrudRepository<Coupon, Integer> {

}