package com.myomi.order.dto;

import com.myomi.order.entity.Delivery;
import com.myomi.order.entity.Order;
import com.myomi.order.entity.OrderDetail;
import com.myomi.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class OrderResponseDto { // 주문 기본, 상세, 배송정보 한번에 다 받아옴
    private User user;
    private String msg;
    private Long couponNum;
    private int usedPoint;
    private Long totalPrice;
    private int savePoint;
    private LocalDateTime createdDate;
    private LocalDateTime payCreatedDate;
    private LocalDateTime canceledDate;
    private String impUid;
    // 주문정보
    private List<OrderDetail> orderDetails;
    // 배송정보
    private Delivery delivery;

    // QueryDsl용 변수
    private Long orderNum; // 주문 번호
    private String pName; // 상품 이름
    private Long reviewNum; // 리뷰 번호

    @Builder
    public OrderResponseDto(User user, LocalDateTime createdDate, String msg, Long couponNum, int usedPoint,
                            int savePoint, Long totalPrice, List<OrderDetail> orderDetail,
                            Delivery delivery) {
        this.user = user;
        this.createdDate = createdDate;
        this.msg = msg;
        this.couponNum = couponNum;
        this.usedPoint = usedPoint;
        this.savePoint = savePoint; // TODO: 그냥 받아와도 될까?
        this.totalPrice = totalPrice;
        this.orderDetails = orderDetail;
        this.delivery = delivery;
    }

    public OrderResponseDto toDto(Order order) {
        return OrderResponseDto.builder()
                .createdDate(LocalDateTime.now())
                .msg(order.getMsg())
                .couponNum(order.getCouponNum())
                .usedPoint(order.getUsedPoint())
                .savePoint(order.getSavePoint())
                .totalPrice(order.getTotalPrice())
                .orderDetail(orderDetails)
                .delivery(delivery)
                .build();
    }
}
