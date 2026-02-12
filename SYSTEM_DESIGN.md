# 行业研究问卷与评分管理系统 (Role-Based Survey & Scoring System) - 系统设计说明书

## 1. 引言 (Introduction)

本系统旨在构建一个基于 Web 的行业研究问卷与评分管理平台，通过角色分流、标准化评分模型与可审计的数据结构，将定性的行业痛点转化为定量的可信数据。系统支持多角色（如供应商、制作方、场馆、策划方）参与，自动计算基础设施失败指数（IFI），并生成符合学术严谨性的统计分析报告。

## 2. 技术架构 (Technical Architecture)

### 2.1 技术栈
*   **前端**: HTML5, CSS3, JavaScript (Vanilla 或轻量级框架)
    *   负责封面页展示、知情同意流程、动态表单渲染、数据提交。
*   **后端**: Node.js (Express 或 Serverless Function)
    *   负责 API 接口处理、评分逻辑计算、数据持久化。
*   **数据库**: PostgreSQL
    *   关系型数据库，用于存储结构化数据、JSON 格式的原始答案及分析结果。
*   **ORM**: Prisma
    *   负责 Schema 定义、数据库迁移 (Migrations) 及类型安全的数据库访问。
*   **开发与部署**: Cursor (IDE), Docker (可选), CI/CD 流水线。

### 2.2 架构图
```mermaid
graph TD
    User[用户/受访者] -->|HTTPS| Frontend[前端应用 (Web)]
    Frontend -->|JSON API| Backend[后端服务 (Node.js API)]
    Backend -->|ORM| DB[(PostgreSQL 数据库)]
    
    subgraph "核心模块"
    Frontend -- 角色分流 --> QuestionEngine[问卷渲染引擎]
    Backend -- 数据处理 --> ScoringEngine[评分与权重计算引擎]
    Backend -- 数据存取 --> DataLayer[数据持久化层]
    end
    
    Analyst[研究员] -->|查询/导出| AnalysisModule[统计分析模块]
    AnalysisModule --> DB
```

## 3. 功能模块设计 (Functional Modules)

### 3.1 封面与准入模块 (Cover & Access)
*   **功能**: 展示研究目的（验证行业信任危机等），明确非商业性质，签署知情同意书。
*   **流程**: 访问页面 -> 阅读说明 -> 勾选 "Agree to Participate" -> 进入角色选择。

### 3.2 角色分流问卷模块 (Role-Based Survey Engine)
*   **功能**: 根据用户选择的角色加载对应的题库版本。
*   **角色类型**:
    *   Equipment Supplier / Rental (设备供应商)
    *   Production / Technical Integrator (制作/集成商)
    *   Venue / Site Operator (场馆/场地)
    *   Event Planner / Agency (策划/代理)
*   **逻辑**: 前端根据 `RoleID` 请求对应的 `Questions` 数据，动态渲染 15 道题目。

### 3.3 评分与计算核心 (Scoring Core)
*   **功能**: 接收原始答案，进行标准化打分与加权计算。
*   **计算逻辑**:
    1.  **逐题标准化**: 将选项映射为 0-100 分。
    2.  **维度聚合**: 计算 Trust, Rework, Safety, Interop 四个维度的加权分。
    3.  **IFI 指数计算**: `IFI = 0.45*Trust + 0.35*Rework + 0.20*Safety` (示例公式)。
    4.  **标签生成**: 根据阈值自动打标 (e.g., `HighRiskSignal`).

### 3.4 统计分析模块 (Analysis & Reporting)
*   **功能**: 生成研究报告所需的统计数据。
*   **输出内容**:
    *   样本分布统计 (Demographics)
    *   跨角色数据对比 (Role Comparison)
    *   IFI 置信区间计算 (Confidence Intervals)
    *   关键题贡献度分析 (Key Drivers Analysis)

## 4. 数据库设计 (Database Schema)

### 4.1 数据表清单

#### (1) Respondents (研究对象表)
记录每一次完整的问卷提交会话。

