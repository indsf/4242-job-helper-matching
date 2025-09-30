package com.test.model.controller;

import com.test.model.dto.externalPublicDto.JobItem;
import com.test.model.service.PublicDataServing;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/job_list_env")
@Tag(name = "공공데이터 API (원본)", description = "FastAPI 거치지 않고 공공데이터 원본을 그대로 반환")
public class TestController {
    private final PublicDataServing publicDataServing;

    @Operation(
            summary = "실시간 공공데이터 잡 리스트",
            description = "FastAPI 호출 없이 공공데이터 API에서 가져온 일자리 정보를 그대로 반환합니다."
    )
    @GetMapping("/row")
    public List<JobItem> getJobs(
            @Parameter(description = "공공데이터 pageNo", example = "1")
            @RequestParam("row") String page,
            @Parameter(description = "공공데이터 numOfRows", example = "100")
            @RequestParam("page") String rows
    ) {
        // fetchCandidates는 Candidate로 변환하니까,
        // JobItem 그대로 뽑는 메서드를 PublicDataServing에 추가하면 더 깔끔합니다.
        return publicDataServing.fetchJobItems(page, rows);
    }
}

