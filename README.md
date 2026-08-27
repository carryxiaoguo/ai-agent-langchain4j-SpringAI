# Guo AI Agent

本仓库提供两套相互独立的 Java AI 实现：

| 分支 | 实现 |
| --- | --- |
| `SpringAI` | Spring AI 与 Spring AI Alibaba 版本 |
| `Langchain4j` | 纯 LangChain4j 主应用，图片 MCP 子模块使用官方 MCP Java SDK |

当前文档对应 `Langchain4j` 分支。该分支的主应用不依赖 Spring AI 或 Spring AI Alibaba。

## 功能

- 恋爱咨询：同步对话、多轮记忆、RAG、SSE 流式响应
- GuoManus：LangChain4j AiServices 与 Tool Calling 组成的任务型智能体
- 本地工具：文件读写、网页搜索、网页抓取、资源下载、终端命令、PDF 生成
- MCP 客户端：通过 LangChain4j MCP 接入图片搜索服务
- 图片 MCP 服务：支持 SSE 与 stdio 两种传输方式
- Vue 前端：保留原有 `/ai/love_app/chat/sse` 和 `/ai/manus/chat` 接口

## 技术栈

- Java 21
- Spring Boot 3.4（Web 容器与依赖注入）
- LangChain4j 1.0.0-beta2
- DashScope 模型适配，默认模型 `deepseek-v4-flash-0731`
- LangChain4j AiServices、ChatMemory、RAG、Tool Calling、MCP Client
- MCP Java SDK 0.10.0
- Vue 3、Vite

## 项目结构

```text
ai-agent/
├─ src/main/java/com/xiaoguo/guaiagent/
│  ├─ agent/          # GuoManus 智能体
│  ├─ app/            # 恋爱咨询 AI Service
│  ├─ config/         # 模型、AiServices、MCP 客户端配置
│  ├─ controller/     # HTTP 与 SSE 接口
│  ├─ rag/            # 文档加载、切分、向量化与检索
│  └─ tools/          # LangChain4j 本地工具
├─ src/main/resources/document/  # RAG 文档
├─ yu-image-search-mcp-server/   # 图片搜索 MCP 子模块
├─ yu-ai-agent-frontend/         # Vue 前端
└─ docs/                         # 架构与技术文档
```

## 环境要求

- JDK 21
- Maven 3.9+
- Node.js 18+（仅运行前端时需要）

## 配置

PowerShell：

```powershell
$env:LANGCHAIN4J_ENABLED = "true"
$env:DASHSCOPE_API_KEY = "你的 DashScope API Key"
$env:DASHSCOPE_MODEL_NAME = "deepseek-v4-flash-0731"
$env:SEARCH_API_KEY = "你的 SearchAPI Key"
```

没有设置 `LANGCHAIN4J_ENABLED=true` 时，应用仍可启动，但只提供健康检查，不创建模型与 AI 接口。

## 启动主应用

```powershell
mvn spring-boot:run
```

主应用地址：`http://localhost:8123/api`

- 健康检查：`GET /api/health/`
- 恋爱咨询 SSE：`GET /api/ai/love_app/chat/sse?message=你好&chatId=1`
- GuoManus SSE：`GET /api/ai/manus/chat?message=生成一份计划`
- 接口文档：`http://localhost:8123/api/doc.html`

## 启动图片 MCP 服务

先打包子模块：

```powershell
cd yu-image-search-mcp-server
$env:PEXELS_API_KEY = "你的 Pexels API Key"
mvn clean package
```

SSE 模式（默认）：

```powershell
java -jar target/guo-image-search-mcp-server-0.0.1-SNAPSHOT.jar
```

SSE 地址为 `http://localhost:8127/sse`。主应用接入时增加：

```powershell
$env:MCP_ENABLED = "true"
$env:IMAGE_MCP_SSE_URL = "http://localhost:8127/sse"
```

stdio 模式：

```powershell
java -Dspring.profiles.active=stdio -jar target/guo-image-search-mcp-server-0.0.1-SNAPSHOT.jar
```

stdio 客户端配置示例见 `src/main/resources/mcp-servers.json`，其中也保留了高德 `npx` MCP 配置。

## 构建与测试

```powershell
mvn test
mvn -f yu-image-search-mcp-server/pom.xml test
```

详细结构见 [架构图](docs/架构图.md)，LangChain4j 与 RAG 实现见 [技术实现](docs/LangChain4j与RAG技术实现.md)。
