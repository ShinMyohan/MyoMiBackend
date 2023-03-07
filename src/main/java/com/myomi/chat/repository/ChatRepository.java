package com.myomi.chat.repository;


import com.myomi.chat.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRepository extends JpaRepository<ChatRoom, Long> {
    public Optional<ChatRoom> findByUserId(String userId);
    public List<ChatRoom> findAll();
}
