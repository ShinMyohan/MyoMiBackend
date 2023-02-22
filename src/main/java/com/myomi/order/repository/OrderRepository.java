package com.myomi.order.repository;

import com.myomi.order.entity.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
    public List<Order> findAllByUserId(String userId);
}
