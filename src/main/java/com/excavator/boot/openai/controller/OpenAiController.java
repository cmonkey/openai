package com.excavator.boot.openai.controller;

import com.excavator.boot.openai.GptModelEnum;
import com.excavator.boot.openai.entity.UserChatMessage;
import com.excavator.boot.openai.service.ChatCompletionService;
import com.excavator.boot.openai.service.OpenaiRequestService;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.model.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/openai/v1")
public class OpenAiController {
    public static final Logger log = LoggerFactory.getLogger(OpenAiController.class);

    private final OpenaiRequestService openaiRequestService;
    private final ChatCompletionService chatCompletionService;

    public OpenAiController(OpenaiRequestService openaiRequestService, ChatCompletionService chatCompletionService) {
        this.openaiRequestService = openaiRequestService;
        this.chatCompletionService = chatCompletionService;
    }

    @GetMapping("/models")
    public Flux<List<Model>> queryModels(){
        return openaiRequestService.queryModels().map(Flux::just).orElse(Flux.empty());
    }

    @PostMapping("/chat")
    public Mono<String> chat(@RequestBody UserChatMessage userChatMessage){
        var msgId = userChatMessage.getMsgId();
        var chatMessageList = userChatMessage.getChatMessageList();
        log.info("input msgId is [{}] chatMessages is [{}]", msgId, chatMessageList);
        var r = chatCompletionService.userChat(msgId, chatMessageList);
        return r.map(Mono::just).orElse(Mono.empty());
    }
}
