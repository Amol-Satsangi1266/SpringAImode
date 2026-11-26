package com.course.SpringAImode.Memory;

import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatMemoryDemo {

    private final ChatMemory chatMemory;

    public ChatMemoryDemo() {
        this.chatMemory = MessageWindowChatMemory.builder()
                .chatMemoryRepository(new InMemoryChatMemoryRepository())
                .maxMessages(10)
                .build();



    }
}
