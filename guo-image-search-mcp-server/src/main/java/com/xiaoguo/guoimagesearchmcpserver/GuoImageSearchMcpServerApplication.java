package com.xiaoguo.guoimagesearchmcpserver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiaoguo.guimagesearchmcpserver.tools.ImageSearchTool;
import io.modelcontextprotocol.server.McpServer;
import io.modelcontextprotocol.server.McpSyncServer;
import io.modelcontextprotocol.server.transport.StdioServerTransportProvider;
import io.modelcontextprotocol.server.transport.WebMvcSseServerTransportProvider;
import io.modelcontextprotocol.spec.McpSchema;
import io.modelcontextprotocol.spec.McpServerTransportProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

/**
 * 图片搜索 MCP 服务，支持 SSE 和 stdio 两种传输方式。
 */
@SpringBootApplication
public class GuoImageSearchMcpServerApplication {

    private static final String INPUT_SCHEMA = """
            {
              "type": "object",
              "properties": {
                "query": {"type": "string", "description": "图片搜索关键词"}
              },
              "required": ["query"],
              "additionalProperties": false
            }
            """;

    public static void main(String[] args) {
        SpringApplication.run(GuoImageSearchMcpServerApplication.class, args);
    }

    @Bean
    @Profile("sse")
    public WebMvcSseServerTransportProvider sseTransportProvider(ObjectMapper objectMapper) {
        return new WebMvcSseServerTransportProvider(objectMapper, "/mcp/message", "/sse");
    }

    @Bean
    @Profile("sse")
    public RouterFunction<ServerResponse> mcpRouter(WebMvcSseServerTransportProvider transportProvider) {
        return transportProvider.getRouterFunction();
    }

    @Bean
    @Profile("stdio")
    public StdioServerTransportProvider stdioTransportProvider(ObjectMapper objectMapper) {
        return new StdioServerTransportProvider(objectMapper);
    }

    @Bean(destroyMethod = "close")
    public McpSyncServer imageSearchMcpServer(McpServerTransportProvider transportProvider,
                                              ImageSearchTool imageSearchTool) {
        McpSchema.Tool tool = new McpSchema.Tool(
                "searchImage",
                "从 Pexels 搜索图片并返回中等尺寸图片 URL",
                INPUT_SCHEMA
        );
        return McpServer.sync(transportProvider)
                .serverInfo("guo-image-search-mcp-server", "0.0.1")
                .capabilities(McpSchema.ServerCapabilities.builder().tools(false).build())
                .tool(tool, (exchange, arguments) -> {
                    try {
                        String query = String.valueOf(arguments.get("query"));
                        return new McpSchema.CallToolResult(imageSearchTool.searchImage(query), false);
                    } catch (Exception e) {
                        return new McpSchema.CallToolResult("图片搜索失败：" + e.getMessage(), true);
                    }
                })
                .build();
    }
}
