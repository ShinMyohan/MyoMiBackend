package com.myomi.chat.service;

import com.myomi.chat.dto.ChatMsgDTO;
import com.myomi.chat.dto.ChatRoomDTO;
import com.myomi.chat.entity.ChatMsg;
import com.myomi.chat.entity.ChatRoom;
import com.myomi.chat.repository.ChatMsgRepository;
import com.myomi.chat.repository.ChatRepository;
import com.myomi.common.status.ResponseDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatService {
    private final ChatRepository chatRepository;
    private final ChatMsgRepository chatMsgRepository;

    // 회원 id에 해당하는 채팅룸 찾기
    @Transactional
    public ResponseDetails findByUserId(Authentication user) {
        String path = "/api/chat/room";
        log.info("회원의 채팅룸이 있는지 확인합니다. [userId : {}]", user.getName());
        Optional<ChatRoom> roomOpt = chatRepository.findByUserId(user.getName());
        if (roomOpt.isEmpty()) { // 회원에 해당하는 채팅방이 없다면
            ChatRoom chatRoom = createChatRoom(user); // 채팅방 생성 메서드로 이동
            log.info("채팅룸이 존재하지 않으므로 새로운 채팅룸을 생성합니다. [userId : {}, roomNum : {}]", user.getName(), chatRoom.getNum());
            return new ResponseDetails(chatRoom, 200, path);
        } else {
            ChatRoomDTO chatRoomDTO = new ChatRoomDTO();
            log.info("채팅룸이 존재합니다. [userId : {}, roomNum : {}]", user.getName(), roomOpt.get().getNum());
            return new ResponseDetails(chatRoomDTO.toDto(roomOpt.get()), 200, path);
        }
    }

    // 채팅룸 생성
    @Transactional
    protected ChatRoom createChatRoom(Authentication user) {
        ChatRoom chatRoom = ChatRoom.builder()
                .userId(user.getName())
                .adminId("admin")
                .build();
        chatRepository.save(chatRoom);
        return chatRoom;
    }

    // 메시지 저장
    @Transactional
    public ResponseDetails createMessage(ChatMsgDTO chatMsgDTO) {
        String path = "/api/chat/message";
        log.info("메시지를 저장합니다. [roomNum : {}, msg : {}]", chatMsgDTO.getNum(), chatMsgDTO.getContent());
        ChatRoom room = new ChatRoom(chatMsgDTO.getNum());
        ChatMsg m = chatMsgDTO.toEntity(chatMsgDTO);
        m.registerChatRoom(room); // 연관관계
        chatMsgRepository.save(m);

        return new ResponseDetails(m, 200, path);
    }

    // 관리자가 모든 채팅방 목록 보기
    @Transactional
    public ResponseDetails findAllRoom() {
        String path = "/api/chat/rooms";
        log.info("채팅방 목록 조회를 시작합니다.");
        List<ChatRoom> rooms = chatRepository.findAll();
        if (rooms.isEmpty()) {
            return new ResponseDetails("채팅룸이 존재하지 않습니다.", 200, path);
        }
        List<ChatRoomDTO> list = new ArrayList<>();
        for (ChatRoom room : rooms) {
            ChatRoomDTO chatRoomDTO = new ChatRoomDTO();
            chatRoomDTO.toDto(room);
            list.add(chatRoomDTO);
        }
        return new ResponseDetails(list, 200, path);
    }

    // 방을 만들고 하루 지나면 삭제
    @Scheduled(cron = "0 0 0 * * *") // 매일 정각
    @Transactional
    public ResponseDetails deleteRoom() {
        String path = "/api/chat/room";
        log.info("메시지가 존재하지 않는 채팅룸 삭제를 시작합니다.");
        List<ChatRoom> rooms = chatRepository.findAll();
        for (ChatRoom room : rooms) {
            if (room.getMsg().size() == 0) {
                log.info("메시지가 존재하지 않는 채팅룸을 찾았습니다. 삭제합니다. [userId : {}, roomNum : {}]", room.getUserId(), room.getNum());
                chatRepository.deleteById(room.getNum());
            }
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date now = new Date();
        String strDate = sdf.format(now);
        log.info("채팅룸 삭제를 완료했습니다. [finishDate : {}]", strDate);
        return new ResponseDetails("채팅룸 삭제를 완료했습니다.", 200, path);
    }
}