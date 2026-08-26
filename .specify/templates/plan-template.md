# 实施计划：[FEATURE]

**分支**：[`###-feature-name`] | **日期**：[DATE] | **规格**：[链接]

**输入**：来自 `/specs/[###-feature-name]/spec.md` 的特性规格

**说明**：本模板由 `/speckit-plan` 命令填写。执行流程见
`.specify/templates/plan-template.md`。

## 摘要

[从特性规格提取主要需求，并结合研究结果说明技术方案]

## 技术上下文

<!--
  必须将本节替换为当前特性的真实技术细节。
  下列字段用于引导分析，不要求机械照抄。
-->

**语言/版本**：[Python 3.11、React 19 + TypeScript，或 NEEDS CLARIFICATION]

**主要依赖**：[FastAPI、Pydantic v2、SQLAlchemy 2、LangGraph、React 19，或 NEEDS CLARIFICATION]

**存储**：[PostgreSQL/pgvector、Redis、S3 兼容对象存储，或 N/A 并说明理由]

**测试**：[Pytest、契约/集成测试、Vitest、Playwright、AI 评测，或 NEEDS CLARIFICATION]

**目标平台**：[Linux 容器、最新稳定版 Chrome/Edge、390 像素移动端，或 NEEDS CLARIFICATION]

**项目类型**：[Web 服务、前端应用、MCP 服务、命令行工具，或 NEEDS CLARIFICATION]

**性能目标**：[领域相关目标，例如 SSE 首事件 P95、并发流数量，或 NEEDS CLARIFICATION]

**约束**：[领域相关约束，例如超时、内存、离线能力，或 NEEDS CLARIFICATION]

**规模/范围**：[用户数、数据量、页面数、接口数，或 NEEDS CLARIFICATION]

**API/流式影响**：[OpenAPI 接口、SSE 事件/Schema/版本和兼容性影响，或 N/A]

**安全/隐私影响**：[身份、授权、CSRF/CORS、工具权限、SSRF/路径控制、保留期限，或 N/A]

**可观测性**：[request_id/run_id、日志、指标、追踪、告警、Token/成本记录]

**AI 评测**：[数据集、基线、模型/提示词/Embedding 版本、质量和成本门槛，或 N/A]

## 宪章检查

*门禁：进入第 0 阶段研究前必须通过；第 1 阶段设计完成后必须重新检查。*

- [ ] 本特性是可独立测试的纵向切片，并有可量化验收标准。
- [ ] API 变更有类型化 Schema、OpenAPI 更新、稳定错误码和契约测试。
- [ ] 流式变更遵循 SSE 1.0：`meta`、递增 `seq`、关联 ID、心跳和唯一终态事件。
- [ ] 安全控制位于模型之外；已记录身份、数据所有权、CSRF/CORS、密钥、工具和网络边界。
- [ ] 已规划适用的单元、契约、集成、安全和浏览器测试；默认不调用真实外部服务。
- [ ] AI 行为变更定义了版本化评测集、基线、人工复核样本、延迟、Token、成本和质量门槛。
- [ ] 已设计幂等、超时、有限重试、取消、终态和断流恢复。
- [ ] 已明确脱敏结构化日志、关联 ID、健康检查、指标和必要告警。
- [ ] 设计保持模块化单体；若不保持，必须在“复杂度跟踪”中说明理由。
- [ ] 已处理适用的数据迁移、保留/删除、备份、回滚和第三方授权问题。

## 项目结构

### 本特性的文档

```text
specs/[###-feature]/
├── spec.md              # 本特性规格
├── plan.md              # 本文件
├── research.md          # 第 0 阶段研究结果
├── data-model.md        # 第 1 阶段数据模型
├── quickstart.md        # 第 1 阶段本地验证步骤
├── contracts/           # 第 1 阶段接口和事件契约
└── tasks.md             # 第 2 阶段任务清单，由 /speckit-tasks 生成
```

### 源码结构

<!--
  必须删除未使用的选项，并替换为本项目实际路径。
  交付的计划中不得保留“选项 1/2/3”等示例标签。
-->

```text
backend/
├── app/
│   ├── api/
│   ├── conversations/
│   ├── agents/
│   ├── tools/
│   ├── rag/
│   ├── providers/
│   ├── persistence/
│   └── observability/
├── migrations/
└── tests/
    ├── contract/
    ├── integration/
    ├── security/
    └── unit/

frontend/
├── src/
│   ├── components/
│   ├── pages/
│   ├── features/
│   └── services/
├── tests/
└── e2e/

mcp/
└── [服务名称]/
```

**结构决策**：[说明选定结构，并引用上面的真实目录]

## 复杂度跟踪

> 仅当宪章检查存在需要批准的例外时填写。

| 违反的原则 | 必要原因 | 拒绝更简单方案的理由 | 责任人/失效条件 |
|---|---|---|---|
| [例如：新增独立服务] | [具体需求或风险] | [为什么单体或现有模块不能满足] | [负责人/日期] |
