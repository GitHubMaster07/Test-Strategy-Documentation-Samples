# CI/CD Pipeline & Quality Gates

This document defines the CI/CD pipeline architecture, execution flow, quality gates, parallelization strategy, artifact management, and failure handling model for automated testing.

The goal is to ensure deterministic, fast, scalable, and audit‑ready test execution across all environments.

---

## 1. CI/CD Overview

### 1.1 Purpose
- Automate build, test, and deployment workflows.
- Provide fast feedback to developers.
- Enforce quality gates before merging or releasing.
- Ensure consistent, reproducible test execution.
- Generate audit‑ready evidence for compliance.

### 1.2 Supported CI/CD Platforms
- GitHub Actions
- Jenkins (optional)
- Local execution via Maven

### 1.3 High-Level Pipeline Diagram
```
+------------------+
|   Pull Request   |
+--------+---------+
|
v
+------------------+
|   Static Checks  |
| Lint, SAST, etc. |
+--------+---------+
|
v
+------------------+
|     Unit Tests   |
+--------+---------+
|
v
+------------------------------+
|  API + Contract + Schema     |
|        Validation            |
+--------------+---------------+
|
v
+------------------------------+
|     Integration Tests        |
| (API → DB, WireMock, Events) |
+--------------+---------------+
|
v
+------------------------------+
|         UI Smoke Tests       |
+--------------+---------------+
|
v
+------------------------------+
|     Full Regression (Nightly)|
+--------------+---------------+
|
v
+------------------------------+
|      Reporting & Artifacts   |
+------------------------------+
```

---

## 2. Pipeline Stages & Execution Order

### 2.1 Stage 1 — Static Analysis
- Linting (Checkstyle, SpotBugs)
- SAST (GitHub CodeQL)
- Dependency vulnerability scan

### 2.2 Stage 2 — Unit Tests
- Fast execution (< 1 min)
- Must pass 100%

### 2.3 Stage 3 — API Tests
- RestAssured tests
- Schema validation
- Authentication/authorization checks

### 2.4 Stage 4 — Contract Tests
- Pact consumer tests
- Publish contracts to Pact Broker
- Provider verification (separate pipeline)

### 2.5 Stage 5 — Integration Tests
- API → DB validation
- WireMock virtualization
- Event emission validation (Kafka/SQS)

### 2.6 Stage 6 — UI Smoke Tests
- Critical flows only
- Parallel execution

### 2.7 Stage 7 — Full Regression (Nightly)
- UI + API + DB + Events
- Parallel matrix execution
- Allure reporting

### 2.8 Stage 8 — Reporting & Artifacts
- Allure report
- Logs
- Screenshots
- Payloads
- Videos (optional)

---

## 3. Parallelization Strategy

### 3.1 TestNG Parallel Execution
- Parallel by classes
- Parallel by methods
- ThreadLocal isolation for:
  - WebDriver
  - API clients
  - DB connections

### 3.2 CI/CD Matrix Builds
- Browser matrix (Chrome, Firefox)
- Environment matrix (QA, Staging)
- Sharded test suites (split by tags)

### 3.3 Distributed Execution
- Selenium Grid / Selenoid
- Parallel API tests (stateless)
- Parallel event tests (isolated topics)

### 3.4 Performance Goals
- Smoke suite: < 10 minutes
- Full regression: < 30 minutes
- Contract tests: < 2 minutes

---

## 4. Quality Gates & Thresholds

### 4.1 Pre-Merge Quality Gates
| Gate | Requirement |
|------|-------------|
| Unit Tests | 100% pass |
| API Tests | 100% pass |
| Contract Tests | 100% pass |
| Static Analysis | No critical issues |
| Linting | No violations |
| Code Coverage | ≥ 80% |
| Secrets Scan | No exposed secrets |

### 4.2 Pre-Release Quality Gates
| Gate | Requirement |
|------|-------------|
| Regression Suite | ≥ 95% pass |
| Flaky Tests | ≤ 1% |
| Performance Baseline | Within SLA |
| Security Scan | No critical vulnerabilities |
| Contract Verification | All providers verified |
| Schema Validation | No drift detected |

### 4.3 Production Deployment Gates
- All CI pipelines green
- No open critical defects
- All evidence archived
- Rollback plan validated

---

## 5. Reporting & Artifacts

### 5.1 Allure Reporting
- Steps
- Attachments
- Screenshots
- API payloads
- Event payloads
- History trends
- Categories (flaky, product bugs, infra issues)

### 5.2 CI/CD Artifacts
- Allure results
- Logs (UI, API, DB, events)
- Screenshots
- Videos (optional)
- Pact contracts
- JSON schemas
- WireMock stubs

### 5.3 Artifact Retention Policy
- PR runs: 7 days
- Nightly runs: 30 days
- Release runs: 12 months (audit requirement)

---

## 6. Failure Handling & Reruns

### 6.1 Automatic Reruns
- RetryAnalyzer for known flaky categories
- Max retries: 1
- Only for:
  - UI synchronization issues
  - Network timeouts
  - Transient environment failures

### 6.2 Failure Categorization
- Product defect
- Test defect
- Environment issue
- Flaky test

### 6.3 Failure Triage Workflow
1. Analyze logs, screenshots, payloads  
2. Categorize failure  
3. Assign owner (QA, Dev, DevOps)  
4. Create defect (if applicable)  
5. Add evidence to Allure  

### 6.4 CI/CD Failure Rules
- Pipeline fails immediately on:
  - Contract test failure
  - Schema drift
  - Security vulnerability
  - Secrets exposure

### 6.5 Notifications
- Slack / Teams alerts
- Email summary (optional)
- GitHub PR status checks


