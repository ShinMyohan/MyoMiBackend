package com.myomi.order.dto;

import com.myomi.order.entity.Order;
import com.myomi.order.entity.OrderDetail;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderDetailRequestDto {
    private Long prodNum;
    private int prodCnt;

    @Builder
    public OrderDetailRequestDto(Order order, Long prodNum, int prodCnt) {
        this.prodNum = prodNum;
        this.prodCnt = prodCnt;
    }

    public OrderDetail toEntity(OrderDetailRequestDto orderDetailRequestDto) {
            return OrderDetail.builder()
                    .prodCnt(orderDetailRequestDto.getProdCnt())
                    .build();
    }

    public static OrderDetailRequestDto toDto(OrderDetail orderDetail) {
        return OrderDetailRequestDto.builder()
                .prodNum(orderDetail.getProduct().getProdNum())
                .prodCnt(orderDetail.getProdCnt())
                .build();
    }
}
