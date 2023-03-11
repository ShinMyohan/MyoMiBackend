package com.myomi.chat.repository;

import com.myomi.chat.entity.ChatMsg;
import com.myomi.chat.entity.ChatMsgEmbedded;
import org.springframework.data.repository.CrudRepository;

public interface ChatMsgRepository extends CrudRepository<ChatMsg, ChatMsgEmbedded> {
}
