package com.XCLONE.Budget_tracker.Controllers;

import com.XCLONE.Budget_tracker.Entity.InsightQuery;
import com.XCLONE.Budget_tracker.Services.AiService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/insights")
public class AiController {

    private final AiService aiService;

    public AiController(AiService aiService) {
        this.aiService = aiService;
    }

    @PostMapping
    public String getInsight(@RequestBody InsightQuery query) {
        return aiService.getInsight(query);
    }
}
