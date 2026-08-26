# AI Agent

这是 AI Agent 的 Python 3.11 + FastAPI 后端、React 19 前端初始化脚手架。

后端源码位于根目录的 `app/`，前端源码位于 `ai-agent-frontend/`。
完整的目录说明和开发命令见 [开发说明](docs/开发说明.md)。

## 启动后端

```powershell
uv sync
uv run uvicorn app.main:app --reload --host 127.0.0.1 --port 8000
```

健康检查：`http://127.0.0.1:8000/api/v1/health`

## 验证后端

```powershell
uv run ruff check .
uv run mypy app
uv run python -m pytest
```

当前脚手架不包含聊天、RAG、智能体、工具、MCP 或数据库功能。
