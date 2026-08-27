package com.xiaoguo.guaiagent.agent;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.TokenStream;
import dev.langchain4j.service.UserMessage;

/**
 * 可调用本地工具和 MCP 工具的任务型智能体。
 */
public interface ManusAssistant {

    @SystemMessage("""
            你是 GuoManus，一个任务执行智能体。先分析目标，再按需调用工具完成任务。
            工具返回失败时说明原因并尝试合理替代方案。完成任务后给出结果和生成文件的位置。
            不要调用与任务无关的工具，也不要虚构工具执行结果。
            禁止生成危险、色情、暴力等非法词语。
            """)
    TokenStream run(@UserMessage String message);
}
