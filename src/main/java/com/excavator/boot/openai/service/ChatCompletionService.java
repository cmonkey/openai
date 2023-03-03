package com.excavator.boot.openai.service;

import com.excavator.boot.openai.GptModelEnum;
import com.theokanning.openai.OpenAiService;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ChatCompletionService {
    public static final Logger log = LoggerFactory.getLogger(ChatCompletionService.class);

    private final Map<String,List<ChatMessage>> messageMap = new ConcurrentHashMap<>();

    private final OpenAiService openAiService;

    public ChatCompletionService(OpenAiService openAiService) {
        this.openAiService = openAiService;
    }

    public Optional<String> userChat(String msgId,List<ChatMessage> messages){
        var oldMessages = messageMap.get(msgId);
        if(CollectionUtils.isEmpty(oldMessages)){
            var message = doPrompt(messages,GptModelEnum.GPT_3_5_TURBO).map(responseChatMessages -> {
                var newMessages = new ArrayList<ChatMessage>();
                newMessages.addAll(messages);
                newMessages.addAll(responseChatMessages);

                messageMap.put(msgId,newMessages);

                responseChatMessages.forEach(chatMessage -> {
                    log.info("role is [{}] content is [{}]", chatMessage.getRole(), chatMessage.getContent());
                });
                return responseChatMessages.get(0).getContent();
            }).orElse(null);
            return Optional.ofNullable(message);
        }else{
            oldMessages.addAll(messages);
            var message = doPrompt(oldMessages, GptModelEnum.GPT_3_5_TURBO).map(responseChatMessage -> {
                oldMessages.addAll(responseChatMessage);
                messageMap.put(msgId, oldMessages);
                responseChatMessage.forEach(chatMessage -> {
                    log.info("role is [{}] content is [{}]", chatMessage.getRole(), chatMessage.getContent());
                });
                return responseChatMessage.get(0).getContent();
            }).orElse(null);
            return Optional.ofNullable(message);
        }
    }

    public Optional<List<ChatMessage>> doPrompt(List<ChatMessage> messages, GptModelEnum gptModelEnum){
        log.info("doPrompt input chat messages is [{}]", messages);
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
        var choiceList = choices.stream().map(choice -> choice.getMessage()).toList();
        return Optional.of(choiceList);
    }
}
