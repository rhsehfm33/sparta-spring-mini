package com.sparta.vikingband.entity;

import com.sparta.vikingband.dto.CommentRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Comment extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;


    public Comment(CommentRequestDto requestDto) {
        this.content = requestDto.getContent();
    }
}