| 字段名 | 类型 | 必填 | 说明 |
| :--- | :--- | :--- | :--- |
| `id` | UUID | Y | 主键 |
| `role` | VARCHAR | Y | 角色 (Supplier, Production, Venue, Planner) |
| `submission_time` | TIMESTAMP | Y | 提交时间 |
| `consent_agreed` | BOOLEAN | Y | 是否签署同意书 |
| `contact_email` | VARCHAR | N | 可选联系方式 (加密存储) |
| `meta_info` | JSONB | N | 浏览器指纹、来源等元数据 |

#### (2) Questions (题库表)
存储所有版本的题目定义及评分规则。

| 字段名 | 类型 | 必填 | 说明 |
| :--- | :--- | :--- | :--- |
| `id` | INT/UUID | Y | 主键 |
| `role_category` | VARCHAR | Y | 适用角色 |
| `version` | VARCHAR | Y | 题库版本号 (e.g., v1.0) |
| `content` | TEXT | Y | 题目文本 |
| `dimension` | VARCHAR | Y | 归属维度 (Trust, Rework, Safety, Interop) |
| `weight` | DECIMAL | Y | 题目在维度内的权重 (0.0 - 1.0) |
| `question_type` | VARCHAR | Y | 题型 (Likert, SingleChoice, etc.) |
| `options_mapping` | JSONB | Y | 选项与分数映射规则 (e.g., {"A": 0, "B": 50, "C": 100}) |

#### (3) Responses (逐题答案表)
记录每个 Respondent 对每个 Question 的具体回答及得分。

| 字段名 | 类型 | 必填 | 说明 |
| :--- | :--- | :--- | :--- |
| `id` | UUID | Y | 主键 |
| `respondent_id` | UUID | Y | 外键 -> Respondents.id |
| `question_id` | INT/UUID | Y | 外键 -> Questions.id |
| `raw_answer` | JSONB | Y | 用户的原始选择 |
| `standardized_score`| DECIMAL | Y | 标准化得分 (0-100) |
| `applied_weight` | DECIMAL | Y | 计算时使用的权重快照 |
| `applied_dimension` | VARCHAR | Y | 计算时归属的维度快照 |

#### (4) Scores (评分结果表)
存储聚合后的评分结果。

| 字段名 | 类型 | 必填 | 说明 |
| :--- | :--- | :--- | :--- |
| `id` | UUID | Y | 主键 |
| `respondent_id` | UUID | Y | 外键 -> Respondents.id |
| `trust_score` | DECIMAL | Y | 信任维度分 |
| `rework_score` | DECIMAL | Y | 返工维度分 |
| `safety_score` | DECIMAL | Y | 安全维度分 |
| `interop_score` | DECIMAL | Y | 互操作性维度分 |
| `ifi_index` | DECIMAL | Y | 基础设施失败指数 (综合分) |
| `flags` | TEXT[] | N | 自动标签 (SystemicTrustFailure, HighRiskSignal) |

#### (5) AnalysisRuns (分析运行记录)
用于审计分析结果的可复现性。

| 字段名 | 类型 | 必填 | 说明 |
| :--- | :--- | :--- | :--- |
| `id` | UUID | Y | 主键 |
| `run_timestamp` | TIMESTAMP | Y | 分析执行时间 |
| `dataset_version` | VARCHAR | Y | 数据集截止时间点或版本 |
| `output_summary` | JSONB | Y | 关键指标摘要 (Top 5, Means, CI) |

## 5. 接口设计 (API Design)

*   `GET /api/config`: 获取问卷配置（含角色列表）。
*   `GET /api/questions/:role`: 根据角色获取题目列表。
*   `POST /api/submit`: 提交问卷答案。
    *   **Input**: `{ role, answers: [{ questionId, value }] }`
    *   **Process**: 验证 -> 存入 Responses -> 计算 Scores -> 存入 Scores -> 返回结果。
*   `GET /api/admin/analysis`: (受保护) 获取统计分析报告。

## 6. 安全与合规 (Security & Compliance)

*   **数据隔离**: 个人身份信息 (PII) 如 Email 需与评分数据逻辑分离或加密存储。
*   **最小权限**: 数据库连接仅授予应用所需的最小读写权限。
*   **匿名化**: 默认导出的分析报告不包含 Respondent ID 与联系方式的关联。
*   **审计**: 关键数据变更（如题库修改）应有日志记录，Question 表设计支持版本控制。
