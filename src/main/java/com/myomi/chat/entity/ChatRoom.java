package com.myomi.chat.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor

@DynamicInsert
@Entity
@Table(name = "chat_room")
@SequenceGenerator(name = "CHAT_SEQ_GENERATOR", sequenceName = "CHAT_SEQ"
        , initialValue = 1, allocationSize = 1)
public class ChatRoom {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE, generator = "CHAT_SEQ_GENERATOR"
    )
    @Column(name = "num")
    private Long num;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "admin_id")
    @ColumnDefault("'admin'")
    private String adminId;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    // 부모, 양방향
    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL)
    private List<ChatMsg> msg;


    @Builder // DTO로 받아온 정보를 엔티티객체로 만들어줌
    public ChatRoom(Long num, String userId, String adminId) {
        this.num = num;
        this.userId = userId;
        this.adminId = adminId;
        this.createdDate = LocalDateTime.now();
    }

    public ChatRoom(Long num) {
        this.num = num;
    }

    // 연관관계 편의 메서드
    public void registerChatMsg(ChatMsg chatMsg) {
//        this.msg.add(chatMsg);
        chatMsg.registerChatRoom(this); // chatMsg에 chatRoom의 객체를 저장해줌
        msg.add(chatMsg); // chatRoom에도 chatMsg의 정보를 저장
    }
}
