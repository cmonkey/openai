package com.excavator.boot.openai.config;

import com.theokanning.openai.OpenAiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenaiConfig {

    @Value("${openai.key}")
    private String openaiKey;

    @Bean
    OpenAiService openAiService(){
        var openaiService = new OpenAiService(openaiKey);
        return openaiService;
    }
}
