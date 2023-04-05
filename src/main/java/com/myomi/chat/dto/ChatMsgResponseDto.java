package com.myomi.chat.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.myomi.chat.entity.ChatMsg;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ChatMsgResponseDto {
    @JsonFormat(timezone = "Asia/Seoul", pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdDate;
    private String senderId;
    private String content;

    @Builder
    public ChatMsgResponseDto(LocalDateTime createdDate, String senderId, String content) {
        this.createdDate = createdDate;
        this.senderId = senderId;
        this.content = content;
    }

    public static ChatMsgResponseDto toDto(ChatMsg chatMsg) {
        return ChatMsgResponseDto.builder()
                .createdDate(chatMsg.getId().getCreatedDate())
                .senderId(chatMsg.getSenderId())
                .content(chatMsg.getContent())
                .build();
    }
}