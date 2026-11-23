package com.course.SpringAImode.Advisors;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.AdvisorChain;
import org.springframework.ai.chat.client.advisor.api.BaseAdvisor;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;

import java.util.List;

public class DisclaimerAdvisor implements BaseAdvisor {

    int order;
    String DISCLAIMER;

    public DisclaimerAdvisor(int order, String DISCLAIMER) {
        this.order = order;
        this.DISCLAIMER = DISCLAIMER;
    }

    @Override
    public ChatClientRequest before(ChatClientRequest chatClientRequest, AdvisorChain advisorChain) {
        return chatClientRequest;
    }

    @Override
    public ChatClientResponse after(ChatClientResponse chatClientResponse, AdvisorChain advisorChain) {

        ChatResponse chatResponse = chatClientResponse.chatResponse();

        if(chatClientResponse == null || chatResponse.getResult() == null)
        { return  chatClientResponse;
        }

        AssistantMessage message = chatResponse.getResult().getOutput();
        AssistantMessage withDisclaimer = AssistantMessage.builder()
                .content(message.getText() + DISCLAIMER)
                .properties(message.getMetadata())
                .toolCalls(message.getToolCalls())
                .media(message.getMedia())
                .build();

        Generation updatedGeneration = new Generation(withDisclaimer,
                chatResponse.getResult().getMetadata());

        ChatResponse newResponse = ChatResponse.builder()
                .from(chatResponse)
                .generations(List.of(updatedGeneration))
                .build();

        return chatClientResponse.mutate()
                .chatResponse(newResponse)
                .build();
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
