package com.myomi.coupon.repository;

import com.myomi.coupon.entity.Coupon;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CouponRepository extends CrudRepository<Coupon, Long> {
    public Optional<Coupon> findByCouponNumAndUserId(Long num, String userId);

}
