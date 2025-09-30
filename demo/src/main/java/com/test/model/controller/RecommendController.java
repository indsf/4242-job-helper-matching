package com.test.model.controller;

import com.test.model.dto.fastApiDto.Candidate;
import com.test.model.dto.fastApiDto.RankRequest;
import com.test.model.dto.fastApiDto.RankRes;
import com.test.model.dto.fastApiDto.UserDto;
import com.test.model.service.PublicDataServing;
import com.test.model.service.RankingCall;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/model")
@Tag(name = "공공데이터 API", description = "공공데이터 일자리 관련 API") // swagger 묶음
public class RecommendController {


    private final PublicDataServing publicDataServing;
    private final RankingCall rankingCall;
    @Operation(
            summary = "잡 추천 (동기 응답)",
            description = "공공데이터에서 후보를 수집해 FastAPI로 점수화한 결과를 반환합니다."
    )
    @ApiResponse(
            responseCode = "200",
            description = "성공",
            content = @Content(schema = @Schema(implementation = RankRes.class))
    )
    @GetMapping("/jobs")
    public RankRes recommend(
            @Parameter(description = "사용자 지역", example = "대구") @RequestParam String region,
            @Parameter(description = "장애 유형", example = "지체") @RequestParam(required = false) String disability,
            @Parameter(description = "공공데이터 pageNo", example = "1") @RequestParam(defaultValue = "1") String page,
            @Parameter(description = "공공데이터 numOfRows", example = "100") @RequestParam(defaultValue = "5") String rows
    ) {
        // 1) 공공데이터 → 후보 정규화
        List<Candidate> candidates = publicDataServing.fetchCandidates(page, rows);

        // 2) 사용자 컨텍스트 구성
        UserDto user = new UserDto();
        user.setRegion(region);
        user.setDisability(disability);

        RankRequest req = new RankRequest();
        req.setUserDto(user);
        req.setCandidates(candidates);
        req.setContext(Map.of(
                "now", Instant.now().toString(),
                "request_id", java.util.UUID.randomUUID().toString()
        ));

        // 3) FastAPI 랭킹 동기 호출 (내부에서 WebClient 사용)
        return rankingCall.callRank(req).block();  // 과제/MVP는 block()으로 간단히
    }

    // 리액티브 버전이 필요하면 이 엔드포인트를 사용하세요(Swagger에도 함께 노출됨)
    @Operation(summary = "잡 추천 (리액티브 응답)")
    @GetMapping("/jobs/reactive")
    public Mono<RankRes> recommendReactive(
            @RequestParam String region,
            @RequestParam(required = false) String disability,
            @RequestParam(defaultValue = "1") String page,
            @RequestParam(defaultValue = "100") String rows
    ) {
        List<Candidate> candidates = publicDataServing.fetchCandidates(page, rows);

        UserDto user = new UserDto();
        user.setRegion(region);
        user.setDisability(disability);

        RankRequest req = new RankRequest();
        req.setUserDto(user);
        req.setCandidates(candidates);
        req.setContext(Map.of("now", Instant.now().toString()));

        return rankingCall.callRank(req); // Mono 그대로 반환
    }

}
