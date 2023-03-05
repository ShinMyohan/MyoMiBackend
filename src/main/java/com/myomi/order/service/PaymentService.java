package com.myomi.order.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.myomi.cart.repository.CartRepository;
import com.myomi.coupon.service.CouponService;
import com.myomi.exception.FindException;
import com.myomi.order.dto.PaymentRequestDto;
import com.myomi.order.entity.Order;
import com.myomi.order.entity.OrderDetail;
import com.myomi.order.repository.OrderRepository;
import com.myomi.point.service.PointDetailService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<String> payment(PaymentRequestDto paymentRequestDto, Authentication user) throws IOException, FindException {
        String token = getToken();
        System.out.println("토큰 : " + token);
        String impUid = paymentRequestDto.getImpUid();

        Long orderNum = Long.parseLong(paymentRequestDto.getMerchant_uid().replace("orderNum_", ""));
        // DB 저장된 주문 정보
        Order order = orderRepository.findByUserIdAndOrderNum(user.getName(), orderNum)
                .orElseThrow(() -> new FindException("해당 주문번호가 없습니다.")); // 주문 저장시에, 주문번호 가져오기

        // 결제 완료된 금액
        int amount = paymentInfo(impUid, token); // 아임포트에서 결제완료 된 금액 가져옴

        try {
            // 클라이언트에서 가져온 금액과 DB 금액이 다를 때
            if (amount != order.getTotalPrice()) {
                paymentCancel(token, paymentRequestDto.getImpUid(), amount, "결제 금액 오류");
                log.info("결제 금액 오류, 결제 취소");
                log.info("amount");
                log.info("order.getTotalPrice()");
                return new ResponseEntity<String>("결제 금액 오류, 결제 취소", HttpStatus.BAD_REQUEST);

            }
            // 주문한 목록이 장바구니에 있다면 삭제
            for (OrderDetail orderDetail : order.getOrderDetails()) {
//                Cart cart = Cart.builder().product(orderDetail.getProduct()).build();
                cartRepository.deleteCartByUserIdAndProduct(user.getName(), orderDetail.getProduct().getProdNum());
            }
            // impUid, 결제일 업데이트
            modifyPayCreatedDate(order, impUid, paymentRequestDto.getPayCreatedDate());


            // 쿠폰 상태 변경
            Long couponNum = order.getCouponNum();
            if (couponNum != 0) {
                couponService.modifyCoupon(couponNum, 1); // 사용 완료
            }
            // 포인트 insert, update
            int usedPoint = order.getUsedPoint();
            int savePoint = order.getSavePoint();
            pointService.savePoint(-usedPoint, 2, user);
            pointService.savePoint(savePoint, 1, user);

            return new ResponseEntity<>("결제가 완료되었습니다", HttpStatus.OK);

        } catch (Exception e) {
            paymentCancel(token, paymentRequestDto.getImpUid(), amount, "결제 에러");
            return new ResponseEntity<String>("결제 에러", HttpStatus.BAD_REQUEST);
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
    public ResponseEntity<String> orderCancel(Long num, Authentication user) throws FindException, IOException {
        Order order = orderRepository.findByOrderNumAndUser(num, user.getName())
                .orElseThrow(() -> new FindException("해당하는 주문 번호가 없습니다."));
        String impUid = order.getImpUid();
        // 수령일 계산
        String receiveDate = order.getDelivery().getReceiveDate().substring(0, 9); // yyyy-mm-dd만 뽑기
        LocalDateTime now = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate receive = LocalDate.parse(receiveDate, formatter);
        LocalDateTime receiveDay = receive.atStartOfDay();
        int betweenDays = (int) Duration.between(now, receiveDay).toDays();

        try {
            // 수령일 3일 전에만 가능
            if (betweenDays >= 2) {
                if (!"".equals(impUid)) { // impUid가 있다면
                    String token = getToken();
                    int amount = paymentInfo(impUid, token);
                    paymentCancel(token, impUid, amount, "주문취소");
                    // DB에 취소일 업데이트
                    order.updateCanceledDate(impUid);

                    // 쿠폰 상태 변경
                    Long couponNum = order.getCouponNum();
                    if (couponNum != 0) {
                        couponService.modifyCoupon(couponNum, 0); // 사용 가능하도록, 만료기간 지나면 기간만료 status로 변경
                    }
                    // 포인트 insert, update
                    int usedPoint = order.getUsedPoint();
                    int savePoint = order.getSavePoint();
                    pointService.savePoint(usedPoint, 2, user);
                    pointService.savePoint(-savePoint, 1, user);
                }
            } else {
                log.info("배송일 3일전 까지만 취소 가능합니다");
            }
            return ResponseEntity.ok().body("주문취소 완료");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("주문취소 실패");
        }
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
    }
}
