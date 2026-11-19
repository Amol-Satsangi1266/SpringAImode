package com.course.SpringAImode.Streaming;


import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import java.time.Duration;


@RestController
public class StreamingDemo {
    private final ChatClient chatClient;

    public StreamingDemo(ChatClient.Builder builder ) {
        this.chatClient = builder.build();}

    @GetMapping(
            value = "/api/streaming/sse-stream",
            produces = MediaType.TEXT_EVENT_STREAM_VALUE
    )
    public Flux<String> courseSummary(@RequestParam String topic) {

        return chatClient.prompt()
                .user("Give 5 short fun facts about " + topic)
                .stream()
                .content();
    }

    @GetMapping(
            value = "/api/streaming/test",
            produces = MediaType.TEXT_EVENT_STREAM_VALUE
    )
    public Flux<String> testStreaming() {

        return Flux.interval(Duration.ofSeconds(1))
                .take(5)
                .map(i -> "Chunk " + i);
    }
}
