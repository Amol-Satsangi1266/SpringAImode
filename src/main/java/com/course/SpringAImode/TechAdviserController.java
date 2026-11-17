package com.course.SpringAImode;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
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
}
