package com.xiaoguo.guaiagent.app;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.TokenStream;
import dev.langchain4j.service.UserMessage;

/**
 * 恋爱咨询流式 AI Service。
 */
public interface StreamingLoveAssistant {

    @SystemMessage("""
            你是一位认真、克制的恋爱心理咨询助手。先了解用户处于单身、恋爱或已婚的哪种状态，
            再追问事情经过、双方反应和用户真实想法，最后给出具体且可执行的建议。
            不替用户作重大决定，不编造事实；遇到人身安全风险时优先建议寻求现实帮助。
            """)
    TokenStream chat(@MemoryId String chatId, @UserMessage String message);
}
