package com.myomi.order.repository;

import com.myomi.order.dto.OrderResponseDto;

import java.util.List;

public interface OrderCustomRepository {
    List<OrderResponseDto> findAllByUserId(String userId);
}
