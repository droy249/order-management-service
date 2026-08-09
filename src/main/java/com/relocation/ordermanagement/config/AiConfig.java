package com.relocation.ordermanagement.config;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.anthropic.AnthropicChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class AiConfig {

    @Value("${anthropic.api.key}")
    private String apiKey;

    @Bean
    public ChatLanguageModel chatLanguageModel() {
        return AnthropicChatModel.builder()
                .apiKey(apiKey)
                .modelName("claude-3-5-sonnet-20240620") // Elite intelligence for JSON parsing
                .temperature(0.1)                         // Keeps responses highly deterministic
                .timeout(Duration.ofSeconds(30))          // Prevents the API from hanging on slow connections
                .build();
    }
}
