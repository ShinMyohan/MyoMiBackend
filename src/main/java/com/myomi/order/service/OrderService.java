package com.myomi.order.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.myomi.cart.entity.Cart;
import com.myomi.cart.repository.CartRepository;
import com.myomi.coupon.entity.Coupon;
import com.myomi.coupon.repository.CouponRepository;
import com.myomi.order.dto.OrderDetailRequestDto;
import com.myomi.order.dto.OrderRequestDto;
import com.myomi.order.dto.OrderResponseDto;
import com.myomi.order.dto.PaymentRequestDto;
import com.myomi.order.entity.Order;
import com.myomi.order.entity.OrderDetail;
import com.myomi.order.repository.OrderRepository;
import com.myomi.product.entity.Product;
import com.myomi.product.repository.ProductRepository;
import com.myomi.user.entity.User;
import com.myomi.user.repository.UserRepository;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CouponRepository couponRepository;

    /* TODO: 1. 주문서 작성 (배송, 상세) (OK)
             2. 배송정보 입력 (OK)
             3. 상세정보 입력 (OK)
             4. 내 주문 목록 조회 (OK)
             5. 주문 상세 조회 (OK)
             6. 결제 API
             7. 결제시 포인트 차감/적립/쿠폰적용
             8. 결제 시, 장바구니에서 주문한 상품 삭제 -> cart에서 메서드 가져오기
             9. 배송 3일 전에 주문 취소 (OK)
    */

    // 주문서 작성
    @Transactional
    public Long addOrder(Authentication user, OrderRequestDto requestDto) {
        User u = userRepository.findById(user.getName())
                .orElseThrow(() -> new IllegalArgumentException("로그인한 사용자만 이용 가능합니다."));

        // 실제 상품 가격
        double realPrice = 0.0;

//        try {
            // 상품이 있는지 확인
            for (OrderDetailRequestDto od : requestDto.getOrderDetails()) {
                Product product = productRepository.findByProdNum(od.getProduct().getProdNum())
                        .orElseThrow(() -> new IllegalArgumentException("해당 상품이 없습니다."));
                if(product.getStatus() != 0) {
                    log.info("품절된 상품입니다.");
                } else {
                    // 상품 가격 계산
                    realPrice += product.getOriginPrice() * (1 - product.getPercentage() * 0.01) * od.getProdCnt();
                }
            }

            if (requestDto.getCouponNum() != 0) {
                // 사용 가능한 쿠폰인지 확인
                Coupon coupon = couponRepository.findByCouponNumAndUserId(requestDto.getCouponNum(), u.getName())
                        .orElseThrow(() -> new IllegalArgumentException("사용 가능한 쿠폰이 없습니다."));
                if (coupon.getStatus() != 0) {
                    throw new IllegalArgumentException("사용할 수 없는 쿠폰입니다.");
                } else {
                    // 쿠폰을 적용한 총 금액
                    realPrice *= (1 - coupon.getPercentage() * 0.01);
                }
            }
            // 사용포인트가 본인 가진 것 보다 적은지 확인
            if (u.getPoint().getTotalPoint() < requestDto.getUsedPoint()) {
                throw new IllegalArgumentException("보유포인트 잔액을 초과했습니다.");
            } else {
                // 사용한 포인트를 제외한 총 금액
                realPrice -= requestDto.getUsedPoint();
            }

            if (realPrice * 0.01 != requestDto.getSavePoint()) {
                throw new IllegalArgumentException("적립 예정 금액이 다릅니다.");
            }

            // 계산한 금액과 가져온 금액이 같은지 확인
            if ((long) realPrice != requestDto.getTotalPrice()) {
                throw new IllegalArgumentException("총 금액을 확인해주세요");
            }

            // 주문 기본 등록
            Order order = requestDto.toEntity(u); // TODO: 결제 금액 계산하는 로직 짜기, 포인트가 본인이 가진것보다 많은건지 확인
            // 주문 가능한 상품인지 확인
            for (OrderDetailRequestDto orderDetail : requestDto.getOrderDetails()) {
                // 주문 상세 등록
                Optional<Product> prod = productRepository.findByProdNum(orderDetail.getProduct().getProdNum());
                if (prod.isPresent() && prod.get().getStatus() == 0) {
                    // 연관관계 등록
                    orderDetail.toEntity(orderDetail).registerOrderAndProduct(order, prod.get());
                } else {
                    throw new IllegalArgumentException("구매할 수 없는 상품입니다.");
                }
            }
            // 배송 정보 등록
            requestDto.getDelivery().registerOrder(order);
            requestDto.getDelivery().toEntity(order);

            orderRepository.save(order);
            return order.getOrderNum();
//        } catch (IllegalArgumentException e) {
//            throw new IllegalArgumentException("주문에 실패했습니다.");
//        }
    }

    // 회원 정보로 주문 목록 확인
    @Transactional
    public List<OrderResponseDto> findOrderListByUserId(Authentication user) {
        return orderRepository.findAllByUserId(user.getName());
    }

    // 회원 정보로 주문 상세 조회
    @Transactional
    public OrderResponseDto findOrderByUserId(Authentication user, Long num) {
        Order order = orderRepository.findByUserIdAndOrderNum(user.getName(), num);
        OrderResponseDto orderResponseDto = new OrderResponseDto();
        return orderResponseDto.toDto(user.getName(), order);
    }

    // 배송일 3일 전에 주문 취소 가능(cancledDate update), 일자 계산
    @Transactional
    public void modifyOrderCanceledDate(Authentication user, Long num) {
        Order order = orderRepository.findByUserIdAndOrderNum(user.getName(), num);
        String receiveDate = order.getDelivery().getReceiveDate();
        LocalDateTime now = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate receive = LocalDate.parse(receiveDate, formatter);
        LocalDateTime receiveDay = receive.atStartOfDay();
        int betweenDays = (int) Duration.between(now, receiveDay).toDays();

        if (betweenDays >= 3) {
            order.updateCanceledDate(num);
        } else {
            log.info("배송일 3일전 까지만 취소 가능합니다");
        }
    }

    // 결제일 업데이트
    public void updatePayCreatedDate(Order order) {
        order.updatePayCreatedDate(order.getOrderNum());
    }

    //-------------------------------------------------------------------------
    public ResponseEntity<String> payment(PaymentRequestDto paymentRequestDto, Authentication user) throws IOException {
        String token = getToken();
        System.out.println("토큰 : " + token);

        System.out.println("paymentRequestDto.getMerchant_uid() : " + paymentRequestDto.getMerchant_uid());
        System.out.println("paymentRequestDto.getMerchant_uid() : " + paymentRequestDto.getTotalPrice());

        // DB 저장된 주문 정보
        Order order = orderRepository.findByUserIdAndOrderNum(user.getName(), paymentRequestDto.getMerchant_uid()); // 주문 저장시에, 주문번호 가져오기
        // 결제 완료된 금액
        int amount = paymentInfo(paymentRequestDto.getImpUid(), token);

        try {
            // 클라이언트에서 가져온 금액과 DB 금액이 다를 때
            if (amount != order.getTotalPrice()) {
                paymentCancel(token, paymentRequestDto.getImpUid(), amount, "결제 금액 오류");
                return new ResponseEntity<String>("결제 금액 오류, 결제 취소", HttpStatus.BAD_REQUEST);
            }
            // 주문한 목록이 장바구니에 있다면 삭제
            for (OrderDetail orderDetail : order.getOrderDetails()) {
                Cart cart = Cart.builder().product(orderDetail.getProduct()).build();
                cartRepository.deleteCartByUserIdAndProduct(user.getName(), cart.getProduct().getProdNum());
            }


            updatePayCreatedDate(order);
            return new ResponseEntity<>("주문이 완료되었습니다", HttpStatus.OK);

        } catch (Exception e) {
            paymentCancel(token, paymentRequestDto.getImpUid(), amount, "결제 에러");
            return new ResponseEntity<String>("결제 에러", HttpStatus.BAD_REQUEST);
        }
    }


    // 결제
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

    // 결제 정보
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
