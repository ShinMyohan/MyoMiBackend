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

    //stompConfig에서 설정한 applicationDestinationPrefixes와 @MessageMapping 경로가 병합됨
    //"/pub/chat/enter"
    // 처음 채팅방 입장 시 환영 메시지 전송 // TODO: 메시지 들어올 때 마다 안보내도록 방이 처음 만들어지면 그냥 메시지가 저장되고, 방에들어오면 그걸 불러오도록
    @MessageMapping(value = "/chat/enter") // MessageMapping : Client가 SEND할 수 있는 경로, num이랑 senderId를 프론트에서 받아옴
    public void enter(ChatMsgDTO chatMsgDTO) { // 유저의 정보를 가져와서 builder에 넣어줘도 될 듯
        String msg = "안녕하세요. 무엇을 도와드릴까요?";
        template.convertAndSend("/sub/chat/enter/" + chatMsgDTO.getNum(), msg); // DTO로 받아온 값을 이용해서 메시지 전송
    }

    // 메시지 전송
    @MessageMapping(value = "/chat/message")  // pub/chat/message
    public void message(ChatMsgDTO chatMsgDTO) {
        chatService.createMessage(chatMsgDTO);
        template.convertAndSend("/sub/chat/room/" + chatMsgDTO.getNum(), chatMsgDTO);
    }
}