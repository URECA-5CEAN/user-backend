package com.ureca.ocean.jjh.chat.repository;

import com.ureca.ocean.jjh.chat.entity.ChatRoomUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ChatRoomUserRepository  extends JpaRepository<ChatRoomUser, UUID> {
}
