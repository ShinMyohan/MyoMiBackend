package com.myomi.order.repository;

import com.myomi.order.dto.OrderDto;

import java.util.List;

public interface OrderCustomRepository {
    List<OrderDto> findAllByUserId(String userId);
}
