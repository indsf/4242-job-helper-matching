package com.test.fastapi;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RecommendService {
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${fastapi.base-url:http://127.0.0.1:8000}")
    private String fastApiBase;

    public Map<String, Object> getRecommendations(String region, String jobType) {
        String url = fastApiBase + "/recommend";
        Map<String, Object> body = Map.of(
                "region", region,
                "job_type", jobType,
                "topk", 50
        );
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> req = new HttpEntity<>(body, headers);

        ResponseEntity<Map> resp = restTemplate.postForEntity(url, req, Map.class);
        return resp.getBody();
    }
}
