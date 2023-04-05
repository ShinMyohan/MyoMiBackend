package com.myomi.chat.repository;

import com.myomi.chat.entity.ChatMsg;
import com.myomi.chat.entity.ChatMsgEmbedded;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ChatMsgRepository extends CrudRepository<ChatMsg, ChatMsgEmbedded> {
    List<ChatMsg> findChatMsgById_RoomNumOrderByIdAsc(Long roomNum);
}
