package com.xiaoguo.guaiagent.app;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class LoveAppTest {

    @Test
    void delegatesSynchronousChatToLangChain4jService() {
        LoveAssistant assistant = mock(LoveAssistant.class);
        StreamingLoveAssistant streamingAssistant = mock(StreamingLoveAssistant.class);
        when(assistant.chat("chat-1", "你好")).thenReturn("你好，请说说你的情况");

        LoveApp loveApp = new LoveApp(assistant, streamingAssistant);

        assertEquals("你好，请说说你的情况", loveApp.doChat("你好", "chat-1"));
    }
}
