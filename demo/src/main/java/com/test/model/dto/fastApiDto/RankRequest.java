package com.test.model.dto.fastApiDto;

import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Data
public class RankRequest {
    private UserDto userDto;
    private List<Candidate> candidates;
    private Map<String, Object> context;
}
