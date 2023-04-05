package com.myomi.chat.controller;

import com.myomi.chat.dto.ChatMsgRequestDto;
import com.myomi.chat.service.ChatService;
import com.myomi.common.status.ResponseDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MsgController {

    //EnableWebSocketMessageBroker를 통해 등록되는 bean(stomp)
    private final SimpMessagingTemplate template; //특정 Broker로 메세지를 전달
    private final ChatService chatService;

    // 메시지 전송
    @MessageMapping(value = "/chat/message")  // pub/chat/message
    public void message(ChatMsgRequestDto chatMsgRequestDto) {
        chatService.createMessage(chatMsgRequestDto);
        template.convertAndSend("/sub/chat/room/" + chatMsgRequestDto.getNum(), chatMsgRequestDto);
    }

    // 채팅룸 번호로 메시지 조회
    @GetMapping(value = "/chat/room/{num}/message")
    public ResponseEntity<?> message(@PathVariable Long num) {
        ResponseDetails responseDetails = chatService.findAllMsg(num);
        return new ResponseEntity<>(responseDetails, HttpStatus.valueOf(responseDetails.getHttpStatus()));
    }
}