package com.ureca.ocean.jjh.chat.entity;

import com.ureca.ocean.jjh.common.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class ChatRoom extends BaseEntity {
    @Id
    private UUID id;

}
