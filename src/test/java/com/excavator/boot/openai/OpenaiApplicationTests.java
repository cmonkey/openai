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
		var prompt = "如何主持ChatGPT?";
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

}
