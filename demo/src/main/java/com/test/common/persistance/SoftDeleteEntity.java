package com.test.common.persistance;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Transient;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@MappedSuperclass
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SoftDeleteEntity extends BaseEntity{


    @Transient   // DB 컬럼으로 만들지 않음
    private boolean delete = false;

    @Transient
    private LocalDateTime delateTime;
}
