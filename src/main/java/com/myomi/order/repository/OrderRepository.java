package com.myomi.order.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.myomi.order.entity.Order;

public interface OrderRepository extends CrudRepository<Order, Long> {
    public List<Order> findAllByUserId(String userId);
    
}
