package com.myomi.chat.dto;

import com.myomi.chat.entity.ChatRoom;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class ChatRoomDTO {
    private Long num;
    private String adminId;
    private String userId;

    @Builder
    public ChatRoomDTO(Long num, String adminId, String userId) {
        this.num = num;
        this.adminId = adminId;
        this.userId = userId;
    }

    public ChatRoomDTO toDto(ChatRoom chatRoom) {
        return ChatRoomDTO.builder()
                .num(chatRoom.getNum())
                .userId(chatRoom.getUserId())
                .adminId(chatRoom.getAdminId())
                .build();
    }
}