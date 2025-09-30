package com.test.post.Entity;


import jakarta.persistence.Embeddable;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Embeddable
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
//봉사시간
public class AssistanceTime {
    private LocalTime AssistanceStartTime;
    private LocalTime AssistanceEndTime;
}
