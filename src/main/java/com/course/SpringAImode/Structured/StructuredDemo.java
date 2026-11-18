package com.course.SpringAImode.Structured;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StructuredDemo {

    private final ChatClient chatClient;

    public StructuredDemo(ChatClient.Builder builder ) {
        this.chatClient = builder.build();}

    @Value("classpath:/prompts/recipe-prompt.st")
    private Resource resourcePrompt;

    @GetMapping("/structured")
    public CourseSummary courseSummary(@RequestParam String topic)
    {
        String prompt = """
        Generate a structured course summary for the topic: %s.
        
        Provide the following details:
        - A clear and engaging course title.
        - The appropriate difficulty level (e.g., Beginner, Intermediate, or Advanced).
        - A list of 3 to 5 specific learning objectives.
        - The realistic estimated hours required to complete the course as a whole number.
        """.formatted(topic);

        return chatClient.prompt()
                .user(prompt)
                .call()
                .entity(CourseSummary.class);
    }


}
