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

    /* TODO: 1. 클라이언트의 요청에 대한 확인작업만 한다.(요청을 한 사람이 회원인지, 관리자인지 확인하는 등)
             2. 그 외 DB에서 자료를 꺼내오는 등 로직은 service에서 작성하고 컨트롤러는 service의 메서드를 호출해서 응답함
             3. 컨트롤러의 응답을 프론트에서 어떻게 보여줄 것인지를 정한다.
     */

    //채팅방 조회
    //이미 있는 채팅방인지 회원의 id로 조회하고
    @GetMapping("/room")  // /chat/room 으로 또는 chat/room/{id} 사용
    public ResponseEntity<ChatRoomDTO> getRoom(Authentication user) {  // String roomId
        // 임시 로그인 아이디
        log.info("# get Chat Room, userId : " + user.getName());
        return chatService.findByUserId(user);
    }

    //채팅방 개설
    //만들어져있는 채팅방이 없다면 새로 개설 -> 컨트롤러로 따로 만들필요 없이 서비스에서 회원아이디에 해당하는 채팅방이 없다면 개설하게끔 하면 될거같음
//    @PostMapping(value = "/room") // /chat/room 으로
//    public void create(){
//        // 임시 로그인 아이디
//        String userId = "id3";
//        log.info("# Create Chat Room , name: " + userId);
//        chatService.createChatRoom(userId);
//    }


    //관리자만 채팅방 목록 조회
    @GetMapping(value = "/rooms") // /chat/rooms 으로 채팅방의 목록을 조회가능
   public List<ChatRoomDTO> rooms(){
        return chatService.findAllRoom();
    }

    @DeleteMapping(value="/room")
    public void deleteRoom() {
        chatService.deleteRoom();
    }
}