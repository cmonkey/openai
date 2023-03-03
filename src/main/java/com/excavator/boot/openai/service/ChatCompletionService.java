package com.excavator.boot.openai.service;

import com.excavator.boot.openai.GptModelEnum;
import com.theokanning.openai.OpenAiService;
import com.theokanning.openai.completion.CompletionChoice;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
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

    public Optional<List<String>> doPrompt(List<ChatMessage> messages, GptModelEnum gptModelEnum){
        var request = ChatCompletionRequest.builder()
                .model(gptModelEnum.getName())
                .messages(messages)
                .topP(0.1)
                .n(1)
                .stop(null)
                .maxTokens(1024)
                .build();
        var choices = openAiService.createChatCompletion(request).getChoices();
        choices.forEach(System.out::println);
        var textList = choices.stream().map(ChatCompletionChoice::getFinishReason).toList();
        return Optional.of(textList);
    }
}
