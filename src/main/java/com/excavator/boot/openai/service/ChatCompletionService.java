package com.excavator.boot.openai.service;

import com.excavator.boot.openai.GptModelEnum;
import com.theokanning.openai.OpenAiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChatCompletionService {
    public static final Logger log = LoggerFactory.getLogger(ChatCompletionService.class);

    private final OpenAiService openAiService;

    public ChatCompletionService(OpenAiService openAiService) {
        this.openAiService = openAiService;
    }

    public Optional<List<String>> doPrompt(String prompt, GptModelEnum gptModelEnum){
        //TODO fix me
        return Optional.empty();
    }
}
