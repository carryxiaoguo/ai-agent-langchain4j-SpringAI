package com.xiaoguo.guaiagent.langchain4j.config;

import com.xiaoguo.guaiagent.langchain4j.app.LangChain4jLoveApp;
import dev.langchain4j.community.model.dashscope.QwenChatModel;
import dev.langchain4j.model.chat.ChatLanguageModel;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;

/**
 * LangChain4j 组件配置，与现有 Spring AI Bean 相互隔离。
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(prefix = "langchain4j", name = "enabled", havingValue = "true")
@EnableConfigurationProperties(LangChain4jProperties.class)
public class LangChain4jConfiguration {

    @Bean("langChain4jChatModel")
    public ChatLanguageModel langChain4jChatModel(LangChain4jProperties properties) {
        // 启用 LangChain4j 时必须显式提供模型 API Key。
        Assert.hasText(properties.getApiKey(), "请配置 langchain4j.api-key");
        return QwenChatModel.builder()
                .apiKey(properties.getApiKey())
                .modelName(properties.getModelName())
                .build();
    }

    @Bean
    public LangChain4jLoveApp langChain4jLoveApp(ChatLanguageModel langChain4jChatModel) {
        // 使用独立 Bean，避免与 Spring AI 的 ChatModel 混淆。
        return new LangChain4jLoveApp(langChain4jChatModel);
    }
}
