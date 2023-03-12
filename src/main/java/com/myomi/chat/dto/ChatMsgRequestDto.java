package com.myomi.chat.dto;

import com.myomi.chat.entity.ChatMsg;
import com.myomi.chat.entity.ChatMsgEmbedded;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ChatMsgRequestDto {
    private Long num;
    private String senderId;
    private String content;

    @Builder
    public ChatMsgRequestDto(Long num, String senderId, String content) {
        this.num = num;
        this.senderId = senderId;
        this.content = content;
    }

    public ChatMsg toEntity(ChatMsgRequestDto chatMsgRequestDto) {
        ChatMsgEmbedded id = new ChatMsgEmbedded(chatMsgRequestDto.getNum(), LocalDateTime.now());
        return ChatMsg.builder()
                .id(id)
                .senderId(chatMsgRequestDto.getSenderId())
                .content(chatMsgRequestDto.getContent())
                .build();
    }
}