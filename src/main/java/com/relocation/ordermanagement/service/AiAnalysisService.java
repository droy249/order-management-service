package com.relocation.ordermanagement.service;

import com.relocation.ordermanagement.model.OrderAnalysisResponse;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.UserMessage;
import org.springframework.stereotype.Service;

@Service
public class AiAnalysisService {

    private final OrderAnalyzer orderAnalyzer;

    // Internal structural interface for LangChain4j extraction
    interface OrderAnalyzer {
        @UserMessage("""
            Analyze the following e-commerce order details.
            Product: {{productName}}
            Total Price: {{price}}
            Customer Notes: "{{customerNotes}}"
            
            Provide a structured analysis classifying the customer sentiment (POSITIVE/NEUTRAL/NEGATIVE), 
            if the order notes show patterns of potential fraud/scams (true/false), and a suggested department category.
            """)
        OrderAnalysisResponse analyzeOrder(String productName, Double price, String customerNotes);
    }

    public AiAnalysisService(ChatLanguageModel chatLanguageModel) {
        // Instruct LangChain4j to implement our structural parsing dynamically
        this.orderAnalyzer = AiServices.builder(OrderAnalyzer.class)
                .chatLanguageModel(chatLanguageModel)
                .build();
    }

    public OrderAnalysisResponse performEnrichment(String product, Double price, String notes) {
        if (notes == null || notes.isBlank()) {
            OrderAnalysisResponse fallback = new OrderAnalysisResponse();
            fallback.setSentiment("NEUTRAL");
            fallback.setPotentialFraud(false);
            fallback.setCategory("GENERAL");
            return fallback;
        }
        return orderAnalyzer.analyzeOrder(product, price, notes);
    }
}
