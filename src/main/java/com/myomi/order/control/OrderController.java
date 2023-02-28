package com.myomi.order.control;

import com.myomi.exception.FindException;
import com.myomi.order.dto.OrderRequestDto;
import com.myomi.order.dto.OrderResponseDto;
import com.myomi.order.dto.PaymentRequestDto;
import com.myomi.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/list")
    public List<OrderResponseDto> orderList(Authentication user) {
        return orderService.getOrderListByUserId(user);
    }

    @PostMapping("")
    public Long orderAdd(Authentication user, @RequestBody OrderRequestDto requestDto) {
        return orderService.addOrder(user, requestDto);
    }

    @GetMapping("/{num}")
    public OrderResponseDto orderDetails(Authentication user, @PathVariable Long num) throws FindException {
        return orderService.getOrderByUserId(user, num);
    }

    @PutMapping("/{num}")
    public void orderModifyCanceledDate(Authentication user, @PathVariable Long num) throws FindException {
        orderService.modifyOrderCanceledDate(user, num);
    }

    // 결제
    @PutMapping("/payment")
    public ResponseEntity<String> paymentComplete(@RequestBody PaymentRequestDto paymentRequestDto, Authentication user) throws IOException, FindException {
        return orderService.payment(paymentRequestDto, user);
//        String token = orderService.getToken();
//        System.out.println("토큰 : " + token);
//
//        // DB 저장된 주문 정보
//        OrderResponseDto order = orderService.findOrderByUserId(user, paymentRequestDto.getMerchant_uid()); // 주문 저장시에, 주문번호 가져오기
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
//            if (point != usedPoint) {
//                orderService.paymentCancel(token, paymentRequestDto.getImpUid(), amount, "유저 포인트 오류");
//                return new ResponseEntity<String>("유저 포인트 오류", HttpStatus.BAD_REQUEST);
//            }
//
//            // 클라이언트에서 가져온 금액과 DB 금액이 다를 때
//            if (amount != order.getTotalPrice()) {
//                orderService.paymentCancel(token, paymentRequestDto.getImpUid(), amount, "결제 금액 오류");
//                return new ResponseEntity<String>("결제 금액 오류, 결제 취소", HttpStatus.BAD_REQUEST);
//            }
//
//            // 주문한 목록이 장바구니에 있다면 삭제
//            List<CartDeleteRequestDto> cartProdList = new ArrayList<>();
//            for (OrderDetail orderDetail : order.getOrderDetails()) {
//                CartDeleteRequestDto prod = CartDeleteRequestDto.builder().product(orderDetail.getProduct()).build();
//                cartProdList.add(prod);
//            }
//            cartService.removeCart(user, cartProdList);
//
//            orderService.updatePayCreatedDate(user, order);
//            return new ResponseEntity<>("주문이 완료되었습니다", HttpStatus.OK);
//
//        } catch (Exception e) {
//            orderService.paymentCancel(token, paymentRequestDto.getImpUid(), amount, "결제 에러");
//            return new ResponseEntity<String>("결제 에러", HttpStatus.BAD_REQUEST);
//        }


    }

    // 주문취소
    @PatchMapping("/payment/cancel")
    public ResponseEntity<String> orderCancel(PaymentRequestDto paymentRequestDto) throws IOException {
        if (!"".equals(paymentRequestDto.getImpUid())) {
            String token = orderService.getToken();
            int amount = orderService.paymentInfo(paymentRequestDto.getImpUid(), token);
            orderService.paymentCancel(token, paymentRequestDto.getImpUid(), amount, "관리자 취소");
        }
//        adminService.orderCancel(orderCancelDto);
        return ResponseEntity.ok().body("주문취소완료");
    }

}
