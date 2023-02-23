package com.myomi.order.dto;

import com.myomi.order.entity.OrderDetail;
import com.myomi.product.entity.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class OrderDto { // 주문 기본, 상세, 배송정보 한번에 다 받아옴
    private Long oNum;
    private String msg;
    private Long couponNum;
    private Long usedPoint;
    private Long totalPrice;
    private Long savePoint;
    private LocalDateTime payCreatedDate;
    private LocalDateTime canceledDate;
    // 주문정보
    private List<OrderDetail> orderDetail;
    private List<Product> products;
    private Product product;
    private int prodCnt;
    // 배송정보
    private String name;
    private String tel;
    private String addr;
    private String deliveryMsg;
    private String receiveDate;

    // QueryDsl용 변수
    private String pName; // 상품 이름
    private Long rNum; // 리뷰 번호
}
