package com.myomi.order.control;

import com.myomi.order.dto.OrderDto;
import com.myomi.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
//@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // 임시 정보
//    User user = User.builder()
//            .id("id1").build();

    @GetMapping("/order")
    public List<OrderDto> orderList (Authentication user) {
        return orderService.findOrderListByUserId(user);
    }

    @PostMapping("/order")
    public void orderAdd(Authentication user, @RequestBody OrderDto requestDto) {
        orderService.addOrder(user, requestDto);
    }
}
