package com.excavator.boot.openai;

import com.excavator.boot.openai.service.OpenaiRequestService;
import com.theokanning.openai.model.Model;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OpenaiApplicationTests {

	@Resource
	private OpenaiRequestService openaiRequestService;

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

	@Test
	@DisplayName("test gpt3 model by model name is gpt-3.5-turbo-0301")
	public void testGpt3ModelByModelNameIsGpt35turbo0303(){
		var prompt = "介绍一下你自己";
		var optional = openaiRequestService.doRequest(prompt, GptModelEnum.GPT_3_5_TURBO_0301);
		assertNotNull(optional);
		var messages = optional.get();
		assertNotNull(messages);
	}

	@Test
	@DisplayName("test gpt3 model by model name is gpt-3.5-turbo")
	public void testGpt3ModelByModelNameIsGpt35Turbo(){
		var prompt = "gpt3.5 模型都可以实现什么";
		var optional = openaiRequestService.doRequest(prompt, GptModelEnum.GPT_3_5_TURBO);
		assertNotNull(optional);
		var messages = optional.get();
		assertNotNull(messages);
	}

}
