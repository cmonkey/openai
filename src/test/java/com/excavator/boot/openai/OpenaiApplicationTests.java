package com.excavator.boot.openai;

import com.excavator.boot.openai.service.OpenaiRequestService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class OpenaiApplicationTests {

	@Resource
	private OpenaiRequestService openaiRequestService;

	@Test
	@DisplayName("test prompt test")
	public void testPromptTest(){
		var prompt = "如何主持ChatGPT?";
		var optional = openaiRequestService.doRequest(prompt);
		assertNotNull(optional);
		var messages = optional.get();
		assertNotNull(messages);
	}

	@Test
	@DisplayName("test query models")
	public void testQueryModles(){
		var optional = openaiRequestService.queryModels();
		assertNotNull(optional);
		var models = optional.get();
		assertNotNull(models);
		models.forEach(System.out::println);
	}

}
