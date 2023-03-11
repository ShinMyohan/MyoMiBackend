package com.myomi.order.repository;

import com.myomi.order.entity.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long>, OrderCustomRepository {
    Optional<Order> findByUserIdAndOrderNum(String userId, Long num);
}
