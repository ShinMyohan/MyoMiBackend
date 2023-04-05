package com.myomi.chat.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.myomi.chat.entity.ChatRoom;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Getter
@NoArgsConstructor
public class ChatRoomDto {
    private Long num;
    private String adminId;
    private String userId;
    @JsonFormat(timezone = "Asia/Seoul", pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdDate;

    @Builder
    public ChatRoomDto(Long num, String adminId, String userId, LocalDateTime createdDate) {
        this.num = num;
        this.adminId = adminId;
        this.userId = userId;
        this.createdDate = createdDate;
    }

    public ChatRoomDto toDto(ChatRoom chatRoom) {
        return ChatRoomDto.builder()
                .num(chatRoom.getNum())
                .userId(chatRoom.getUserId())
                .adminId(chatRoom.getAdminId())
                .createdDate(chatRoom.getCreatedDate())
                .build();
    }
}