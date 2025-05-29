package com.XCLONE.Budget_tracker.Services;

import com.XCLONE.Budget_tracker.Entity.AiResponse;
import com.XCLONE.Budget_tracker.Entity.InsightQuery;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AiService {

    @Value("${ai.service.url}")
    private String aiServiceUrl;

    private final RestTemplate restTemplate;

    public AiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getInsight(InsightQuery query) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<InsightQuery> request = new HttpEntity<>(query, headers);

        ResponseEntity<AiResponse> response = restTemplate.postForEntity(
                aiServiceUrl + "/rag", request, AiResponse.class);

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            return response.getBody().getAnswer();
        } else {
            return "Failed to retrieve insight";
        }
    }
}
