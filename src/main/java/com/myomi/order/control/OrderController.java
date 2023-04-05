package com.myomi.order.control;

import com.myomi.common.status.ResponseDetails;
import com.myomi.order.dto.OrderRequestDto;
import com.myomi.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;

    // 마이페이지에서 주문 목록 받기
    @GetMapping("/list")
    public ResponseEntity<?> orderMyList(Authentication user) {
        ResponseDetails responseDetails = orderService.getOrderListByUserId(user);
        return new ResponseEntity<>(responseDetails, HttpStatus.valueOf(responseDetails.getHttpStatus()));
    }

    @PostMapping("")
    public ResponseEntity<?> orderAdd(Authentication user, @RequestBody OrderRequestDto requestDto) {
        ResponseDetails responseDetails = orderService.addOrder(user, requestDto);
        return new ResponseEntity<>(responseDetails, HttpStatus.valueOf(responseDetails.getHttpStatus()));
    }

    // 마이페이지에서 주문 상세 확인
    @GetMapping("/{num}")
    public ResponseEntity<?> orderDetails(Authentication user, @PathVariable Long num) {
        ResponseDetails responseDetails = orderService.getOrderByUserId(user, num);
        return new ResponseEntity<>(responseDetails, HttpStatus.valueOf(responseDetails.getHttpStatus()));
    }

    @DeleteMapping("")
    public ResponseEntity<?> orderDelete() {
        ResponseDetails responseDetails = orderService.deleteOrder();
        return new ResponseEntity<>(responseDetails, HttpStatus.valueOf(responseDetails.getHttpStatus()));
    }
}
