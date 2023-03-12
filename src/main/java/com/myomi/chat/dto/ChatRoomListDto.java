package com.myomi.chat.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.myomi.chat.entity.ChatRoom;
import com.myomi.membership.entity.MembershipLevel;
import com.myomi.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Getter
@NoArgsConstructor
public class ChatRoomListDto {
    private Long num;
    private String adminId;
    private String userId;
    @JsonFormat(timezone = "Asia/Seoul", pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdDate;
    private int role;
    private MembershipLevel membershipLevel;


    @Builder
    public ChatRoomListDto(Long num, String adminId, String userId, LocalDateTime createdDate, int role, MembershipLevel membershipLevel) {
        this.num = num;
        this.adminId = adminId;
        this.userId = userId;
        this.createdDate = createdDate;
        this.role = role;
        this.membershipLevel = membershipLevel;
    }

    public ChatRoomListDto toDto(ChatRoom chatRoom, User user) {
        return ChatRoomListDto.builder()
                .num(chatRoom.getNum())
                .userId(chatRoom.getUserId())
                .adminId(chatRoom.getAdminId())
                .createdDate(chatRoom.getCreatedDate())
                .role(user.getRole())
                .membershipLevel(user.getMembership())
                .build();
    }
}