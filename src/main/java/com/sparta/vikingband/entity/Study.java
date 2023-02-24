package com.sparta.vikingband.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity(name = "study")
@NoArgsConstructor
public class Study extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String title;

    @Column(nullable = false, length = 255)
    private String content;

    @Column(nullable = false, length = 30)
    private String subject;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private int minPersonnel;

    @Column(nullable = false)
    private int maxPersonnel;
}
