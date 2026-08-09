package com.relocation.ordermanagement.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "customer_orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productName;
    private Double price;
    private Integer quantity;
    
    @Column(columnDefinition = "TEXT")
    private String customerNotes;

    private String status;
    private LocalDateTime createdAt;

    // GenAI Enriched Fields
    private String aiSentimentAnalysis;
    private boolean isFlaggedAsFraud;
    private String aiSuggestedCategory;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (this.status == null) this.status = "PENDING";
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public String getCustomerNotes() { return customerNotes; }
    public void setCustomerNotes(String customerNotes) { this.customerNotes = customerNotes; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }

    public String getAiSentimentAnalysis() { return aiSentimentAnalysis; }
    public void setAiSentimentAnalysis(String aiSentimentAnalysis) { this.aiSentimentAnalysis = aiSentimentAnalysis; }

    public boolean isFlaggedAsFraud() { return isFlaggedAsFraud; }
    public void setFlaggedAsFraud(boolean flaggedAsFraud) { isFlaggedAsFraud = flaggedAsFraud; }

    public String getAiSuggestedCategory() { return aiSuggestedCategory; }
    public void setAiSuggestedCategory(String aiSuggestedCategory) { this.aiSuggestedCategory = aiSuggestedCategory; }
}
