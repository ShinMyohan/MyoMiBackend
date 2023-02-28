package com.myomi.order.repository;

import com.myomi.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>, OrderCustomRepository {
//    public List<Order> findByUserId(String userId);

    //    @Query("SELECT o From Order o WHERE o.user.id = :userId AND o.oNum = :num")
    public Order findByUserIdAndOrderNum(String userId, Long num);
}
