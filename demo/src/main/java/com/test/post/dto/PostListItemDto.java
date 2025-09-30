package com.test.post.dto;

import com.test.Member.entity.DisabilityType;
import com.test.post.Entity.Collage;
import com.test.post.Entity.PostType;
import lombok.Builder;

@Builder
public record PostListItemDto(
        Long id,
        String title,
        Collage collage,
        PostType postType,
        DisabilityType disabilityType,
        AssistanceResDto assistance,
        ScheduleListResDto schedule,
        Boolean isLiked
) {

}