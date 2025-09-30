package com.test.utils.fastapi.controller;

import com.test.utils.fastapi.service.DataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/jobs")
public class FastApiTest {

    private final DataService dataService;

    @GetMapping("/recommend")
    public ResponseEntity<?> recommendJobs(
            @RequestParam(value = "region", required = true) String region,
            @RequestParam(value = "disability", required = true) String disability,
            @RequestParam(value = "pay", required = true) Integer pay) {

        try {
            // null 체크
            if (region.isEmpty() || disability.isEmpty() || pay == null) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("error", "필수 파라미터가 비어있습니다."));
            }

            List<Map<String, Object>> recommendations = dataService.fetchRecommendations(region, disability, pay);

            if (recommendations == null) {
                recommendations = List.of(); // 빈 리스트로 대체
            }

            return ResponseEntity.ok(Map.of("recommendations", recommendations));

        } catch (IllegalArgumentException e) {
            // 예: 잘못된 파라미터 값
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "잘못된 요청 파라z미터입니다.", "details", e.getMessage()));
        } catch (Exception e) {
            // 기타 예외
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "서버 에러가 발생했습니다.", "details", e.getMessage()));
        }
    }
}
