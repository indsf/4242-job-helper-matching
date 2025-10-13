package com.test.post.dto;

import java.time.LocalDateTime;

public record PostSummaryDto(
        Long id,
        String title,
        String authorName,
        LocalDateTime createdAt,
        String assistanceType,   // enum 이름 문자열 (없으면 null)
        long commentCount        // 댓글 수
) {}