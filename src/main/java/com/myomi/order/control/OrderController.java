package com.myomi.order.control;

import com.myomi.exception.FindException;
import com.myomi.order.dto.OrderDetailResponseDto;
import com.myomi.order.dto.OrderListResponseDto;
import com.myomi.order.dto.OrderRequestDto;
import com.myomi.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;

    // 마이페이지에서 주문 목록 받기
    @GetMapping("/list")
    public Map<Long, List<OrderListResponseDto>> orderMyList(Authentication user) {
        return orderService.getOrderListByUserId(user);
    }

    @PostMapping("")
    public Long orderAdd(Authentication user, @RequestBody OrderRequestDto requestDto) {
        return orderService.addOrder(user, requestDto);
    }

    // 마이페이지에서 주문 상세 확인
    @GetMapping("/{num}")
    public OrderDetailResponseDto orderDetails(Authentication user, @PathVariable Long num) throws FindException {
        return orderService.getOrderByUserId(user, num);
    }
}
