package com.test.model.dto.fastApiDto;

import com.test.model.dto.externalPublicDto.Items;
import lombok.Data;

import java.util.List;

@Data
public class Candidate {
    private String id;
    private String title;
    private List<String> district;
    private List<String> tags;

}
