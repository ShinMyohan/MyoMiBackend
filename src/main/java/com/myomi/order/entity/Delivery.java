package com.myomi.order.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@ToString
@Entity
public class Delivery{
    @Id
    @Column(name="order_num")
    private Long oNum;

    @MapsId
    @OneToOne(cascade= CascadeType.ALL)
    @JoinColumn(name="order_num", insertable = false)
    private Order order;

    private String name;
    private String tel;
    private String addr;
    @Column(name="delivery_msg")
    private String deliveryMsg;
    @Column(name="receive_date")
    private String receiveDate;
}
