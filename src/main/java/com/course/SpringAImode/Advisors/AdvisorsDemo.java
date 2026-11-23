package com.course.SpringAImode.Advisors;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SafeGuardAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
                .advisors(logger)
                .call()
                .content();
    }


    @GetMapping("/safeguard")
    public String safeguard(@RequestParam String topic){

        String prompt = """
                Give 5 facts about this %s in 2 lines
                """.formatted(topic);

        SafeGuardAdvisor advisor = SafeGuardAdvisor.builder()
                .sensitiveWords(List.of("password","credit card"))
                .failureResponse("Sorry cant do that ;( ERROR :(")
                .build();


        return chatClient.prompt()
                .user(prompt)
                .advisors(advisor)
                .call()
                .content();
    }

    @GetMapping("/persona")
    public String safeguard(@RequestParam String persona,
                            @RequestParam String topic){

        String prompt = """
                Give 5 facts about this %s in 2 lines
                """.formatted(topic);

        return chatClient.prompt()
                .user(prompt)
                .advisors(new PersonaAdvisor(persona,0))
                .call()
                .content();
    }

    @GetMapping("/timing")
    public String timing(@RequestParam String topic){

        String prompt = """
                Give 5 facts about this %s in 2 lines
                """.formatted(topic);

        return chatClient.prompt()
                .user(prompt)
                .advisors(new TimingAdvisor(0))
                .call()
                .content();
    }

}
