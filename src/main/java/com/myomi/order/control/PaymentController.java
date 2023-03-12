package com.myomi.order.control;

import com.myomi.common.status.ResponseDetails;
import com.myomi.order.dto.PaymentRequestDto;
import com.myomi.order.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    // 결제 완료 후 검증 & DB에 정보 저장
    @PutMapping("")
    public ResponseEntity<?> paymentComplete(@RequestBody PaymentRequestDto requestDto, Authentication user) throws IOException {
        ResponseDetails responseDetails = paymentService.payment(requestDto, user);
        return new ResponseEntity<>(responseDetails, HttpStatus.valueOf(responseDetails.getHttpStatus()));
    }

    // 주문취소
    @PatchMapping("/cancel/{num}")
    public ResponseEntity<?> paymentCancel(@PathVariable Long num, Authentication user) throws IOException {
        ResponseDetails responseDetails = paymentService.orderCancel(num, user);
        return new ResponseEntity<>(responseDetails, HttpStatus.valueOf(responseDetails.getHttpStatus()));
    }
}
