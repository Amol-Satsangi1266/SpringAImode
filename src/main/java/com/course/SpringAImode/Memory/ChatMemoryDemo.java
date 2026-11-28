package com.course.SpringAImode.Memory;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatMemoryDemo {

    private final ChatMemory chatMemory;
    private final ChatClient chatClient;

    public ChatMemoryDemo(ChatClient.Builder builder) {

        this.chatClient = builder.build();

        this.chatMemory = MessageWindowChatMemory.builder()
                .chatMemoryRepository(new InMemoryChatMemoryRepository())
                .maxMessages(10)
                .build();
    }


    @GetMapping("/memory")
    public String chatMemoryfirst(@RequestParam String Message)
    {
        return chatClient.prompt()
                .user(Message)
                .advisors(a->
                        a.advisors(
                        MessageChatMemoryAdvisor.builder(chatMemory).build())
                                .param(ChatMemory.CONVERSATION_ID,"shared"))
                .call()
                .content();
    }

    @GetMapping("/memorynew")
    public String chatMemorysecond(@RequestParam String Message, @RequestParam String id)
    {
        return chatClient.prompt()
                .user(Message)
                .advisors(a->
                        a.advisors(
                                        MessageChatMemoryAdvisor.builder(chatMemory).build())
                                .param(ChatMemory.CONVERSATION_ID,id))
                .call()
                .content();
    }
}
