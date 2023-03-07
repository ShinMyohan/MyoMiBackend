package com.myomi.chat.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;


@Getter
@NoArgsConstructor
@ToString
//@DynamicInsert
@Entity
@Table(name = "chat_msg")
public class ChatMsg {
    @EmbeddedId
    private ChatMsgEmbedded id;

    @MapsId("roomNum")
    @ManyToOne
    @JoinColumn(name = "num")  // chatRoom의 PK
    private ChatRoom chatRoom;

    @Column(name = "sender_id", nullable = false)
    private String senderId;

    @Column(name = "content", nullable = false)
    private String content;


    /* 연관관계 편의 메소드 */
    public void registerChatRoom(ChatRoom chatRoom) {
        this.chatRoom = chatRoom;
    }

    public void registerChatRoomAndMsg(ChatRoom chatRoom) {
        chatRoom.registerChatMsg(this);

//        if(!order.getOrderDetails().contains(this)) {
//            order.getOrderDetails().add(this);
//        }
    }

    @Builder
    public ChatMsg(ChatMsgEmbedded id, ChatRoom chatRoom, String senderId, String content) {
        this.id = id;// service단에서 현재시간으로 id를 보내줌
//        this.chatRoom = chatRoom;
        this.senderId = senderId;
        this.content = content;
    }

}