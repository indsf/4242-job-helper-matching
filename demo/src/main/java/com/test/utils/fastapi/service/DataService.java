package com.test.utils.fastapi.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class DataService {

    private final RestTemplate restTemplate = new RestTemplate();

    public List<Map<String, Object>> fetchRecommendations(String region, String disability, int pay) {
        try {
            String url = UriComponentsBuilder.fromHttpUrl("http://localhost:8000/recommend")
                    .queryParam("region", region)
                    .queryParam("disability", disability)
                    .queryParam("pay", pay)
                    .toUriString();

            // FastAPI에서 반환된 JSON을 Map으로 받음
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);

            if (response == null || !response.containsKey("recommendations")) {
                return Collections.emptyList();
            }

            // 안전하게 캐스팅
            Object recs = response.get("recommendations");
            if (!(recs instanceof List)) {
                return Collections.emptyList();
            }

            return (List<Map<String, Object>>) recs;

        } catch (Exception e) {
            // 예외 발생 시 빈 리스트 반환
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
