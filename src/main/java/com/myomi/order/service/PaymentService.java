package com.myomi.order.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.myomi.cart.repository.CartRepository;
import com.myomi.common.status.ErrorCode;
import com.myomi.common.status.NoResourceException;
import com.myomi.common.status.ResponseDetails;
import com.myomi.coupon.service.CouponService;
import com.myomi.order.dto.PaymentRequestDto;
import com.myomi.order.entity.Order;
import com.myomi.order.entity.OrderDetail;
import com.myomi.order.repository.OrderRepository;
import com.myomi.point.service.PointDetailService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.TimeZone;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final CouponService couponService;
    private final PointDetailService pointService;

    @Value("${imp_key}")
    private String impKey;

    @Value("${imp_secret}")
    private String impSecret;

    @Data
    private class Response {
        private PaymentInfo response;
    }

    @Data
    private class PaymentInfo {
        private int amount;
    }

    // 결제 완료
    @Transactional
    public ResponseDetails payment(PaymentRequestDto paymentRequestDto, Authentication user) throws IOException {
        String path = "/api/payment";
        String token = getToken();
        String impUid = paymentRequestDto.getImpUid();
        Long orderNum = Long.parseLong(paymentRequestDto.getMerchant_uid().replace("orderNum_", ""));
        log.info("결제 완료 후 검증 작업을 시작합니다. [userId : {}, token : {}, impUid : {}]", user.getName(), token, impUid);

        // 결제 완료된 금액
        int amount = paymentInfo(impUid, token); // 아임포트에서 결제완료 된 금액 가져옴
        log.info("아임포트에서 결제된 금액 : " + amount);

        try {
            // DB 저장된 주문 정보
            Order order = orderRepository.findByUserIdAndOrderNum(user.getName(), orderNum)
                    .orElseThrow(() -> new NoResourceException(ErrorCode.RESOURCE_NOT_FOUND, "해당 주문번호가 없습니다.")); // 주문 저장시에, 주문번호 가져오기

            // 클라이언트에서 가져온 금액과 DB 금액이 다를 때
            if (amount != order.getTotalPrice()) {
                paymentCancel(token, paymentRequestDto.getImpUid(), amount, "결제 금액 오류");
                log.info("아임포트에서 결제된 금액과 실제 상품의 계산된 총액이 다릅니다. 결제 금액 오류, 결제를 취소 후 400 에러를 응답합니다." +
                        "[imPortAmount : {}, totalPrice : {}]", amount, order.getTotalPrice());
                throw new IllegalArgumentException("결제 금액이 동일하지 않습니다.");

            }
            // 주문한 목록이 장바구니에 있다면 삭제
            for (OrderDetail orderDetail : order.getOrderDetails()) {
                log.info("주문이 완료된 상품들을 회원의 장바구니에서 삭제합니다. [userId : {}, prodNum :{}]", user.getName(), orderDetail.getProduct().getProdNum());
                cartRepository.deleteCartByUserIdAndProduct(user.getName(), orderDetail.getProduct().getProdNum());
            }
            // impUid, 결제일 업데이트
            log.info("DB에 해당 주문에 impUid와 결제일을 업데이트 합니다. [orderNum : {}, impUid : {}]", orderNum, impUid);
            modifyPayCreatedDate(order, impUid, paymentRequestDto.getPayCreatedDate());


            // 쿠폰 상태 변경
            Long couponNum = order.getCouponNum();
            if (couponNum != 0) {
                log.info("주문 시 사용한 쿠폰의 상태를 사용완료(1) 로 변경합니다. [couponNum : {}]", couponNum);
                couponService.modifyCoupon(couponNum, 1); // 사용 완료
            }
            // 포인트 insert, update
            int usedPoint = order.getUsedPoint();
            int savePoint = order.getSavePoint();
            if (usedPoint != 0) {
                log.info("사용한 포인트를 포인트 상세 테이블에 입력합니다. [userId : {}, usedPoint : {}]", user.getName(), usedPoint);
                pointService.savePoint(-usedPoint, 2, user);
            }
            log.info("적립된 포인트를 포인트 상세 테이블에 입력합니다. [userId : {}, savePoint : {}]", user.getName(), savePoint);
            pointService.savePoint(savePoint, 1, user);

            log.info("결제 검증을 완료했습니다. 200번 에러코드를 응답합니다. [orderNum : {}]", orderNum);
            return new ResponseDetails(orderNum, 200, path);
        } catch (IllegalArgumentException e) {
            log.info("결제 검증에 실패했습니다. 400번 에러코드를 응답합니다. [orderNum : {}]", orderNum);
            paymentCancel(token, paymentRequestDto.getImpUid(), amount, "결제 에러");
            return new ResponseDetails(orderNum, 400, path);
        }
    }

    // 결제일 업데이트
    public void modifyPayCreatedDate(Order order, String impUid, Long payCreatedDate) {
        LocalDateTime date = LocalDateTime.ofInstant(Instant.ofEpochSecond(payCreatedDate),
                TimeZone.getDefault().toZoneId());
        log.info("결제시간: " + date);
        order.updatePayCreatedDate(order.getOrderNum(), impUid, date);
    }

    // 주문 취소
    public ResponseDetails orderCancel(Long num, Authentication user) throws IOException {
        String path = "/api/payments/cancel/" + num;
        log.info("주문을 취소합니다. [orderNum : {}]", num);
        Order order = orderRepository.findByUserIdAndOrderNum(user.getName(), num)
                .orElseThrow(() -> new NoResourceException(ErrorCode.RESOURCE_NOT_FOUND, "해당하는 주문 번호가 없습니다."));
        String impUid = order.getImpUid();
        // 수령일 계산
        String receiveDate = order.getDelivery().getReceiveDate().substring(0, 10); // yyyy-mm-dd만 뽑기
        log.info("예상 수령일을 확인합니다. [receiveDate : {}]", receiveDate);

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate receive = LocalDate.parse(receiveDate, formatter);
        LocalDateTime receiveDay = receive.atStartOfDay();
        int betweenDays = (int) Duration.between(now, receiveDay).toDays();
        log.info("주문 취소일로부터 수령일까지의 기간 차이를 계산합니다. [betweenDays : {}]", betweenDays);

        // 수령일 3일 전에만 가능
        if (betweenDays >= 2) {
            if (!"".equals(impUid)) { // impUid가 있다면
                String token = getToken();
                int amount = paymentInfo(impUid, token);
                log.info("DB의 impUid값과 token을 통해 아임포트에서 해당 결제건 금액을 가져옵니다. [price : {}]", amount);
                paymentCancel(token, impUid, amount, "주문취소");
                log.info("아임포트에서 결제 취소를 완료했습니다. DB에 취소일을 업데이트합니다.");
                // DB에 취소일 업데이트
                order.updateCanceledDate(impUid);

                // 쿠폰 상태 변경
                Long couponNum = order.getCouponNum();
                if (couponNum != 0) {
                    log.info("결제 시 사용한 쿠폰을 '사용안함'(0) 상태로 변경합니다. 만료되었을 경우 '기간만료'(2)로 처리됩니다. [couponNum : {}]", couponNum);
                    couponService.modifyCoupon(couponNum, 0); // 사용 가능하도록, 만료기간 지나면 기간만료 status로 변경
                }
                // 포인트 insert, update
                int usedPoint = order.getUsedPoint();
                int savePoint = order.getSavePoint();
                if (usedPoint != 0) {
                    log.info("사용한 포인트를 다시 복구합니다. [userId : {}, usedPoint : {}]", user.getName(), usedPoint);
                    pointService.savePoint(usedPoint, 2, user);
                }
                log.info("적립된 포인트를 다시 차감합니다. [userId : {}, savePoint : {}]", user.getName(), savePoint);
                pointService.savePoint(-savePoint, 1, user);
            }
        } else {
            log.info("배송일 3일전 까지만 취소 가능합니다. 400번 에러코드를 응답합니다. [orderNum : {}, betweenDays : {}]", num, betweenDays);
            throw new IllegalArgumentException("배송일 3일전 까지만 취소 가능합니다.");
        }
        log.info("주문 취소를 완료했습니다. [userId : {}, orderNum : {}, requestDate : {}]", user.getName(), num, now);
        return new ResponseDetails("주문취소 완료", 200, path);
    }

    // 토큰 얻기
    public String getToken() throws IOException {
        HttpsURLConnection conn = null;

        URL url = new URL("https://api.iamport.kr/users/getToken");
        conn = (HttpsURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.setDoOutput(true);

        JsonObject json = new JsonObject();
        json.addProperty("imp_key", impKey);
        json.addProperty("imp_secret", impSecret);

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
        bw.write(json.toString());
        bw.flush();
        bw.close();

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
        Gson gson = new Gson();
        String response = gson.fromJson(br.readLine(), Map.class).get("response").toString();
        System.out.println(response);
        String token = gson.fromJson(response, Map.class).get("access_token").toString();

        br.close();
        conn.disconnect();

        return token;
    }

    // 아임포트 결제 정보에서 결제금액 얻기
    public int paymentInfo(String imp_uid, String access_token) throws IOException {
        HttpsURLConnection conn = null;
        System.out.println("imp_uid : " + imp_uid);
        URL url = new URL("https://api.iamport.kr/payments/" + imp_uid);

        conn = (HttpsURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Authorization", access_token);
        conn.setDoOutput(true);

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
        Gson gson = new Gson();
        Response response = gson.fromJson(br.readLine(), Response.class);

        br.close();
        conn.disconnect();

        return response.getResponse().getAmount();
    }

    // 결제 취소
    public void paymentCancel(String access_token, String imp_uid, int amount, String reason) throws IOException {
        System.out.println("결제 취소");

        System.out.println(access_token);

        System.out.println(imp_uid);

        HttpsURLConnection conn = null;
        URL url = new URL("https://api.iamport.kr/payments/cancel");

        conn = (HttpsURLConnection) url.openConnection();

        conn.setRequestMethod("POST");

        conn.setRequestProperty("Content-type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("Authorization", access_token);

        conn.setDoOutput(true);

        JsonObject json = new JsonObject();

        json.addProperty("reason", reason);
        json.addProperty("imp_uid", imp_uid);
        json.addProperty("amount", amount);
        json.addProperty("checksum", amount);

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));

        bw.write(json.toString());
        bw.flush();
        bw.close();

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));

        br.close();
        conn.disconnect();
        log.info("아임포트에서 결제 취소를 완료했습니다. [impUid : {}, amount : {}]", imp_uid, amount);
    }
}
