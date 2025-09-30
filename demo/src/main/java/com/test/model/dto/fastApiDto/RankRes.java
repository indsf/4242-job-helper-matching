package com.test.model.dto.fastApiDto;

import lombok.Data;

import java.util.List;

@Data
public class RankRes {
    private List<RankedItem> rankedItems;
    private String explain;
}

