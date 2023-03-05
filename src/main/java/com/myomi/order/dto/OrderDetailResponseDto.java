package com.myomi.order.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.myomi.order.entity.Order;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class OrderDetailResponseDto { // 주문 기본, 상세, 배송정보 한번에 다 받아옴
    private Long orderNum; // 주문 번호
    private String msg;
    private int usedPoint;
    private Long totalPrice;
    private int savePoint;
    @JsonFormat(timezone = "Asia/Seoul", pattern = "yy-MM-dd HH:mm")
    private LocalDateTime createdDate;
    @JsonFormat(timezone = "Asia/Seoul", pattern = "yy-MM-dd HH:mm")
    private LocalDateTime payCreatedDate;
    @JsonFormat(timezone = "Asia/Seoul", pattern = "yy-MM-dd HH:mm")
    private LocalDateTime canceledDate;
    // 주문정보
    List<Object> orderDetails; // 상품 정보 담김
    // 배송정보
    private String userName;
    private String tel;
    private String addr;
    private String deliveryMsg;
    private String receiveDate;

    @Builder
    public OrderDetailResponseDto(Long orderNum, String msg, int usedPoint, Long totalPrice,
                                  int savePoint, LocalDateTime createdDate, LocalDateTime payCreatedDate, LocalDateTime canceledDate,
                                  List<Object> orderDetails, String userName, String tel, String addr, String deliveryMsg, String receiveDate) {
        this.orderNum = orderNum;
        this.msg = msg;
        this.usedPoint = usedPoint;
        this.totalPrice = totalPrice;
        this.savePoint = savePoint;
        this.createdDate = createdDate;
        this.payCreatedDate = payCreatedDate;
        this.canceledDate = canceledDate;
        this.orderDetails = orderDetails;
        this.userName = userName;
        this.tel = tel;
        this.addr = addr;
        this.deliveryMsg = deliveryMsg;
        this.receiveDate = receiveDate;
    }

    public OrderDetailResponseDto toDto(Order order, List<Object> orderDetails) {
        return OrderDetailResponseDto.builder()
                .orderNum(order.getOrderNum())
                .msg(order.getMsg())
                .usedPoint(order.getUsedPoint())
                .totalPrice(order.getTotalPrice())
                .savePoint(order.getSavePoint())
                .createdDate(order.getCreatedDate())
                .payCreatedDate(order.getPayCreatedDate())
                .canceledDate(order.getCanceledDate())
                .orderDetails(orderDetails)
                .userName(order.getDelivery().getName())
                .tel(order.getDelivery().getTel())
                .addr(order.getDelivery().getAddr())
                .deliveryMsg(order.getDelivery().getDeliveryMsg())
                .receiveDate(order.getDelivery().getReceiveDate())
                .build();
    }
}
