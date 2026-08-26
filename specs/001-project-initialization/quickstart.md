# 快速开始：前后端初始化脚手架

## 后端

```powershell
uv sync
uv run uvicorn app.main:app --reload --host 127.0.0.1 --port 8000
```

访问 `http://127.0.0.1:8000/api/v1/health`，预期返回 HTTP 200。

## 前端

另开终端：

```powershell
Set-Location ai-agent-frontend
npm.cmd install
npm.cmd run dev -- --host 127.0.0.1 --port 3000
```

打开 `http://127.0.0.1:3000`，页面会显示后端连接状态。

## 验证

```powershell
uv run ruff check .
uv run python -m pytest

Set-Location ai-agent-frontend
npm.cmd run lint
npm.cmd run typecheck
npm.cmd run test -- --run
npm.cmd run build
```

本特性不要求 Docker，不启动数据库、缓存或对象存储。
