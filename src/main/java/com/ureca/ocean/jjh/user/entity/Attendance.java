package com.ureca.ocean.jjh.user.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.time.LocalDate;


@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class Attendance {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name="user_id")
    private User user;

    private LocalDate date;


}
