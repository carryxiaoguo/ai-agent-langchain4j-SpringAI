package com.xiaoguo.guaiagent.app;

import dev.langchain4j.service.TokenStream;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * 保留原有业务入口，内部完全由 LangChain4j 实现。
 */
@Component
@ConditionalOnProperty(prefix = "langchain4j", name = "enabled", havingValue = "true")
public class LoveApp {

    private final LoveAssistant assistant;
    private final StreamingLoveAssistant streamingAssistant;

    public LoveApp(LoveAssistant assistant, StreamingLoveAssistant streamingAssistant) {
        this.assistant = assistant;
        this.streamingAssistant = streamingAssistant;
    }

    public String doChat(String message, String chatId) {
        return assistant.chat(chatId, message);
    }

    public TokenStream doChatByStream(String message, String chatId) {
        return streamingAssistant.chat(chatId, message);
    }
}
