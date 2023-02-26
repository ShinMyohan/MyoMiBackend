package com.myomi.order.control;

import com.myomi.order.dto.OrderDto;
import com.myomi.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/list")
    public List<OrderDto> orderList(Authentication user) {
        return orderService.findOrderListByUserId(user);
    }

    @PostMapping("")
    public void orderAdd(Authentication user, @RequestBody OrderDto requestDto) {
        orderService.addOrder(user, requestDto);
    }

    @GetMapping("/{num}")
    public OrderDto orderDetails(Authentication user, @PathVariable Long num) {
        return orderService.findOrderByUserId(user, num);
    }

    @PutMapping("/{num}")
    public void orderModifyCanceledDate(Authentication user, @PathVariable Long num) {
        orderService.modifyOrderCanceledDate(user, num);
    }
}
