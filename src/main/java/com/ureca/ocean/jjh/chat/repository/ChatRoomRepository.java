package com.ureca.ocean.jjh.chat.repository;

import com.ureca.ocean.jjh.chat.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, UUID> {
}
