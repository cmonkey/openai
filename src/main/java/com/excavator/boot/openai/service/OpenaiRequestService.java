package com.excavator.boot.openai.service;

import com.theokanning.openai.OpenAiService;
import com.theokanning.openai.completion.CompletionChoice;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.model.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OpenaiRequestService {
    public static final Logger log = LoggerFactory.getLogger(OpenaiRequestService.class);

    private final OpenAiService openAiService;

    public OpenaiRequestService(OpenAiService openAiService) {
        this.openAiService = openAiService;
    }

    public Optional<List<Model>> queryModels(){
        var models = openAiService.listModels();
        return Optional.ofNullable(models);
    }

    public Optional<List<String>> doRequest(String prompt){
        var request = CompletionRequest.builder()
                .prompt(prompt)
                .model("text-davinci-003")
                .maxTokens(1024)
                .n(1)
                .stop(null)
                .temperature(0.5)
                .echo(true)
                .build();
        var choices = openAiService.createCompletion(request).getChoices();
        choices.forEach(System.out::println);
        var textList = choices.stream().map(CompletionChoice::getFinish_reason).toList();
        return Optional.of(textList);
    }
}
