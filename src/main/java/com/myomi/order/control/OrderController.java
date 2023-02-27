package com.myomi.order.control;

import com.myomi.cart.service.CartService;
import com.myomi.order.dto.OrderRequestDto;
import com.myomi.order.dto.OrderResponseDto;
import com.myomi.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;
    private final CartService cartService;

    @GetMapping("/list")
    public List<OrderResponseDto> orderList(Authentication user) {
        return orderService.findOrderListByUserId(user);
    }

    @PostMapping("")
    public void orderAdd(Authentication user, @RequestBody OrderRequestDto requestDto) {
        orderService.addOrder(user, requestDto);
    }

    @GetMapping("/{num}")
    public OrderResponseDto orderDetails(Authentication user, @PathVariable Long num) {
        return orderService.findOrderByUserId(user, num);
    }

    @PutMapping("/{num}")
    public void orderModifyCanceledDate(Authentication user, @PathVariable Long num) {
        orderService.modifyOrderCanceledDate(user, num);
    }

    // 결제
//    @PutMapping("/payment")
//    public ResponseEntity<String> paymentComplete(PaymentRequestDto paymentRequestDto, Authentication user) throws IOException {
//
//        String token = orderService.getToken();
//        System.out.println("토큰 : " + token);
//
//        // DB 저장된 주문 정보
//        OrderResponseDto order = orderService.findOrderByUserId(user, paymentRequestDto.getOrderNum());
//        // 결제 완료된 금액
//        int amount = orderService.paymentInfo(paymentRequestDto.getImpUid(), token);
//
//        try {
//            // 주문 시 사용한 포인트
//            Long usedPoint = paymentRequestDto.getUsedPoint();
//
//
//            Long point = order.getUsedPoint();  // -> 디비에서 꺼내와ㅓ서 확인
//
//            // 사용된 포인트가 유저의 포인트보다 많을 때
//            if (point < usedPoint) {
//                orderService.paymentCancel(token, paymentRequestDto.getImpUid(), amount, "유저 포인트 오류");
//                return new ResponseEntity<String>("유저 포인트 오류", HttpStatus.BAD_REQUEST);
//            }
//
//
//            // 클라이언트에서 가져온 금액과 DB 금액이 다를 때
//            if (amount != order.getTotalPrice()) {
//                orderService.paymentCancel(token, paymentRequestDto.getImpUid(), amount, "결제 금액 오류");
//                return new ResponseEntity<String>("결제 금액 오류, 결제 취소", HttpStatus.BAD_REQUEST);
//            }
//
//            List<CartDeleteRequestDto> orderProdList = new ArrayList<>();
//            for (OrderDetailRequestDto orderDetailRequestDto : paymentRequestDto.getOrderDetails()) {
//                CartDeleteRequestDto prod = CartDeleteRequestDto.builder().product(orderDetailRequestDto.getProduct()).build();
//                orderProdList.add(prod);
//            }
//
//            cartService.removeCart(user, orderProdList);
//            orderService.updatePayCreatedDate(user, paymentRequestDto);
//            return new ResponseEntity<>("주문이 완료되었습니다", HttpStatus.OK);
//
//        } catch (Exception e) {
//            orderService.paymentCancel(token, paymentRequestDto.getImpUid(), amount, "결제 에러");
//            return new ResponseEntity<String>("결제 에러", HttpStatus.BAD_REQUEST);
//        }
//
//
//    }

    //주문취소
//    @PatchMapping("/cancel")
//    public ResponseEntity<String> orderCancle(OrderCancelDto orderCancelDto) throws IOException {
//        System.out.println(orderCancelDto.toString());
//        if (!"".equals(orderCancelDto.getImpUid())) {
//            String token = orderService.getToken();
//            int amount = orderService.paymentInfo(orderCancelDto.getImpUid(), token);
//            orderService.paymentCancel(token, orderCancelDto.getImpUid(), amount, "관리자 취소");
//        }
//
//        adminService.orderCancel(orderCancelDto);
//
//        return ResponseEntity.ok().body("주문취소완료");
//    }

}
