package com.test.post.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.time.LocalDateTime;

@Embeddable
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@Builder
@NoArgsConstructor
public class Schedule {

    @Column(name = "schedule_start_date")
    private LocalDateTime startDate;

    @Column(name = "schedule_end_date")
    private LocalDateTime endDate;

    @Enumerated(EnumType.STRING)
    private ScheduleType scheduleType; //정기적으로 할건지 주기적으로 할건지

    @Column(name = "schedule_details")
    private String scheduleDetails; // 상세 스케줄

}
