package com.jmlee.study.domain.posts;

import com.jmlee.study.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity // 테이블과 링크될 클래스임을 명시
public class Posts extends BaseTimeEntity {

    @Id // 해당 테이블의 pk 필드임을 명시
    @GeneratedValue(strategy = GenerationType.IDENTITY) // pk 생성 규칙 명시
    private Long id;

    @Column(length = 500, nullable = false) // 테이블의 컬럼을 명시. 굳이 선언하지 않아도 해당 클래스의 필드는 모두 컬럼임
   private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    @Builder
    public Posts(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }
}
