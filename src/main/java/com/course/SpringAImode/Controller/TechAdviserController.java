package com.course.SpringAImode.Controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RestController
public class TechAdviserController {

    private final ChatClient chatClient;
    private final ChatClient chatClientwithdefaultparams;
    @Value("classpath:/prompts/recipe-prompt.st")
    private Resource resourcePrompt;


    public TechAdviserController(ChatClient.Builder builder ) {
        this.chatClient = builder.build();

        this.chatClientwithdefaultparams = builder.clone()
                .defaultSystem("You are a java interviewer after explaining also ask a follow up question.")
                .build();
    }



    @GetMapping("/advise")
    public String advise(){
        return chatClient.prompt()
                .user("Explain Spring boot in simple terms") //REQUEST
                .call() // CALL
                .content(); //RESPONSE
    }

    @GetMapping("/defres")
    public String defaultresponse()
    {
        return chatClientwithdefaultparams.prompt()
                .user("Explain AI")
                .call()
                .content();
    }

    @GetMapping("/conversation-history")
    public String conversationHistory(){

        Message systemMessage = new SystemMessage("You are a helpful polite Java Developer");
        Message userMessage = new UserMessage("What is a user Message?");
        Message priorReply = new AssistantMessage("In general Java programming, a user message usually refers to either text displayed to prompt or inform the user (using System.out.println or JOptionPane.showMessageDialog) or text entered by the user via tools like the Java Scanner Class.");
        Message newUserMessage = new UserMessage("How to code it?");
        List<Message> messages = new ArrayList<>();

        messages.add(systemMessage);
        messages.add(userMessage);
        messages.add(priorReply);
        messages.add(newUserMessage);

        Prompt prompt = new Prompt(messages);

        return chatClient.prompt(prompt)
                .call()
                .content();
    }



    @GetMapping("/prompt-variables")
    public String promptVariables(@RequestParam String language, @RequestParam String task)
    {
        PromptTemplate template = new PromptTemplate("Write a short note on {language} language and do {task} in this. ");
        Prompt prompt = template.create(Map.of(
                "language", language,
                "task", task
        ));

        return "<pre>" +  chatClient.prompt(prompt)
                .call()
                .content() + "</pre>";
    }

    @GetMapping("/prompt-variables1")
    public String promptVariables(@RequestParam String ingredient) {
        PromptTemplate template = new PromptTemplate(resourcePrompt);
        Prompt prompt = template.create(Map.of("ingredient",ingredient));

        return "<pre>" +  chatClient.prompt(prompt)
                .call()
                .content() + "</pre>";

    }
}

