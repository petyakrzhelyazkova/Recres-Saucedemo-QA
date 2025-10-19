# Recres-SauseDemo-QA  
Automated API and UI Test Suites for Recres.in and Saucedemo

---

## 1. Table of Contents
1. [Overview](#2-overview)  
2. [What’s in this repo](#3-whats-in-this-repo)  
3. [Task Description](#32-task-description)  
4. [Prerequisites (Required Files)](#4-prerequisites-required-files)  
5. [Environment Requirements](#5-environment-requirements)  
6. [Environment Setup](#6-environment-setup)  
7. [Repository Structure](#7-repository-structure)  
8. [Bonus Task: Multi‑Resolution Support](#8-bonus-task-multi-resolution-support)  
9. [Run at Different Resolutions (Selenium)](#9-run-at-different-resolutions-selenium)

---

## 2. Overview
**Recres-SauseDemo-QA** combines both **API** and **Web UI** automation tasks, created as part of а QA Automation Challenge.

The repository contains:
- **REST API tests** for [Recres.in](https://reqres.in/) implemented using **Postman** and **REST Assured (Java)**.  
- **UI automation tests** for [Saucedemo](https://www.saucedemo.com/) implemented using **Selenium WebDriver** with **Java** and **TestNG**.  

These suites validate functional correctness, browser compatibility, and environment configurability while ensuring scalable execution with reporting and filtering options.

---

## 3. What’s in this repo
Concise setup and ready-to-run automation suites covering both API and UI layers.  
Each folder contains the full implementation, configuration, and run instructions for the given toolset.

### 3.1 Included Test Suites
| Folder | Description |
|--------|--------------|
| **Postman** | Manual and automated API tests for Recres.in. Contains the collection, environment files, and a Windows batch script for automatic execution and **Allure** report generation. |
| **REST Assured** | Java implementation of the same API scenarios as Postman, built with **Maven**, **TestNG**, and **Allure** reporting. Includes both manual CLI and Windows batch scripts for automated runs and report display. |
| **Selenium** | End‑to‑end UI automation for Saucedemo using **Selenium WebDriver**, **TestNG**, and **Allure**. Includes configuration for multiple browsers and resolutions, plus a ready‑to‑run Windows batch script for automatic execution and reporting. |

---

## 3.2 Task Description

### **REST API Task**
**Tools:** Postman / REST Assured  
**API Base:** https://reqres.in/api  

#### Scenario Steps:
1. List available users - `GET /api/users?page=1`  
   - Validate JSON response structure  
   - Extract single user details (`id`, `email`)  
   - *(Optional)* Extract and sort all users by first name  
2. Get extracted user details - `GET /api/users/{USER_ID}`  
   - Validate user exists  
3. Try to get non‑existing user - `GET /api/users/{USER_ID}`  
   - Validate error response  
4. Create a unique user - `POST /api/users`  
   - Assert JSON response fields  
5. Delete newly created user - `DELETE /api/users/{USER_ID}`  
   - Assert status and empty body  
6. Parameterize the base URL for flexible environments  

---

### **Web UI Task**
**Tools:** Selenium WebDriver / Java / TestNG  

#### Version 1 Scenarios
**Scenario 1**
1. Log in with the standard user  
2. Add the first and last product to the cart -> verify contents  
3. Remove the first item, add the previous‑to‑last -> verify contents  
4. Proceed to checkout and finish the order  
5. Verify the order is placed and the cart is empty  
6. Logout  

**Scenario 2**
1. Log in with the standard user  
2. Verify sorting “Price (high to low)” orders items correctly  
3. Logout  

#### Version 2 Enhancements
- Ability to **filter tests** for targeted execution  
- **Custom HTML report** (Allure)  
- **Environment configuration** (dev, testing, staging)  
- **Multi‑browser support:** Chrome & Firefox  
- **Bonus:** Responsive tests for multiple resolutions  

---

## 4. Prerequisites (Required Files)
Even with all tools installed, the following files/folders are required:
- `/Postman` - Postman collection + environment JSON + automated run script  
- `/REST Assured` - Java project with POM, config, test classes, and automated run script  
- `/Selenium` - Java project with Selenium POM structure, configuration, and batch file for automated run and report generation  

---

## 5. Environment Requirements

| Tool / Library | Purpose | Link |
|----------------|----------|------|
| **Java (JDK 17+)** | Required for Java test execution | https://adoptium.net/ |
| **Maven** | Build & dependency management | https://maven.apache.org/ |
| **TestNG** | Test execution framework | https://testng.org/ |
| **Allure** | Reporting framework for REST & UI suites | https://allurereport.org/ |
| **Postman / Newman** | API test creation and CLI execution | https://www.postman.com/downloads/ |
| **Selenium WebDriver** | UI automation framework | https://www.selenium.dev/ |
| **WebDriverManager** | Driver binaries manager | https://github.com/bonigarcia/webdrivermanager |
| **Chrome / Firefox** | Supported browsers | [Chrome](https://www.google.com/chrome/) / [Firefox](https://www.mozilla.org/firefox/) |
| **Git** | Version control | https://git-scm.com/downloads |

---

## 6. Environment Setup

### 6.1 API (Postman / REST Assured)
- Import the **Postman collection** and **environment**.  
- Or run via CLI:
  ```bash
  newman run Reqres.in.postman_collection.json -e Reqres.in.postman_environment.json
  ```
- To run automatically (Windows only):
  ```bash
  windows-postman-run-and-report.bat
  ```

- For REST Assured:
  ```bash
  mvn clean test
  ```
  or via batch script:
  ```bash
  windows-rest-run-and-report.bat
  ```
  Results: `target/allure-results/`  
  Generate report automatically with:
  ```bash
  allure serve target/allure-results
  ```

### 6.2 UI (Selenium)
1. Open the `Selenium` folder in IntelliJ IDEA.  
2. Run via Maven:
   ```bash
   mvn clean test
   ```
3. Or run the automated script (Windows):
   ```bash
   run-and-report.bat
   ```
4. To view the Allure report:
   ```bash
   allure serve target/allure-results
   ```

---

## 7. Repository Structure
```
Recres-SauseDemo-QA/
│
├── .idea/
│
├── Postman/
│   ├── reports/
│   ├── Reqres.in.postman_collection.json
│   ├── Reqres.in.postman_environment.json
│   └── windows-postman-run-and-report.bat
│
├── REST Assured/
│   ├── .idea/
│   ├── reports/
│   ├── src/
│   ├── pom.xml
│   └── windows-rest-run-and-report.bat
│
├── Selenium/                    
│   ├── .allure/
│   ├── .idea/
│   ├── allure-results/
│   ├── src/
│   ├── windows-selenium-run-and-report.bat
│   ├── .gitignore
│   ├── pom.xml
│   └── testing.xml
│
└── README.md
```

---

## 8. Bonus Task: Multi‑Resolution Support
The Selenium framework supports **different browser resolutions** to simulate desktop, tablet and mobile.  
Supply a `resolution` property to the Driver Factory in one of the following ways.

### Examples (Maven)
```bash
mvn clean test -Dbrowser=chrome  -Dresolution=1920x1080   # Desktop
mvn clean test -Dbrowser=chrome  -Dresolution=1366x768    # Laptop
mvn clean test -Dbrowser=chrome  -Dresolution=768x1024    # Tablet (portrait)
mvn clean test -Dbrowser=chrome  -Dresolution=375x667     # Mobile
mvn clean test -Dbrowser=firefox -Dresolution=headless    # Headless
```
> Accepted formats: `WIDTHxHEIGHT` (e.g., `1920x1080`) or `headless`.

### Examples (Batch script)
Edit the **Selenium** batch file and set:
```bat
set "BROWSER=chrome"
set "RES=1920x1080"  REM options: 1920x1080 / 1366x768 / 768x1024 / 375x667 / headless

call mvn -Dmaven.test.failure.ignore=true ^
  -Dbrowser=%BROWSER% ^
  -Dresolution=%RES% ^
  clean test io.qameta.allure:allure-maven:serve
```
This script runs the tests and immediately opens the **Allure** report.

---

### Notes
- The repo is designed for educational and assessment purposes.  
- All credentials and environment data are handled via configuration files.  
- Automated `.bat` scripts are included for full run + Allure report generation.  
- No compiled or built data is included as per task requirements.

---

**Author:** [Petya Zhelyazkova](https://github.com/petyakrzhelyazkova)  
**License:** Private project for QA Automation Challenge.
