@echo off
setlocal EnableExtensions

REM === Project root ===
cd /d "%~dp0"

REM === Pick exactly your three tests ===
set "TESTS=Scenario1Test,Scenario2Test"

REM === Where Allure will write results ===
set "RES_DIR=%cd%\allure-results"
if exist "%RES_DIR%" rmdir /s /q "%RES_DIR%"
mkdir "%RES_DIR%" >nul 2>&1

REM === Config ===
set "BASE_URL=https://www.saucedemo.com/"
set "USER=standard_user"
set "PASS=secret_sauce"

call mvn -Dmaven.test.failure.ignore=true ^
  -Dtest=%TESTS% ^
  -Dallure.results.directory="%RES_DIR%" ^
  -DbaseUrl=%BASE_URL% ^
  -Dusername=%USER% ^
  -Dpassword=%PASS% ^
  clean test io.qameta.allure:allure-maven:serve

echo.
echo If the window closed immediately before, run this file from a Command Prompt:
echo   cd /d "%~dp0"
echo   windows-selenium-run-and-report.bat
pause

