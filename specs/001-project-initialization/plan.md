# 实施计划：前后端初始化脚手架

**分支**：`001-project-initialization` | **日期**：2026-08-26 | **规格**：[spec.md](spec.md)

## 摘要

在仓库根目录建立 Python 3.11 + FastAPI 后端和 `ai-agent-frontend/` React 19 + TypeScript
前端。后端只提供配置、请求 ID、健康接口和最小测试；前端只提供应用工作区、连接检查、
重试和构建配置；根目录提供中文开发文档。本特性不接入数据库、缓存、模型、SSE、MCP 或业务功能。

## 技术上下文

**语言/版本**：Python 3.11；React 19 + TypeScript

**主要依赖**：FastAPI、Pydantic Settings、Uvicorn；React、Vite、Vitest、Testing Library

**存储**：N/A；本特性不保存业务数据

**测试**：Pytest、HTTPX、Ruff；Vitest、TypeScript、ESLint、Vite build

**目标平台**：Windows 本地开发；Chrome/Edge；390 像素移动端；目标运行环境另行确认

**项目类型**：前后端分离 Web 应用脚手架

**性能目标**：健康接口 P95 小于 1 秒；前端连接检查超时 3 秒

**约束**：后端源码位于仓库根目录，前端目录固定为 `ai-agent-frontend/`；Python 固定 3.11.x

**规模/范围**：1 个健康接口、1 个前端页面、无业务实体

**API/流式影响**：新增 `/api/v1/health`；不实现 SSE

**安全/隐私影响**：仅使用 `.env.example`；禁止真实密钥进入源码和构建产物

**可观测性**：健康接口生成或透传 `X-Request-ID`

**AI 评测**：N/A，本特性不改变 AI 行为

## 宪章检查

- [x] 可独立启动、测试和演示。
- [x] 健康接口具有稳定 JSON 契约和测试计划。
- [x] 本特性不引入 SSE，未绕过流式规则。
- [x] 配置和错误边界已限定，不包含真实密钥。
- [x] 后端和前端均有最小自动化测试。
- [x] 不改变 AI 行为，AI 评测不适用。
- [x] 前端连接检查有超时和重试。
- [x] 健康接口具有请求关联标识。
- [x] 保持两个简单项目，不拆分额外服务。

## 项目结构

```text
.
├── app/
│   ├── api/routes/health.py
│   ├── core/config.py
│   └── main.py
├── tests/test_health.py
├── .env.example
├── pyproject.toml
└── uv.lock

ai-agent-frontend/
├── src/App.tsx
├── src/api.ts
├── src/main.tsx
├── src/styles.css
├── src/App.test.tsx
├── .env.example
├── package.json
├── tsconfig.json
└── vite.config.ts

docs/开发说明.md
```

**结构决策**：仓库根目录就是后端项目目录，`app/` 是合法 Python 包；
`ai-agent-frontend/` 是前端项目目录。没有数据库、Compose 或 MCP 子项目。

## 复杂度跟踪

无。所有实现均为初始化脚手架，不引入额外架构复杂度。
