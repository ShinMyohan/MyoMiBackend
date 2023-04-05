package com.myomi.order.repository;

import com.myomi.order.dto.OrderListResponseDto;
import com.myomi.order.dto.OrderSumResponseDto;

import java.util.List;
import java.util.Map;

public interface OrderCustomRepository {

    // 마이페이지 주문 목록
    Map<Long, List<OrderListResponseDto>> findAllByUserId(String userId);

    // 3개월간 주문 금액 계산
    List<OrderSumResponseDto> findOrderTotalPrice();
}

