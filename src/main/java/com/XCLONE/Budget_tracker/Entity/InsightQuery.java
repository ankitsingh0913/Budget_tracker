package com.XCLONE.Budget_tracker.Entity;

import java.util.List;
import java.util.Map;

public class InsightQuery {
    private List<Map<String, Object>> transactions;
    private String question;

    // Getters and setters
    public List<Map<String, Object>> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Map<String, Object>> transactions) {
        this.transactions = transactions;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
