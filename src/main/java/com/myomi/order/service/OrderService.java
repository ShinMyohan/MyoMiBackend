package com.myomi.order.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.myomi.order.dto.OrderDetailRequestDto;
import com.myomi.order.dto.OrderRequestDto;
import com.myomi.order.dto.OrderResponseDto;
import com.myomi.order.entity.Order;
import com.myomi.order.repository.OrderRepository;
import com.myomi.product.entity.Product;
import com.myomi.product.repository.ProductRepository;
import com.myomi.user.entity.User;
import com.myomi.user.repository.UserRepository;
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

    // 주문서 작성 TODO: 상품도 같이 등록되는 문제
    @Transactional
    public void addOrder(Authentication user, OrderRequestDto orderRequestDto) {
        User u = userRepository.findById(user.getName())
                .orElseThrow(() -> new IllegalArgumentException("로그인한 사용자만 이용 가능합니다."));

        // 주문 기본 등록
        Order order = orderRequestDto.toEntity(u); // TODO: 결제 금액 계산하는 로직 짜기, 포인트가 본인이 가진것보다 많은건지 확인
        // 주문 가능한 상품인지 확인
        for (OrderDetailRequestDto orderDetail : orderRequestDto.getOrderDetails()) {
            // 주문 상세 등록
            Optional<Product> prod = productRepository.findByProdNum(orderDetail.getProduct().getProdNum());
            if(prod.isPresent() && prod.get().getStatus() == 0) {
                // 연관관계 등록
                orderDetail.toEntity(orderDetail).registerOrderAndProduct(order, prod.get());
            } else {
                log.info("구매할 수 없는 상품입니다.");
                throw new IllegalArgumentException("구매할 수 없는 상품입니다.");
            }
        }
        // 배송 정보 등록
        orderRequestDto.getDelivery().registerOrder(order);
        orderRequestDto.getDelivery().toEntity(order);

        orderRepository.save(order);
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

//    @Transactional
//    public OrderDto findTotalPriceByUserId(Authentication user) {
//        orderRepository.findByUserIdAndOrderNum()
//    }

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

    // 결제일 업데이트
    public void updatePayCreatedDate(Authentication user, OrderResponseDto orderResponseDto) {
        Order order = orderRepository.findByUserIdAndOrderNum(user.getName(), orderResponseDto.getOrderNum());
        order.updatePayCreatedDate(orderResponseDto.getOrderNum());
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
