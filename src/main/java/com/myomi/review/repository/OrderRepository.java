package com.myomi.review.repository;

import org.springframework.data.repository.CrudRepository;

import com.myomi.order.entity.Order;

public interface OrderRepository extends CrudRepository<Order, Long> {

}
