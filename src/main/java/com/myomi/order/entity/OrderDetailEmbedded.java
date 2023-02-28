package com.myomi.order.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class OrderDetailEmbedded implements Serializable {
    // 주문 번호
    private Long orderNum;
    // 상품 번호
    private Long prodNum;
}
