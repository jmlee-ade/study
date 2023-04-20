package com.jmlee.study.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass // Entity 클래스들이 해당 클래스를 상속할때 선언한 필드들도 컬럼으로 인식
@EntityListeners(AuditingEntityListener.class) // 자동으로 값 매핑
public class BaseTimeEntity {

    // Entity가 생성되어 저장될 때 시간이 자동으로 저장
    @CreatedDate
    private LocalDateTime createdDate;

    // 조회한 Entity의 값을 변경할 때 시간이 자동으로 저장
    @LastModifiedDate
    private LocalDateTime modifiedDate;
}
