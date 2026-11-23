package com.course.SpringAImode.Advisors;

import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.AdvisorChain;
import org.springframework.ai.chat.client.advisor.api.BaseAdvisor;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;

import java.util.List;

public class PersonaAdvisor implements BaseAdvisor {

    String persona;
    int order;

    public PersonaAdvisor(String persona, int order) {
        this.persona = persona;
        this.order = order;
    }


    @Override
    public ChatClientRequest before(ChatClientRequest chatClientRequest, AdvisorChain advisorChain) {

        Prompt augmented = chatClientRequest.prompt()
                .augmentSystemMessage("You are " + persona + "Please stay in character and answer within two lines aboutt the topic.");
        return chatClientRequest.mutate()
                .prompt(augmented)
                .build();
    }

    @Override
    public ChatClientResponse after(ChatClientResponse chatClientResponse, AdvisorChain advisorChain) {
        return chatClientResponse;
    }

    @Override
    public int getOrder() {
        return order;
    }
}
