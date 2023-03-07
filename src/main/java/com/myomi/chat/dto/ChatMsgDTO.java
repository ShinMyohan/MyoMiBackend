package com.myomi.chat.dto;

import com.myomi.chat.entity.ChatMsg;
import com.myomi.chat.entity.ChatMsgEmbedded;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ChatMsgDTO {
    private Long num;
    private String senderId;
    private String content;

    @Builder
    public ChatMsgDTO (Long num, String senderId, String content) {
        this.num = num;
        this.senderId = senderId;
        this.content = content;
    }

    public ChatMsg toEntity(ChatMsgDTO chatMsgDTO) {
        ChatMsgEmbedded id = new ChatMsgEmbedded(chatMsgDTO.getNum(), LocalDateTime.now());
        return ChatMsg.builder()
                .id(id)
                .senderId(chatMsgDTO.getSenderId())
                .content(chatMsgDTO.getContent())
                .build();
    }
}