package com.myomi.order.dto;

import com.myomi.order.entity.Order;
import com.myomi.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class OrderDto { // 주문 기본, 상세, 배송정보 한번에 다 받아옴
    private Order order;
    private User user;
    private String msg;
    private Long couponNum;
    private Long usedPoint;
    private Long totalPrice;
    private Long savePoint;
    private LocalDateTime createdDate;
    private LocalDateTime payCreatedDate;
    private LocalDateTime canceledDate;
    // 주문정보
//    @JsonIgnore
    private List<OrderDetailDto> orderDetails;
    // 배송정보
    private DeliveryDto delivery;

    // QueryDsl용 변수
    private Long oNum; // 주문 번호
    private String pName; // 상품 이름
    private Long rNum; // 리뷰 번호

    @Builder
    public OrderDto (User user, LocalDateTime createdDate, String msg, Long couponNum, Long usedPoint,
                     Long savePoint, Long totalPrice, List<OrderDetailDto> orderDetail,
                     DeliveryDto delivery) {
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

    public Order toEntity(User u, OrderDto orderDto) {
        return Order.builder()
                .user(u)
                .createdDate(LocalDateTime.now())
                .msg(orderDto.getOrder().getMsg())
                .couponNum(orderDto.getOrder().getCouponNum())
                .usedPoint(orderDto.getOrder().getUsedPoint())
                .savePoint(orderDto.getOrder().getSavePoint())
                .totalPrice(orderDto.getOrder().getTotalPrice())
                .delivery(orderDto.getDelivery().toEntity(orderDto.getDelivery(), orderDto.getOrder()))
                .build();
    }

    public OrderDto toDto(String userId, Order order) {
        return OrderDto.builder()
                .user(User.builder().id(userId).build())
                .createdDate(LocalDateTime.now())
                .msg(order.getMsg())
                .couponNum(order.getCouponNum())
                .usedPoint(order.getUsedPoint())
                .savePoint(order.getSavePoint())
                .totalPrice(order.getTotalPrice())
                .delivery(DeliveryDto.toDto(order.getDelivery()))
                .build();
    }
}
