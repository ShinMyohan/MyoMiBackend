package com.myomi.order.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class OrderListResponseDto { // 주문 기본, 상세, 배송정보 한번에 다 받아옴
    private Long orderNum; // 주문 번호
    private Long prodNum;
    private String pName; // 상품 이름
    private Long totalPrice;
    @JsonFormat(timezone = "Asia/Seoul", pattern = "yyyy-MM-dd")
    private LocalDateTime createdDate;
    @JsonFormat(timezone = "Asia/Seoul", pattern = "yy-MM-dd HH:mm")
    private LocalDateTime payCreatedDate;
    private LocalDateTime canceledDate;
    private Long reviewNum; // 리뷰 번호

//    @Builder
//    public OrderListResponseDto(Long orderNum, String pName, Long totalPrice, LocalDateTime payCreatedDate,
//                                LocalDateTime canceledDate, Long reviewNum) {
//        this.orderNum = orderNum;
//        this.pName = pName;
//        this.totalPrice = totalPrice;
//        this.payCreatedDate = payCreatedDate;
//        this.canceledDate = canceledDate;
//        this.reviewNum = reviewNum;
//    }
//
//    public OrderListResponseDto toDto(Order order) {
//        return OrderListResponseDto.builder()
//                .orderNum(order.getOrderNum())
//                .pName(order.get)
//                .totalPrice(order.getCouponNum())
//                .payCreatedDate(order.getUsedPoint())
//                .canceledDate(order.getSavePoint())
//                .reviewNum(order.getTotalPrice())
//                .build();
//    }
}
