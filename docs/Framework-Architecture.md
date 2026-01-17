# Framework Architecture

## 1. Architecture Overview
- Purpose of the framework
- Supported testing domains (UI, API, DB, Events, Contracts)
- High-level architecture diagram
- Key design goals (scalability, maintainability, parallelism, determinism)

## 2. Core Design Principles
- Single Responsibility Principle (SRP)
- Separation of Concerns (SoC)
- No business logic in Step Definitions
- Thread safety (ThreadLocal drivers/clients/DB)
- Environment-agnostic execution
- Deterministic test behavior

## 3. Project Structure
- Full folder tree (src/main/java, src/test/java, resources)
- Explanation of each folder
- Mapping of layers → directories
- Naming conventions

## 4. Configuration & Environment Management
- config.properties
- Environment profiles (local, QA, staging, CI)
- Maven profiles
- Secrets management (GitHub Secrets / Jenkins Credentials)
- Runtime overrides (system properties)

## 5. UI Architecture (Selenium + POM)
- Page Object Model structure
- BasePage design
- DriverFactory (ThreadLocal)
- WebDriverManager integration
- Wait strategy
- Reusable UI utilities
- Example Page Object

## 6. API Architecture (RestAssured)
- BaseApi client
- Request specification builder
- Environment-aware base URLs
- Payload builders
- JSON schema validation
- Response validation utilities
- Example API client

## 7. Database Architecture (JDBC)
- ThreadLocal DB connections
- Parameterized queries
- Query execution utilities
- DB validators
- Example DB validation

## 8. Event Testing Architecture (Kafka/SQS)
- Producer/consumer abstraction
- Schema registry validation
- DLQ validation
- Offset management
- Example event validator

## 9. Contract Testing Architecture (Pact)
- Consumer-driven contract flow
- Pact Broker integration
- Provider verification
- Versioning & backward compatibility rules
- Example contract

## 10. Service Virtualization (WireMock)
- Stub server setup
- Dynamic stubs
- Fault injection
- Example WireMock stub

## 11. Test Data Layer
- Data builders
- Factories
- Synthetic data generation
- TDM integration (if applicable)

## 12. Observability Hooks
- Logging strategy (SLF4J + Logback)
- Request/response logging
- Screenshot & artifact capture
- Metrics & traces (if applicable)

## 13. Parallel Execution Model
- TestNG parallelism
- ThreadLocal isolation
- Avoiding shared state
- CI/CD parallel matrix builds

## 14. Error Handling & Retry Strategy
- RetryAnalyzer
- Flakiness governance
- Exception wrapping
- Recovery actions

## 15. Reporting Architecture
- Allure integration
- Attachments (screenshots, logs, payloads)
- Cucumber HTML report
- CI/CD artifact storage

## 16. Extensibility Model
- Plugin architecture
- Adding new services/layers
- Adding new test types
- Reusable abstractions

## 17. Final Consolidated Folder Structure
- Full tree of the entire framework
- Mapping of all layers
- Explanation of each module


