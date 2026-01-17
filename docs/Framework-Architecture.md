# Framework Architecture

## 1. Architecture Overview

The automation framework provides a unified, scalable, and maintainable architecture for validating a modern distributed system across UI, API, database, event-driven, and contract layers.

### Key Design Goals
- High maintainability through modular design.
- Deterministic, reproducible, environment‑agnostic execution.
- Full compatibility with CI/CD pipelines.
- Thread‑safe parallel execution.
- Clear separation of concerns across layers.
- Extensibility for new services, domains, and test types.

### Supported Testing Domains
- UI (Selenium WebDriver)
- API (RestAssured)
- Database (JDBC)
- Event-driven (Kafka/SQS)
- Contract testing (Pact)
- Service virtualization (WireMock)

---

## 2. Core Design Principles

### 2.1 Single Responsibility Principle (SRP)
Each class has one purpose:
- Page classes → UI interactions  
- API clients → HTTP operations  
- DB utilities → SQL execution  
- Step definitions → orchestration only  

### 2.2 Separation of Concerns (SoC)
- UI, API, DB, events, and contracts are isolated into independent layers.
- No cross-layer leakage (e.g., no SQL in UI tests).

### 2.3 No Business Logic in Step Definitions
StepDefs only orchestrate:
- Page actions  
- API calls  
- DB validations  
- Event checks  

### 2.4 Thread Safety
All parallel execution uses:
- ThreadLocal WebDriver  
- ThreadLocal API clients  
- ThreadLocal DB connections  

### 2.5 Environment-Agnostic Execution
All environment variables come from:
- config.properties  
- Maven profiles  
- CI/CD secrets  

### 2.6 Deterministic Test Behavior
- No randomness without seed  
- No shared state  
- No time-dependent tests  

---

## 3. Project Structure

```
java-selenium-bdd-framework/
├── src/
│   ├── main/java/
│   │   └── core/
│   │       ├── driver/              # DriverFactory, WebDriverManager, ThreadLocal driver
│   │       ├── config/              # ConfigReader, Environment loader
│   │       ├── utils/               # Common utilities (waits, logging, random data)
│   │       └── api/                 # API client base classes
│   ├── test/java/
│   │   ├── pages/                   # Page Object Model (POM) classes
│   │   ├── api/                     # RestAssured clients, endpoints, payload builders
│   │   ├── db/                      # JDBC connectors, queries, DB validators
│   │   ├── events/                  # Kafka/SQS consumers, event validators
│   │   ├── contracts/               # Pact consumer tests
│   │   ├── stepdefs/                # Cucumber Step Definitions
│   │   ├── hooks/                   # Before/After hooks (screenshots, cleanup)
│   │   └── runners/                 # TestNG runners (parallel, smoke, regression)
│   └── test/resources/
│       ├── features/                # Gherkin feature files
│       ├── schemas/                 # JSON schemas for API validation
│       ├── pact/                    # Pact contracts
│       ├── testdata/                # Environment-specific test data
│       └── config.properties         # Global configuration
├── pom.xml                           # Maven dependencies & plugins
├── .gitignore                       # Excludes target/, logs/, allure-results/
└── .gitattributes                   # Ensures Java language detection
```

---

## 4. Configuration & Environment Management

### 4.1 Configuration Sources
- `config.properties` (default values)
- Maven profiles (`-Plocal`, `-Pqa`, `-Pstaging`)
- System properties (`-Denv=qa`)
- CI/CD secrets (GitHub Actions, Jenkins)

### 4.2 Environment Variables
- Base URLs  
- Browser settings  
- Timeouts  
- API tokens (injected at runtime)  
- DB connection strings  

### 4.3 Secrets Management
- Never stored in repo  
- Injected via CI/CD  
- Access controlled  

---

## 5. UI Architecture (Selenium + POM)

### 5.1 Page Object Model
- Each page = one class  
- No assertions inside pages  
- Only UI interactions  

### 5.2 BasePage
- Click, type, getText, waits  
- Scroll, visibility checks  
- ThreadLocal WebDriver  

### 5.3 DriverFactory
- Creates driver per thread  
- Supports Chrome, Firefox, Edge  
- Supports Grid/Selenoid  

### 5.4 Example Page Object
(Already provided in your earlier content — included in final file)

---

## 6. API Architecture (RestAssured)

### 6.1 BaseApi Client
- Builds request specification  
- Injects base URL  
- Handles logging  
- Sets content type  

### 6.2 API Clients
- One class per service  
- No hardcoded URLs  
- Payload builders  
- Schema validation  

### 6.3 Example API Client
(Already provided — included in final file)

---

## 7. Database Architecture (JDBC)

### 7.1 ThreadLocal Connections
- One connection per thread  
- Auto-close after scenario  

