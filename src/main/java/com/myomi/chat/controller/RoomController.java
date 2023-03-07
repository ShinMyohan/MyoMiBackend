package com.myomi.chat.controller;


import com.myomi.chat.dto.ChatRoomDTO;
import com.myomi.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/chat")
@Log4j2
public class RoomController {

    private final ChatService chatService;

    //채팅방 조회
    //이미 있는 채팅방인지 회원의 id로 조회하고
    @GetMapping("/room")  // /chat/room 으로 또는 chat/room/{id} 사용
    public ResponseEntity<ChatRoomDTO> getRoom(Authentication user) {  // String roomId
        // 임시 로그인 아이디
        log.info("# get Chat Room, userId : " + user.getName());
        return chatService.findByUserId(user);
    }

    //관리자만 채팅방 목록 조회
    @GetMapping(value = "/rooms") // /chat/rooms 으로 채팅방의 목록을 조회가능
    public List<ChatRoomDTO> rooms() {
        return chatService.findAllRoom();
    }

    @DeleteMapping(value = "/room")
    public void deleteRoom() {
        chatService.deleteRoom();
    }
}