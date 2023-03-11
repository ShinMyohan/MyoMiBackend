package com.myomi.chat.controller;

import com.myomi.chat.dto.ChatMsgDTO;
import com.myomi.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MsgController {

    //EnableWebSocketMessageBroker를 통해 등록되는 bean(stomp)
    private final SimpMessagingTemplate template; //특정 Broker로 메세지를 전달
    private final ChatService chatService;

    // 메시지 전송
    @MessageMapping(value = "/chat/message")  // pub/chat/message
    public void message(ChatMsgDTO chatMsgDTO) {
        chatService.createMessage(chatMsgDTO);
        template.convertAndSend("/sub/chat/room/" + chatMsgDTO.getNum(), chatMsgDTO);
    }
}