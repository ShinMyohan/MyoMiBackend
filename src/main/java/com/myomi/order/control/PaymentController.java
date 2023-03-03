package com.myomi.order.control;

import com.myomi.exception.FindException;
import com.myomi.order.dto.PaymentRequestDto;
import com.myomi.order.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;

    // 결제 완료 후 검증 & DB에 정보 저장
    @PutMapping("")
    public ResponseEntity<String> paymentComplete(@RequestBody PaymentRequestDto requestDto, Authentication user) throws IOException, FindException {
        return paymentService.payment(requestDto, user);
    }

    // 주문취소
    @PatchMapping("/cancel/{num}")
    public ResponseEntity<String> paymentCancel(@PathVariable Long num, Authentication user) throws IOException, FindException {
        return paymentService.orderCancel(num, user);

    }
}
