package com.myomi.order.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.myomi.order.entity.OrderDetail;
import com.myomi.order.entity.OrderDetailEmbedded;

public interface OrderDetailRepository extends CrudRepository<OrderDetail, Long> {
	Optional<OrderDetail> findById(OrderDetailEmbedded orderDetailEmbedded);
}
