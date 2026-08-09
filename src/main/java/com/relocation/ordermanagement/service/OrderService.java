package com.relocation.ordermanagement.service;

import com.relocation.ordermanagement.model.Order;
import com.relocation.ordermanagement.model.OrderAnalysisResponse;
import com.relocation.ordermanagement.repository.OrderRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final AiAnalysisService aiAnalysisService;

    public OrderService(OrderRepository orderRepository, AiAnalysisService aiAnalysisService) {
        this.orderRepository = orderRepository;
        this.aiAnalysisService = aiAnalysisService;
    }

    public Order processAndSaveOrder(Order order) {
        // 1. Invoke GenAI Enrichment using prompt variables
        OrderAnalysisResponse aiResult = aiAnalysisService.performEnrichment(
                order.getProductName(),
                order.getPrice(),
                order.getCustomerNotes()
        );

        // 2. Inject structured AI analysis fields into our domain model
        order.setAiSentimentAnalysis(aiResult.getSentiment());
        order.setFlaggedAsFraud(aiResult.isPotentialFraud());
        order.setAiSuggestedCategory(aiResult.getCategory());

        // 3. Handle inline system flag if fraud risk is caught by LLM
        if (aiResult.isPotentialFraud()) {
            order.setStatus("FLAGGED_FOR_REVIEW");
        } else {
            order.setStatus("CONFIRMED");
        }

        // 4. Save directly into PostgreSQL
        return orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}
