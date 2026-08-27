# LangChain4j 与 RAG 技术实现

## 1. AiServices

`LoveAssistant`、`StreamingLoveAssistant` 和 `ManusAssistant` 是普通 Java 接口。LangChain4j 的 `AiServices` 在运行时创建代理实现，负责把方法参数转换为模型消息，并把模型响应转换为 Java 返回值。

核心能力通过构建器组合：

- `chatLanguageModel`：同步模型
- `streamingChatLanguageModel`：流式模型
- `chatMemoryProvider`：按 `chatId` 隔离多轮上下文
- `contentRetriever`：把 RAG 检索结果加入提示词
- `tools`：注册带 `dev.langchain4j.agent.tool.Tool` 注解的本地方法
- `toolProvider`：动态提供 MCP 工具

业务接口使用 `@SystemMessage`、`@UserMessage` 和 `@MemoryId` 描述对话契约，Controller 不直接拼装底层模型请求。

## 2. 模型与流式响应

项目通过 `QwenChatModel` 和 `QwenStreamingChatModel` 使用 DashScope。模型名称由 `DASHSCOPE_MODEL_NAME` 控制，默认值为 `deepseek-v4-flash-0731`。

流式 AI Service 返回 `TokenStream`。Controller 注册三个回调：

- `onPartialResponse`：每收到一段文本就发送一个 SSE event
- `onCompleteResponse`：结束 SSE 连接
- `onError`：把异常交给 `SseEmitter`

这种实现保留了浏览器 `EventSource` 所需的 SSE 协议，同时不依赖 Reactor 或 Spring AI。

## 3. 对话记忆

`MessageWindowChatMemory` 按 `@MemoryId` 参数创建独立窗口。当前窗口默认保留 20 条消息，可通过 `langchain4j.memory-messages` 调整。

窗口记忆会保留系统消息，并在超出容量后淘汰较早的用户、助手和工具消息。同步与流式 AI Service 各自维护会话容器，调用方应避免用同一 `chatId` 并发写入同一个服务实例。

## 4. RAG 实现

本项目的 RAG 启动链路如下：

```text
resources/document/*.md
        ↓ FileSystemDocumentLoader
Document
        ↓ DocumentSplitters.recursive(500, 80)
TextSegment
        ↓ AllMiniLmL6V2EmbeddingModel
Embedding
        ↓
InMemoryEmbeddingStore<TextSegment>
```

请求链路如下：

```text
用户问题
  ↓ 向量化
相似度检索（最多 5 条，最低分 0.55）
  ↓
相关 TextSegment 注入模型上下文
  ↓
模型基于问题、会话记忆和知识片段生成回答
```

本地 AllMiniLM 模型使文档入库不依赖远程 Embedding API。当前向量库位于内存中，应用重启后会从 Markdown 文档重新构建。

## 5. 工具调用

本地工具方法使用 LangChain4j 注解：

```java
@Tool("Write content to a file")
public String writeFile(
        @P("Name of the file to write") String fileName,
        @P("Content to write") String content) {
    // 执行业务逻辑
}
```

`No @Tool annotated methods found` 通常是把 Spring AI 的 `@Tool` 对象交给 LangChain4j 扫描，或反过来造成的。两个框架的注解名称相同但类型不同，不能混用。本分支所有主应用工具统一使用 `dev.langchain4j.agent.tool.Tool`。

## 6. MCP 实现

主应用使用 `DefaultMcpClient` 建立 SSE 连接，再由 `McpToolProvider` 把远程工具转换为 LangChain4j 可调用工具。设置以下变量即可启用：

```text
MCP_ENABLED=true
IMAGE_MCP_SSE_URL=http://localhost:8127/sse
```

图片服务使用官方 MCP Java SDK 注册 `searchImage` 工具。工具定义包含名称、描述和 JSON Schema；handler 读取 `query` 参数并调用 Pexels API。服务支持：

- SSE：`WebMvcSseServerTransportProvider`
- stdio：`StdioServerTransportProvider`

MCP 服务端不使用 LangChain4j，因为 LangChain4j 该版本提供的是 MCP 客户端；服务端采用协议官方 SDK 更直接。

## 7. 边界

`Langchain4j` 分支中：

- 模型调用、记忆、RAG、工具调用、智能体和 MCP 客户端均由 LangChain4j 实现
- 图片 MCP 服务端由官方 MCP Java SDK 实现
- Spring Boot 仅用于配置、依赖注入、HTTP/SSE 和生命周期管理
- 不包含 Spring AI 或 Spring AI Alibaba 依赖
