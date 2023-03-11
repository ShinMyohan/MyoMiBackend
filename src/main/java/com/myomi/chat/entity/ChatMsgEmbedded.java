package com.myomi.chat.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class ChatMsgEmbedded implements Serializable {
    // 회원 아이디
    private Long roomNum;
    // 상품번호
    @Column(name = "created_date")
    private LocalDateTime createdDate;
}

