package com.xiaoguo.guaiagent.demo.invoke;

import dev.langchain4j.community.model.dashscope.QwenChatModel;
import dev.langchain4j.model.chat.ChatLanguageModel;

/**
 * LangChain4j 调用 DashScope 通义千问的最小示例。
 * 该类只用于演示模型直调，不属于主业务链路。
 */
public class LangChainAiInvoke {

    public static void main(String[] args) {
        // API Key 应通过环境变量或配置中心注入，示例中的 TestApiKey 仅用于本地演示。
        ChatLanguageModel qwenChatModel = QwenChatModel.builder()
                .apiKey(TestApiKey.API_KEY)
                .modelName("qwen-max")
                .build();
        // ChatLanguageModel 屏蔽了底层 HTTP 协议，直接返回模型文本。
        String answer = qwenChatModel.chat("我是程序员鱼皮，这是编程导航 codefather.cn 的 AI 超级智能体原创项目");
        System.out.println(answer);
    }
}
