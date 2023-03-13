package com.myomi.chat.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Getter
@NoArgsConstructor
@Entity
@Table(name = "chat_msg")
public class ChatMsg {
    @EmbeddedId
    private ChatMsgEmbedded id;

    @MapsId("roomNum")
    @ManyToOne
    @JoinColumn(name = "room_num")
    private ChatRoom chatRoom;

    @Column(name = "sender_id", nullable = false)
    private String senderId;

    @Column(name = "content", nullable = false)
    private String content;


    /* 연관관계 편의 메소드 */
    public void registerChatRoom(ChatRoom chatRoom) {
        this.chatRoom = chatRoom;
    }

    @Builder
    public ChatMsg(ChatMsgEmbedded id, String senderId, String content) {
        this.id = id;
        this.senderId = senderId;
        this.content = content;
    }

}