package com.relocation.ordermanagement.model;

public class OrderAnalysisResponse {
    private String sentiment;
    private boolean potentialFraud;
    private String category;

    // Getters and Setters
    public String getSentiment() { return sentiment; }
    public void setSentiment(String sentiment) { this.sentiment = sentiment; }

    public boolean isPotentialFraud() { return potentialFraud; }
    public void setPotentialFraud(boolean potentialFraud) { this.potentialFraud = potentialFraud; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
}
    