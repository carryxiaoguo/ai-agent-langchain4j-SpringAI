# 任务：前后端初始化脚手架

**输入**：`specs/001-project-initialization/` 下的规格、计划和契约

## 阶段 1：项目准备

- [X] T001 在仓库根目录创建后端源码和测试目录，并创建 `ai-agent-frontend/` 前端目录
- [X] T002 [P] 在根目录 `pyproject.toml` 配置 Python 3.11、FastAPI、Uvicorn、Pytest、HTTPX 和 Ruff
- [X] T003 [P] 在 `ai-agent-frontend/package.json` 配置 React 19、TypeScript、Vite、Vitest 和 ESLint
- [X] T004 [P] 在根目录 `.env.example` 和 `ai-agent-frontend/.env.example` 添加示例配置
- [X] T005 [P] 在根目录 `.gitignore` 补充 Python、Node、环境文件和构建产物规则

## 阶段 2：后端基础能力

- [X] T006 在根目录 `app/core/config.py` 实现最小环境配置读取
- [X] T007 在根目录 `app/api/routes/health.py` 定义健康响应模型和健康路由
- [X] T008 在根目录 `app/main.py` 创建 FastAPI 应用、`/api/v1` 路由和 `X-Request-ID` 响应头
- [X] T009 在根目录 `tests/test_health.py` 编写健康接口和请求关联测试

## 阶段 3：用户故事 1：启动后端

- [X] T010 [US1] 在根目录 `README.md` 记录安装、启动、健康接口和测试命令
- [X] T011 [US1] 在根目录 `tests/test_health.py` 验证 OpenAPI 契约中的字段
- [X] T012 [US1] 执行 `uv sync`、Ruff、Mypy 和 Pytest，并修复失败项

## 阶段 4：用户故事 2：启动前端

- [X] T013 [P] [US2] 在 `ai-agent-frontend/src/api.ts` 实现从环境变量读取地址的健康检查请求
- [X] T014 [P] [US2] 在 `ai-agent-frontend/src/App.tsx` 实现工作台、连接状态和重试按钮
- [X] T015 [P] [US2] 在 `ai-agent-frontend/src/main.tsx` 和 `ai-agent-frontend/src/styles.css` 实现入口和响应式样式
- [X] T016 [P] [US2] 在 `ai-agent-frontend/src/App.test.tsx` 编写连接状态测试
- [X] T017 [US2] 在 `ai-agent-frontend/vite.config.ts` 配置开发代理和测试环境
- [X] T018 [US2] 在 `ai-agent-frontend/` 执行 ESLint、TypeScript、Vitest 和 Vite 构建，并修复失败项

## 阶段 5：用户故事 3：复现开发命令

- [X] T019 [US3] 在根目录 `docs/开发说明.md` 记录 Python 3.11、Node.js、安装、启动、测试和构建命令
- [X] T020 [US3] 在根目录 `README.md` 链接开发文档并说明两个项目目录职责
- [X] T021 [US3] 执行快速开始文档中的后端和前端命令，记录本机验证结果

## 最终验证

- [X] T022 验证 390 像素和 1440 像素视口无横向溢出或控件重叠
- [X] T023 扫描源码、日志样例和前端构建产物，确认没有有效密钥
- [X] T024 复查规格、计划、任务和实现范围，确认未混入数据库、AI、SSE、MCP 或业务功能

## 范围说明

本任务只交付可独立启动和验证的前后端初始化脚手架，不实现聊天、RAG、智能体、工具、MCP、数据库、缓存、对象存储或正式身份体系。
