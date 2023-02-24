package com.myomi.order.entity;

import lombok.*;
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
    private Long oNum;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="order_num", referencedColumnName = "num")
//    @PrimaryKeyJoinColumn(name = "num", referencedColumnName = "order_num")
//    @PrimaryKeyJoinColumn(name="order_num", referencedColumnName = "num")
    private Order order;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "tel", nullable = false)
    private String tel;

    @Column(name = "addr", nullable = false)
    private String addr;
    @Column(name = "delivery_msg")
    private String deliveryMsg;

    @Column(name = "receive_date", nullable = false)
    private String receiveDate;

    @Builder
    Delivery(Order order, String name, String tel, String addr, String deliveryMsg, String receiveDate) {
//        this.oNum = oNum;
        this.order = order;
        this.name = name;
        this.tel = tel;
        this.addr = addr;
        this.deliveryMsg = deliveryMsg;
        this.receiveDate = receiveDate;
    }

    public void registerOrder(Order order) {
        this.order = order;
//        order.registerDelivery(this);
    }
}
