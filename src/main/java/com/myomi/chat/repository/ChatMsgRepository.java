package com.myomi.chat.repository;

import com.myomi.chat.entity.ChatMsg;
import com.myomi.chat.entity.ChatMsgEmbedded;
import org.springframework.data.repository.CrudRepository;

public interface ChatMsgRepository extends CrudRepository<ChatMsg, ChatMsgEmbedded> { // 채팅 메시지가 생길때마다 룸이 생기는 것은 아니므로 따로 팜
}
