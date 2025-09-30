package com.test.model.service;

import com.test.model.dto.fastApiDto.RankRequest;
import com.test.model.dto.fastApiDto.RankRes;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


import java.util.stream.Collectors;


@Service
public class RankingCall {

    @Qualifier("fastapiClient")
    private final WebClient fastapiClient;

    public RankingCall(@Qualifier("fastapiClient") WebClient fastapiClient) {
        this.fastapiClient = fastapiClient;
    }

    @CircuitBreaker(name = "fastapi", fallbackMethod = "fallback")
    @Retry(name = "fastapi")
    @TimeLimiter(name = "fastapi") // 지연/실패: TimeLimiter가 시간초과 → X, Retry가 재시도, CircuitBreaker가 차단
    public Mono<RankRes> callRank(RankRequest req) {  // RankRes의 하나의 항목을 실패/성공 -> RankRers의 값을
        return fastapiClient.post()
                .uri("/rank")
                .bodyValue(req)
                .retrieve()
                .bodyToMono(RankRes.class); // WebClient 논블로킹 호출 :contentReference[oaicite:11]{index=11}
    }

    // 폴백: 점수 0, 입력 순서 유지 (이 함수 에러 처리 실패시 실행)
    // 두 개 이상의 기본값이 필요하고 데이터를 처리하는 더 안전한 대체 방법이 있는 경우 를 사용
    private Mono<RankRes> fallback(RankRequest req, Throwable ex) {
        RankRes r = new RankRes();
        r.setExplain("fallback");
        r.setRankedItems(req.getCandidates().stream()
                .map(c -> {
                    var ri = new com.test.model.dto.fastApiDto.RankedItem();
                    ri.setId(c.getId());
                    ri.setScore(0.0);
                    return ri;
                })
                .collect(Collectors.toList()));
        return Mono.just(r);
    }
}
