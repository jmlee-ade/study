package com.jmlee.study.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

// JpaRepository<Entity 클래스, PK 타입>를 상속하면 기본적은 CRUD 자동 생성
// Entity 클래스와 기본 Repository는 함께 위치해야 함
public interface PostsRepository extends JpaRepository<Posts, Long> {

}