### 7.2 Query Execution
- Parameterized queries  
- SQL injection safe  
- Reusable validators  

### 7.3 Example DB Validator
(Already provided)

---

## 8. Event Testing Architecture (Kafka/SQS)

### 8.1 Event Consumers
- Thread-safe consumer wrapper  
- Polling with timeout  
- Offset management  

### 8.2 Schema Validation
- Avro/JSON schema registry  
- Backward compatibility rules  

### 8.3 DLQ Validation
- Dead Letter Queue checks  
- Poison message detection  

### 8.4 Example Event Validator
(Already provided)

---

## 9. Contract Testing Architecture (Pact)

### 9.1 Consumer-Driven Contracts
- Pact consumer tests  
- Contract generation  

### 9.2 Pact Broker Integration
- Publish contracts  
- Retrieve provider versions  

### 9.3 Provider Verification
- CI pipeline verifies provider  
- Backward compatibility enforced  

### 9.4 Versioning Rules
- No breaking changes without version bump  

---

## 10. Service Virtualization (WireMock)

### 10.1 Stub Server
- Local WireMock instance  
- Dynamic stubs  
- Fault injection  

### 10.2 Use Cases
- Unstable dependencies  
- Negative scenarios  
- Performance isolation  

---

## 11. Test Data Layer

### 11.1 Data Builders
- Fluent builders for payloads  
- Reusable across tests  

### 11.2 Factories
- UserFactory  
- BookingFactory  
- EventFactory  

### 11.3 Synthetic Data
- Randomized but deterministic  
- Seed-based generation  

---

## 12. Observability Hooks

### 12.1 Logging
- SLF4J + Logback  
- Request/response logs  
- DB query logs  

### 12.2 Artifacts
- Screenshots  
- API payloads  
- Event payloads  

### 12.3 Metrics (optional)
- Test duration  
- Retry count  
- Failure categories  

---

## 13. Parallel Execution Model

### 13.1 TestNG Parallelism
- Parallel by classes  
- Parallel by methods  
- Parallel suites  

### 13.2 Thread Isolation
- ThreadLocal drivers  
- ThreadLocal API clients  
- ThreadLocal DB connections  

### 13.3 CI/CD Parallel Matrix
- Browser matrix  
- Environment matrix  
- Sharded test suites  

---

## 14. Error Handling & Retry Strategy

### 14.1 RetryAnalyzer
- Retries only for known flaky categories  
- Max retry = 1  

### 14.2 Exception Wrapping
- Custom exceptions for clarity  
- Root cause preservation  

### 14.3 Failure Triage
- Categorize failures  
- Auto-attach logs and screenshots  

---

## 15. Reporting Architecture

### 15.1 Allure Report
- Steps  
- Attachments  
- History  
- Categories  

### 15.2 Cucumber HTML
- Lightweight summary  

### 15.3 CI/CD Artifacts
- Logs  
- Screenshots  
- Videos (if enabled)  

---

## 16. Extensibility Model

### 16.1 Adding New Services
- Create new API client  
- Add schema  
- Add validators  

### 16.2 Adding New Test Types
- Add new layer folder  
- Add utilities  
- Add runners  

### 16.3 Plugin Architecture
- Reusable modules  
- Shared utilities  

---

## 17. Final Consolidated Folder Structure
```
java-selenium-bdd-framework/
├── src/
│   ├── main/java/
│   │   └── core/
│   │       ├── driver/              # DriverFactory, WebDriverManager, ThreadLocal driver
│   │       ├── config/              # ConfigReader, Environment loader
│   │       ├── utils/               # Common utilities (waits, logging, random data)
│   │       └── api/                 # API client base classes
│   ├── test/java/
│   │   ├── pages/                   # Page Object Model (POM) classes
│   │   ├── api/                     # RestAssured clients, endpoints, payload builders
│   │   ├── db/                      # JDBC connectors, queries, DB validators
│   │   ├── events/                  # Kafka/SQS consumers, event validators
│   │   ├── contracts/               # Pact consumer tests
│   │   ├── stepdefs/                # Cucumber Step Definitions
│   │   ├── hooks/                   # Before/After hooks (screenshots, cleanup)
│   │   └── runners/                 # TestNG runners (parallel, smoke, regression)
│   └── test/resources/
│       ├── features/                # Gherkin feature files
│       ├── schemas/                 # JSON schemas for API validation
│       ├── pact/                    # Pact contracts
│       ├── testdata/                # Environment-specific test data
│       └── config.properties         # Global configuration
├── pom.xml                           # Maven dependencies & plugins
├── .gitignore                       # Excludes target/, logs/, allure-results/
└── .gitattributes                   # Ensures Java language detection
```

