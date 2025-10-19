# Selenium UI Tests - Saucedemo

This folder contains **automated UI tests** for [Saucedemo](https://www.saucedemo.com/) using **Selenium WebDriver**, **Java**, and **TestNG**.

## Content
- `/src` - Test sources and Page Object Models  
- `pom.xml` - Maven dependencies and configuration  
- `testing.xml` - TestNG suite file  
- `windows-selenium-run-and-report.bat` - Script for automatic run and Allure report generation  
- `/allure-results` - Folder for Allure outputs  
- `/target` - Maven build output  
- `/.allure` - Local Allure binary for reporting

## How to Run
### Option 1 - Maven CLI
```bash
mvn clean test
```

### Option 2 - Automated Windows Script
```bash
windows-selenium-run-and-report.bat
```
The script builds, executes all tests, and opens the Allure report automatically.

## Supported Browsers
- Chrome  
- Firefox

## Run on Different Resolutions (Bonus)
You can specify the **resolution** either via Maven or directly inside the batch file.

### Example (Maven)
```bash
mvn clean test -Dbrowser=chrome -Dresolution=1920x1080
mvn clean test -Dbrowser=chrome -Dresolution=375x667
mvn clean test -Dbrowser=firefox -Dresolution=headless
```

### Example (Batch file)
```bat
set "BROWSER=chrome"
set "RES=1366x768"
call mvn -Dbrowser=%BROWSER% -Dresolution=%RES% clean test io.qameta.allure:allure-maven:serve
```

## Tested Scenarios
- Login with standard user  
- Add/remove products from cart  
- Complete checkout flow  
- Verify order completion and sorting functionality  
- Environment, filtering, and reporting configuration
