package com.xiaoguo.guaiagent.agent;

import dev.langchain4j.service.TokenStream;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

/**
 * LangChain4j 任务型智能体的业务封装。
 */
@Component
@ConditionalOnProperty(prefix = "langchain4j", name = "enabled", havingValue = "true")
public class GuoManus {

    private final ManusAssistant assistant;

    public GuoManus(ManusAssistant assistant) {
        this.assistant = assistant;
    }

    public SseEmitter runStream(String message) {
        SseEmitter emitter = new SseEmitter(300_000L);
        TokenStream stream = assistant.run(message);
        stream.onPartialResponse(token -> send(emitter, token))
                .onToolExecuted(execution -> send(emitter,
                        "\n[工具] " + execution.request().name() + "\n"))
                .onCompleteResponse(response -> emitter.complete())
                .onError(emitter::completeWithError)
                .start();
        return emitter;
    }

    private void send(SseEmitter emitter, String text) {
        try {
            emitter.send(SseEmitter.event().data(text));
        } catch (IOException e) {
            emitter.completeWithError(e);
        }
    }
}
