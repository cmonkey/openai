package com.excavator.boot.openai;

import com.excavator.boot.openai.service.ChatCompletionService;
import com.excavator.boot.openai.service.OpenaiRequestService;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.model.Model;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OpenaiApplicationTests {

	@Resource
	private OpenaiRequestService openaiRequestService;

	@Resource
	private ChatCompletionService chatCompletionService;

	@Test
	@DisplayName("test prompt test")
	public void testPromptTest(){
		var prompt = "如何问出一个好问题";
		var optional = openaiRequestService.doRequest(prompt);
		assertNotNull(optional);
		var messages = optional.get();
		assertNotNull(messages);
	}

	@Test
	@DisplayName("test query models")
	public void testQueryModels(){
		var optional = openaiRequestService.queryModels();
		assertNotNull(optional);
		var models = optional.get();
		assertNotNull(models);
		models.forEach(System.out::println);
		var text_davinci_003_model = "text-davinci-003";
		var modelMatch = models.stream().map(Model::getId).anyMatch(id -> id.equals(text_davinci_003_model));
		assertTrue(modelMatch);
	}

	@Test
	@DisplayName("test query files")
	public void testQueryFiles(){
		var optional = openaiRequestService.queryFiles();
		assertNotNull(optional);
		var files = optional.get();
		assertNotNull(files);
		files.forEach(System.out::println);
	}

	private void applyProxy(){
		System.setProperty("http.proxyHost", "127.0.0.1");
		System.setProperty("http.proxyPort", "1080");
		System.setProperty("https.proxyPort", "1080");
		System.setProperty("https.proxyPort", "1080");
		System.setProperty("socksProxyHost", "127.0.0.1");
		System.setProperty("socksProxyPort", "1080");
	}

	@Test
	@DisplayName("test gpt3 model by model name is gpt-3.5-turbo-0301")
	public void testGpt3ModelByModelNameIsGpt35turbo0303(){
		applyProxy();
		var messages = queryChatMessages();
		var optional = chatCompletionService.doPrompt(messages, GptModelEnum.GPT_3_5_TURBO_0301);
		assertNotNull(optional);
		var responseMessage = optional.get();
		assertNotNull(responseMessage);
	}

	private List<ChatMessage> queryChatMessages(){
		var systemChatMessage = new ChatMessage();
		systemChatMessage.setRole("system");
		systemChatMessage.setContent("输出为中文");

		var userChatMessage = new ChatMessage();
		userChatMessage.setRole("介绍一下清末民国的人口贩卖情况");

		return List.of(systemChatMessage, userChatMessage);
	}

	@Test
	@DisplayName("test gpt3 model by model name is gpt-3.5-turbo")
	public void testGpt3ModelByModelNameIsGpt35Turbo(){
		applyProxy();
		var messages = queryChatMessages();
		var optional = chatCompletionService.doPrompt(messages, GptModelEnum.GPT_3_5_TURBO);
		assertNotNull(optional);
		var responseMessage = optional.get();
		assertNotNull(responseMessage);
	}

}
