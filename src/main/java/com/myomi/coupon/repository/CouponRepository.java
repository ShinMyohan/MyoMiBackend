package com.myomi.coupon.repository;

import com.myomi.coupon.entity.Coupon;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CouponRepository extends CrudRepository<Coupon, Long> {
    public Optional<Coupon> findByCouponNumAndUserId(Long num, String userId);

	@Query("SELECT cp from Coupon cp WHERE cp.user.id=:username")
	public List<Coupon> findAllByUser(@Param("username")String username, Pageable pageable);

	//public Optional<Coupon> findByCouponNumAndUserId(Long couponNum, String username);
}
