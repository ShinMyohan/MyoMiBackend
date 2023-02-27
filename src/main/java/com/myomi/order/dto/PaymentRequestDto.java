package com.myomi.order.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PaymentRequestDto {
    // date로 받아오는 값들
    private Long merchant_uid; // orderNum
    private String impUid;
    private String name;
    private Long usedPoint;
    private Long totalPrice;
    private String email;
    private String tel;
    private String postNum;
    private String addr;

//    @Builder
//    public PaymentRequestDto (User user, Long orderNum, String impUid) {
//        this.user = user;
//        this.createdDate = LocalDateTime.now();
//        this.msg = msg;
//        this.couponNum = couponNum;
//        this.usedPoint = usedPoint;
//        this.savePoint = savePoint;
//        this.totalPrice = totalPrice;
//    }
}
