package com.xiaoguo.guaiagent.config;

import com.xiaoguo.guaiagent.agent.ManusAssistant;
import com.xiaoguo.guaiagent.app.LoveAssistant;
import com.xiaoguo.guaiagent.app.StreamingLoveAssistant;
import com.xiaoguo.guaiagent.tools.ToolRegistration.LocalTools;
import dev.langchain4j.community.model.dashscope.QwenChatModel;
import dev.langchain4j.community.model.dashscope.QwenStreamingChatModel;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.mcp.McpToolProvider;
import dev.langchain4j.mcp.client.DefaultMcpClient;
import dev.langchain4j.mcp.client.McpClient;
import dev.langchain4j.mcp.client.transport.http.HttpMcpTransport;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.tool.ToolProvider;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * LangChain4j 模型、AI Service 和 MCP 客户端配置。
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(LangChain4jProperties.class)
@ConditionalOnProperty(prefix = "langchain4j", name = "enabled", havingValue = "true")
public class LangChain4jConfiguration {

    @Bean
    public ChatLanguageModel chatLanguageModel(LangChain4jProperties properties) {
        return QwenChatModel.builder()
                .apiKey(properties.getApiKey())
                .modelName(properties.getModelName())
                .build();
    }

    @Bean
    public StreamingChatLanguageModel streamingChatLanguageModel(LangChain4jProperties properties) {
        return QwenStreamingChatModel.builder()
                .apiKey(properties.getApiKey())
                .modelName(properties.getModelName())
                .build();
    }

    @Bean(destroyMethod = "close")
    @ConditionalOnProperty(prefix = "langchain4j.mcp", name = "enabled", havingValue = "true")
    public McpClient imageSearchMcpClient(LangChain4jProperties properties) {
        HttpMcpTransport transport = new HttpMcpTransport.Builder()
                .sseUrl(properties.getMcp().getImageSseUrl())
                .timeout(Duration.ofSeconds(30))
                .build();
        return new DefaultMcpClient.Builder()
                .clientName("guo-ai-agent")
                .clientVersion("0.0.1")
                .transport(transport)
                .build();
    }

    @Bean
    @ConditionalOnProperty(prefix = "langchain4j.mcp", name = "enabled", havingValue = "true")
    public ToolProvider mcpToolProvider(McpClient imageSearchMcpClient) {
        return McpToolProvider.builder().mcpClients(imageSearchMcpClient).build();
    }

    @Bean
    public LoveAssistant loveAssistant(ChatLanguageModel chatLanguageModel,
                                       ContentRetriever loveContentRetriever,
                                       LangChain4jProperties properties) {
        return AiServices.builder(LoveAssistant.class)
                .chatLanguageModel(chatLanguageModel)
                .chatMemoryProvider(id -> MessageWindowChatMemory.builder()
                        .id(id)
                        .maxMessages(properties.getMemoryMessages())
                        .build())
                .contentRetriever(loveContentRetriever)
                .build();
    }

    @Bean
    public StreamingLoveAssistant streamingLoveAssistant(StreamingChatLanguageModel streamingChatLanguageModel,
                                                         ContentRetriever loveContentRetriever,
                                                         LangChain4jProperties properties) {
        return AiServices.builder(StreamingLoveAssistant.class)
                .streamingChatLanguageModel(streamingChatLanguageModel)
                .chatMemoryProvider(id -> MessageWindowChatMemory.builder()
                        .id(id)
                        .maxMessages(properties.getMemoryMessages())
                        .build())
                .contentRetriever(loveContentRetriever)
                .build();
    }

    @Bean
    public ManusAssistant manusAssistant(StreamingChatLanguageModel streamingChatLanguageModel,
                                         LocalTools localTools,
                                         ObjectProvider<ToolProvider> toolProvider) {
        AiServices<ManusAssistant> builder = AiServices.builder(ManusAssistant.class)
                .streamingChatLanguageModel(streamingChatLanguageModel)
                .tools(localTools.tools());
        ToolProvider mcpTools = toolProvider.getIfAvailable();
        if (mcpTools != null) {
            builder.toolProvider(mcpTools);
        }
        return builder.build();
    }
}
