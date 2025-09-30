package com.test.model.dto.externalPublicDto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import lombok.Data;

import java.util.List;

@Data
public class Items {
    @JacksonXmlElementWrapper(useWrapping = false) // XML 태그명 설정
    private List<JobItem> item; // xml 형식 list로 감싸짐
}

