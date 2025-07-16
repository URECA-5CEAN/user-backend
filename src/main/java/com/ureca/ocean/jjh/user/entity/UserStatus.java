package com.ureca.ocean.jjh.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "user_status")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserStatus {

    @Id
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @OneToOne
    @MapsId // PK = FK 구조
    @JoinColumn(name = "id") // 외래키 이름
    private User user;

    private int level;
    private int exp;

}
