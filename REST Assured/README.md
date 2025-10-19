# REST Assured API Tests - Recres.in

This folder contains **Java-based API automation tests** for [Reqres.in](https://reqres.in/) using **REST Assured**, **TestNG**, and **Allure**.

## Content
- `/src` - Test sources and configuration  
- `pom.xml` - Maven dependencies and plugins  
- `windows-rest-run-and-report.bat` - Script for automatic test execution and Allure report generation  
- `/reports` - Folder for local report exports

## How to Run
### Option 1 - Maven CLI
```bash
mvn clean test
```

### Option 2 Automated Windows Script
```bash
windows-rest-run-and-report.bat
```
The script runs the suite and automatically launches the Allure report.

## Tested Scenarios
- List available users and validate JSON  
- Extract and verify user details  
- Handle missing/non-existing users  
- Create and delete a user dynamically  
- Parameterized base URL for multiple environments

## Tools Used
- **Java 17+**  
- **Maven**  
- **REST Assured**  
- **JUnit**  
- **Allure Reports**
