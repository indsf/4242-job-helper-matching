package com.test.model.dto.externalPublicDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.test.common.validation.EnumTypeValue;
import com.test.post.Entity.AssistanceType;
import lombok.Builder;

import java.time.Instant;

@Builder
public record OutPostReq(
        Long id,
        String title,
        String content
) {
}
