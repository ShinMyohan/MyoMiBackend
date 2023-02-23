package com.myomi.order.entity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@ToString
@DynamicInsert
@DynamicUpdate
@Entity
public class Delivery {
    @Id
    @Column(name = "order_num")
    private Long oNum;

    @MapsId
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_num", insertable = false)
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
        this.order = order;
        this.name = name;
        this.tel = tel;
        this.addr = addr;
        this.deliveryMsg = deliveryMsg;
        this.receiveDate = receiveDate;
    }
}
