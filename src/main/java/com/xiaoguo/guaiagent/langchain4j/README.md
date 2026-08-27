# LangChain4j 实现包

本目录中的实现与项目原有的 Spring AI 实现相互隔离。默认不会创建 LangChain4j Bean，也不会影响现有业务。

## 启用模型直调

启动应用前配置以下环境变量，或配置对应的 Spring 属性：

```text
LANGCHAIN4J_ENABLED=true
LANGCHAIN4J_API_KEY=<DashScope API Key>
LANGCHAIN4J_MODEL_NAME=qwen-plus
```

也可以在 `application.yml` 中配置：

```yaml
langchain4j:
  enabled: true
  api-key: ${LANGCHAIN4J_API_KEY}
  model-name: qwen-plus
```

启用后，可以通过下面的接口测试：

```text
GET /api/ai/langchain4j/chat?message=你好
```

`langchain4j.enabled=false`（默认值）时，该接口不存在。

## 实现路线

1. `app/LangChain4jLoveApp`：使用 `ChatLanguageModel` 完成一次模型调用。
2. 增加 `service`：使用 `AiServices`、系统提示词和结构化输出。
3. 增加 `memory`：使用 `ChatMemory` 和按会话 ID 管理记忆。
4. 增加 `tools`：使用 `@Tool`、工具描述、参数 Schema 和工具执行器。
5. 增加 `rag`：实现文档加载、分块、Embedding、向量存储和检索增强。
6. 增加 `agent`：在理解消息和工具调用后实现 ReAct 循环。

## 与 Spring AI 的边界

本包不直接注入 Spring AI 的 `ChatModel`、`ToolCallback` 或 `VectorStore`。需要对比两套框架时，在 Controller 层增加适配接口即可。原有 Spring AI 代码、接口和前端保持不变。
