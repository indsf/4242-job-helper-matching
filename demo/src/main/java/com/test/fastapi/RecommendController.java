package com.test.fastapi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "FastAPI 추천 테스트", description = "FastAPI 서버와 통신하여 추천 결과를 콘솔에 출력")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RecommendController {

    private final RecommendService recommendService;

    @GetMapping("/recommend")
    public Map<String, Object> getRecommendations(
            @RequestParam(name = "region") String region,
            @RequestParam(name = "jobType") String jobType,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        Map<String, Object> fastApi = recommendService.getRecommendations(region, jobType);
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> all = (List<Map<String, Object>>) fastApi.getOrDefault("recommendations", List.of());

        int total = all.size();
        int totalPages = (int) Math.ceil((double) total / size);
        int from = Math.max(0, (page - 1) * size);
        int to = Math.min(total, from + size);
        List<Map<String, Object>> content = from < to ? all.subList(from, to) : List.of();

        Map<String, Object> res = new LinkedHashMap<>();
        res.put("region", region);
        res.put("job_type", jobType);
        res.put("page", page);
        res.put("size", size);
        res.put("total_pages", totalPages);
        res.put("total_items", total);
        res.put("content", content);
        return res;
    }
}
