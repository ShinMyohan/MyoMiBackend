package com.myomi.order.dto;

import com.myomi.order.entity.Order;
import com.myomi.order.entity.OrderDetail;
import com.myomi.product.entity.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderDetailDto {
    private Product product;
    private int prodCnt;

    @Builder
    public OrderDetailDto(Order order, Product product, int prodCnt) {
        this.product = product;
        this.prodCnt = prodCnt;
    }

    public OrderDetail toEntity(OrderDetailDto orderDetailDto) {
            return OrderDetail.builder()
                    .product(orderDetailDto.getProduct())
                    .prodCnt(orderDetailDto.getProdCnt())
                    .build();
    }

    public static OrderDetailDto toDto(OrderDetail orderDetail) {
        return OrderDetailDto.builder()
                .product(orderDetail.getProduct())
                .prodCnt(orderDetail.getProdCnt())
                .build();

    }
}
