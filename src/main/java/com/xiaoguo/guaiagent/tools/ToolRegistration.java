package com.xiaoguo.guaiagent.tools;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * 集中注册 LangChain4j 本地工具。
 */
@Configuration(proxyBeanMethods = false)
public class ToolRegistration {

    @Bean
    public LocalTools localTools(@Value("${search-api.api-key:}") String searchApiKey) {
        return new LocalTools(List.of(
                new FileOperationTool(),
                new WebSearchTool(searchApiKey),
                new WebScrapingTool(),
                new ResourceDownloadTool(),
                new TerminalOperationTool(),
                new PDFGenerationTool(),
                new TerminateTool()
        ));
    }

    public record LocalTools(List<Object> tools) {
    }
}
