package com.ureca.ocean.jjh.community.entity;

import com.ureca.ocean.jjh.common.entity.BaseEntity;
import com.ureca.ocean.jjh.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    private String title;
    private String content;

    @ManyToOne
    @JoinColumn(name= "author_id")
    private User author;

    private String category;
    private UUID brandId;
    private UUID benefitId;

    private LocalDateTime promiseDate;

    private String location;

}
