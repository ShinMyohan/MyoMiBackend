package com.myomi.order.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@ToString
@DynamicInsert
@DynamicUpdate
@Entity
public class Delivery {
    @Id
    @Column(name = "order_num")
    private Long orderNum;

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
}
