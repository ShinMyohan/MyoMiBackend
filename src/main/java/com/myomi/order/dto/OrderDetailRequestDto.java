package com.myomi.order.dto;

import com.myomi.order.entity.Order;
import com.myomi.order.entity.OrderDetail;
import com.myomi.product.entity.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderDetailRequestDto {
    private Product product;
//    private Long prodNum;
    private int prodCnt;

    @Builder
    public OrderDetailRequestDto(Order order, Product product, int prodCnt) {
        this.product = product;
        this.prodCnt = prodCnt;
    }

    public OrderDetail toEntity(OrderDetailRequestDto orderDetailRequestDto) {
            return OrderDetail.builder()
//                    .product(orderDetailRequestDto.getProduct())
//                    .product(orderDetailRequestDto.getProduct())
                    .prodCnt(orderDetailRequestDto.getProdCnt())
                    .build();
    }

    public static OrderDetailRequestDto toDto(OrderDetail orderDetail) {
        return OrderDetailRequestDto.builder()
                .product(orderDetail.getProduct())
                .prodCnt(orderDetail.getProdCnt())
                .build();
    }
}
