package com.excavator.boot.openai.controller;

import com.excavator.boot.openai.GptModelEnum;
import com.excavator.boot.openai.service.ChatCompletionService;
import com.excavator.boot.openai.service.OpenaiRequestService;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.model.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

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
    public Flux<List<String>> chat(@RequestBody List<ChatMessage> messages){
        log.info("input is [{}]", messages);
        var r = chatCompletionService.doPrompt(messages, GptModelEnum.GPT_3_5_TURBO);
        return r.map(Flux::just).orElse(Flux.empty());
    }
}
