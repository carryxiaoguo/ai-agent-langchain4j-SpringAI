package com.xiaoguo.guaiagent.controller;

import com.xiaoguo.guaiagent.agent.GuoManus;
import com.xiaoguo.guaiagent.app.LoveApp;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

/**
 * LangChain4j AI 接口，路径与原前端保持兼容。
 */
@RestController
@RequestMapping("/ai")
@ConditionalOnProperty(prefix = "langchain4j", name = "enabled", havingValue = "true")
public class AiController {

    private final LoveApp loveApp;
    private final GuoManus guoManus;

    public AiController(LoveApp loveApp, GuoManus guoManus) {
        this.loveApp = loveApp;
        this.guoManus = guoManus;
    }

    @GetMapping("/love_app/chat/sync")
    public String doChatWithLoveAppSync(String message, String chatId) {
        return loveApp.doChat(message, chatId);
    }

    @GetMapping(value = "/love_app/chat/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter doChatWithLoveAppSse(String message, String chatId) {
        SseEmitter emitter = new SseEmitter(180_000L);
        loveApp.doChatByStream(message, chatId)
                .onPartialResponse(token -> send(emitter, token))
                .onCompleteResponse(response -> emitter.complete())
                .onError(emitter::completeWithError)
                .start();
        return emitter;
    }

    @GetMapping(value = "/manus/chat", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter doChatWithManus(String message) {
        return guoManus.runStream(message);
    }

    private void send(SseEmitter emitter, String text) {
        try {
            emitter.send(SseEmitter.event().data(text));
        } catch (IOException e) {
            emitter.completeWithError(e);
        }
    }
}
