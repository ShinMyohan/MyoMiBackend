package com.myomi.chat.controller;


import com.myomi.chat.service.ChatService;
import com.myomi.common.status.ResponseDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/chat")
@Log4j2
public class RoomController {
    private final ChatService chatService;

    //회원의 id로 채팅방 조회
    @GetMapping("/room")
    public ResponseEntity<?> getRoom(Authentication user) {
        // 임시 로그인 아이디
        log.info("채팅방을 조회합니다. [userId : {}]", user.getName());
        ResponseDetails responseDetails = chatService.findByUserId(user);
        return new ResponseEntity<>(responseDetails, HttpStatus.valueOf(responseDetails.getHttpStatus()));
    }

    //관리자만 채팅방 목록 조회
    @GetMapping(value = "/rooms")
    public ResponseEntity<?> rooms() {
        ResponseDetails responseDetails = chatService.findAllRoom();
        return new ResponseEntity<>(responseDetails, HttpStatus.valueOf(responseDetails.getHttpStatus()));
    }

    @DeleteMapping(value = "/room")
    public ResponseEntity<?> deleteRoom() {
        ResponseDetails responseDetails = chatService.deleteRoom();
        return new ResponseEntity<>(responseDetails, HttpStatus.valueOf(responseDetails.getHttpStatus()));
    }
}