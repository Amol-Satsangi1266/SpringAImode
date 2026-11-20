package com.course.SpringAImode.Advisors;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdvisorsDemo {
    private final ChatClient chatClient;

    public AdvisorsDemo(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @GetMapping("/advisors")
    public String advise(@RequestParam String topic){

        String prompt = """
                Give 5 facts about this %s in 2 lines
                """.formatted(topic);
        SimpleLoggerAdvisor logger = SimpleLoggerAdvisor.builder()
                .requestToString(request->"Sending prompt" + request.prompt().getContents())
                .responseToString(response->"GOT BACK:" + response.getResult().getOutput().getText())
                .build();
        return chatClient.prompt()
                .user(prompt)
                .advisors(new SimpleLoggerAdvisor())
                .call()
                .content();
    }

}
