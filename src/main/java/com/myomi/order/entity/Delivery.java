package com.myomi.order.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
@Getter
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@Entity
public class Delivery {
    @Id
    @Column(name = "order_num")
    private Long orderNum;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "order_num", referencedColumnName = "num")
    private Order order;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "tel", nullable = false)
    private String tel;

    @Column(name = "addr", nullable = false)
    private String addr;

    @Column(name = "delivery_msg")
    @ColumnDefault("' '")
    private String deliveryMsg;

    @Column(name = "receive_date", nullable = false)
    private String receiveDate;

    @Builder
    private Delivery(String name, String tel, String addr, String deliveryMsg, String receiveDate) {
        this.name = name;
        this.tel = tel;
        this.addr = addr;
        this.deliveryMsg = deliveryMsg;
        this.receiveDate = receiveDate;
    }

    public void registerOrder(Order order) {
        this.order = order;
    }

    public Delivery toEntity(Order order) {
        return Delivery.builder()
                .name(order.getDelivery().getName())
                .tel(order.getDelivery().getTel())
                .addr(order.getDelivery().getAddr())
                .deliveryMsg(order.getDelivery().getDeliveryMsg())
                .receiveDate(order.getDelivery().getReceiveDate())
                .build();
    }
}
