package com.XCLONE.Budget_tracker.Controllers;

import com.XCLONE.Budget_tracker.Entity.Transaction;
import com.XCLONE.Budget_tracker.Entity.Users;
import com.XCLONE.Budget_tracker.Repository.TransactionRepository;
import com.XCLONE.Budget_tracker.Repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/AI")
@RequiredArgsConstructor
public class InsightController {

    private final TransactionRepository transactionRepo;
    private final UsersRepository userRepo;

    @GetMapping
    public Map<String, Object> getInsights(Authentication auth) {
        Users user = userRepo.findByEmail(auth.getName()).orElseThrow();

        LocalDate now = LocalDate.now();
        LocalDate startOfMonth = now.withDayOfMonth(1);

        List<Transaction> txns = transactionRepo.findByUserId(user.getId()).stream()
                .filter(tx -> tx.getDate().isAfter(startOfMonth.minusDays(1)))
                .toList();

        double totalSpent = txns.stream().mapToDouble(Transaction::getAmount).sum();
        long days = now.getDayOfMonth();
        double dailyAvg = totalSpent / ( days > 0 ? days : 1);

        Map<String, Double> categorySummary = txns.stream()
                .collect(Collectors.groupingBy(Transaction::getCategory,
                        Collectors.summingDouble(Transaction::getAmount)));

        boolean overspent = totalSpent > 10000;

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("totalSpent", totalSpent);
        result.put("dailyAverage", dailyAvg);
        result.put("categorySummary", categorySummary);
        result.put("overspendingAlert", overspent ? "You are overspending this month!" : "Spending is within limit");

        return result;
    }

    @GetMapping("/ml-data")
    public List<Map<String, Object>> getTransactionData(Authentication auth) {
        Users user = userRepo.findByEmail(auth.getName()).orElseThrow();
        List<Transaction> txns = transactionRepo.findByUserId(user.getId());

        return txns.stream().map(tx -> {
            Map<String, Object> m = new HashMap<>();
            m.put("date", tx.getDate().toString());
            m.put("amount", tx.getAmount());
            m.put("category", tx.getCategory());
            return m;
        }).toList();
    }

    @PostMapping("/ai-insights")
    public ResponseEntity<?> getAIInsights(Authentication auth) {
        Users user = userRepo.findByEmail(auth.getName()).orElseThrow();
        List<Transaction> txns = transactionRepo.findByUserId(user.getId());

        List<Map<String, Object>> txList = txns.stream().map(tx -> {
            Map<String, Object> m = new HashMap<>();
            m.put("date", tx.getDate().toString());
            m.put("amount", tx.getAmount());
            m.put("category", tx.getCategory());
            return m;
        }).toList();

        // Send to Python
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<List<Map<String, Object>>> entity = new HttpEntity<>(txList, headers);
        String url = "http://localhost:8000/predict";  // Your FastAPI server

        ResponseEntity<Map> response = new RestTemplate().postForEntity(url, entity, Map.class);
        return ResponseEntity.ok(response.getBody());
    }

    @PostMapping("/ask-ai")
    public ResponseEntity<?> askAI(@RequestBody Map<String, String> payload, Authentication auth) {
        String question = payload.get("question");
        Users user = userRepo.findByEmail(auth.getName()).orElseThrow();
        List<Transaction> txns = transactionRepo.findByUserId(user.getId());

        StringBuilder context = new StringBuilder();
        for (Transaction tx : txns) {
            context.append(String.format("Date: %s, Amount: ₹%.2f, Category: %s\n",
                    tx.getDate(), tx.getAmount(), tx.getCategory()));
        }

        String prompt = "You are a personal finance assistant. Analyze this user's transactions:\n" +
                context + "\nQuestion: " + question;

        // Call Python or directly use OpenAI/Claude
        Map<String, String> reqBody = Map.of("prompt", prompt);
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(reqBody);
        ResponseEntity<String> response = new RestTemplate().postForEntity("http://localhost:8001/gpt", entity, String.class);

        return ResponseEntity.ok(response.getBody());
    }

}
