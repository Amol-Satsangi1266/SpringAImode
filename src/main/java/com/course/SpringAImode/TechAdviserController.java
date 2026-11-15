package com.course.SpringAImode;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class TechAdviserController {

    private final ChatClient chatClient;


    public TechAdviserController(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @GetMapping("/advise")
    public String advise(){
        return chatClient.prompt()
                .user("Explain Spring boot in simple terms")
                .call()
                .content();
    }
}
