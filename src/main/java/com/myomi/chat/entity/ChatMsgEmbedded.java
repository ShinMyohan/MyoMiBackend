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
// @DynamicInsert // TODO: roomNum을 따로 넣지 않으니 해줘야 하지않나? 모르겠다.
public class ChatMsgEmbedded implements Serializable {
    // 회원 아이디
    private Long roomNum;
    // 상품번호
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    // 복합키 사용을 위해 정적 팩토리 메소드 작성
//    protected ChatMsgEmbedded() {
//        this.createdDate = LocalDateTime.now();
//    }
//
//    public static ChatMsgEmbedded addCreateDate() {
//        return new ChatMsgEmbedded();
//    }
//    public ChatMsgEmbedded(Long roomNum, LocalDateTime createdDate) {
//        this.roomNum = roomNum;
//        this.createdDate = createdDate;
//    }
}

