package com.course.SpringAImode;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class TechAdviserController {

    private final ChatClient chatClient;


    public TechAdviserController(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @GetMapping("/advise")
    public String advise(){
        return chatClient.prompt()
                .user("Explain Spring boot in simple terms") //REQUEST
                .call() // CALL
                .content(); //RESPONSE
    }

    @GetMapping("/conversation-history")
    public String conversationHistory(){

        Message systemMessage = new SystemMessage("You are a helpful polite Java Developer");
        Message userMessage = new UserMessage("What is a user Message?");
        Prompt prompt = new Prompt(List.of(systemMessage,userMessage));

        return chatClient.prompt(prompt)
                .call()
                .content();
    }
}
