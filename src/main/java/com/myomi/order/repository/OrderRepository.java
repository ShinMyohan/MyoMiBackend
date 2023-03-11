package com.myomi.order.repository;

import com.myomi.order.entity.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long>, OrderCustomRepository {
    Optional<Order> findByUserIdAndOrderNum(String userId, Long num);

    List<Order> findAllByPayCreatedDateIsNullAndCreatedDateBefore(LocalDateTime today);
}
