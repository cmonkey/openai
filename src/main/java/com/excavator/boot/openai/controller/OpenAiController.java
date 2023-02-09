package com.excavator.boot.openai.controller;

import com.excavator.boot.openai.service.OpenaiRequestService;
import com.theokanning.openai.model.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequestMapping("/openai/v1")
public class OpenAiController {
    public static final Logger log = LoggerFactory.getLogger(OpenAiController.class);

    private final OpenaiRequestService openaiRequestService;

    public OpenAiController(OpenaiRequestService openaiRequestService) {
        this.openaiRequestService = openaiRequestService;
    }

    @GetMapping("/models")
    public Flux<List<Model>> queryModels(){
        return openaiRequestService.queryModels().map(Flux::just).orElse(Flux.empty());
    }
}
