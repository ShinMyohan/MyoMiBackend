package com.myomi.chat.service;

import com.myomi.chat.dto.ChatMsgDTO;
import com.myomi.chat.dto.ChatRoomDTO;
import com.myomi.chat.entity.ChatMsg;
import com.myomi.chat.entity.ChatRoom;
import com.myomi.chat.repository.ChatMsgRepository;
import com.myomi.chat.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ChatRoomDTO> findByUserId(Authentication user) {
        Optional<ChatRoom> roomOpt = chatRepository.findByUserId(user.getName());
        if (roomOpt.isEmpty()) { // 회원에 해당하는 채팅방이 없다면
            createChatRoom(user); // 채팅방 생성 메서드로 이동
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            ChatRoomDTO chatRoomDTO = new ChatRoomDTO();
            return new ResponseEntity<>(chatRoomDTO.toDto(roomOpt.get()),
                    HttpStatus.OK
            );
        }
    }

    // 채팅룸 생성
    @Transactional
    protected void createChatRoom(Authentication user) { // 노출 안되도록
        Optional<ChatRoom> room = chatRepository.findByUserId(user.getName());
        if (room.isPresent()) {
            new IllegalArgumentException(user.getName() + "님의 채팅방이 존재합니다.");
        } else {
            ChatRoom chatRoom = ChatRoom.builder()
                    .userId(user.getName())
                    .adminId("admin")
                    .build();
            chatRepository.save(chatRoom);
        }
    }

    // 메시지 저장
    @Transactional
    public ResponseEntity<ChatMsgDTO> createMessage(ChatMsgDTO chatMsgDTO) {
        ChatRoom room = new ChatRoom(chatMsgDTO.getNum());
        ChatMsg m = chatMsgDTO.toEntity(chatMsgDTO);
        m.registerChatRoom(room);
        chatMsgRepository.save(m);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 관리자가 모든 채팅방 목록 보기
    @Transactional
    public List<ChatRoomDTO> findAllRoom() {
        List<ChatRoom> rooms = chatRepository.findAll();
//                .orElseThrow(() -> new NullPointerException("채팅방이 존재하지 않습니다."));
        if (rooms.isEmpty()) {
            throw new NullPointerException("채팅방이 존재하지 않습니다.");
        }
        List<ChatRoomDTO> list = new ArrayList<>();
        for (ChatRoom room : rooms) {
            ChatRoomDTO chatRoomDTO = new ChatRoomDTO();
            chatRoomDTO.toDto(room);
            list.add(chatRoomDTO);
        }
        return list;
    }

    // 방을 만들고 하루 지나면 삭제
    @Scheduled(cron = "0 0 0 * * *") // 매일 정각
    @Transactional
    public void deleteRoom() {
        List<ChatRoom> rooms = chatRepository.findAll();
        for (ChatRoom room : rooms) {
            if (room.getMsg().size() == 0) { // TODO: select를 더 줄일 방법이 있을까?
                System.out.println("회원 id: " + room.getUserId() + "의 채팅룸 삭제");
                chatRepository.deleteById(room.getNum());
            }
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date now = new Date();
        String strDate = sdf.format(now);
        System.out.println("채팅룸 삭제 시간: " + strDate);
    }
}