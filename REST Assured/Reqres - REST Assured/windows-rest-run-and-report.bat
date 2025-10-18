@echo off
setlocal EnableExtensions EnableDelayedExpansion
chcp 65001 >nul

REM ================================
REM  Reqres API – run tests + Allure
REM  Usage (optional):
REM    run-allure.bat  [apiKey]  [baseUrl]  ["-Dtest=Class#method"]
REM ================================

set "SELF_DIR=%~dp0"
pushd "%SELF_DIR%"

REM ---- Optional args
set "P_APIKEY=%~1"
set "P_BASEURL=%~2"
set "P_EXTRA=%~3"

REM ---- Check Maven
where mvn >nul 2>nul || (echo [ERROR] Maven not found in PATH& popd & exit /b 10)

REM ---- Timestamped output folders
for /f "usebackq delims=" %%T in (`powershell -NoProfile -Command "(Get-Date).ToString('yyyyMMdd_HHmmss')"`) do set "RUNID=%%T"
set "OUT_BASE=%SELF_DIR%reports\%RUNID%"
set "OUT_ALLURE=%OUT_BASE%\allure"
set "OUT_SUREFIRE=%OUT_BASE%\surefire"
mkdir "%OUT_ALLURE%" 2>nul
mkdir "%OUT_SUREFIRE%" 2>nul

REM ---- Maven args for your config keys
set "MVN_ARGS=-DskipTests=false"
if defined P_APIKEY set "MVN_ARGS=%MVN_ARGS% -Dreqres.apiKey=%P_APIKEY%"
if defined P_BASEURL set "MVN_ARGS=%MVN_ARGS% -Dreqres.baseUrl=%P_BASEURL%"
if defined P_EXTRA   set "MVN_ARGS=%MVN_ARGS% %P_EXTRA%"

echo === mvn clean test %MVN_ARGS%
call mvn -q clean test %MVN_ARGS%
set "TEST_EXIT=%ERRORLEVEL%"

REM ---- Surefire HTML (fallback)
call mvn -q surefire-report:report
if exist "target\surefire-reports" xcopy /e /i /y "target\surefire-reports" "%OUT_SUREFIRE%" >nul
if exist "target\site\surefire-report.html" copy /y "target\site\surefire-report.html" "%OUT_SUREFIRE%\index.html" >nul

REM ---- Detect Allure results
set "HAVE_ALLURE_RESULTS=0"
if exist "target\allure-results" (
  for %%F in (target\allure-results\*.json) do (
    set "HAVE_ALLURE_RESULTS=1"
    goto :_have_results
  )
)
:_have_results

REM ---- Find Allure CLI
set "ALLURE_CMD="
where allure >nul 2>nul && set "ALLURE_CMD=allure"
if not defined ALLURE_CMD ( where npx >nul 2>nul && set "ALLURE_CMD=npx allure" )

REM ---- Generate Allure & open
if "%HAVE_ALLURE_RESULTS%"=="1" if defined ALLURE_CMD (
  echo === Generating Allure report to: %OUT_ALLURE%
  call %ALLURE_CMD% generate "target\allure-results" -o "%OUT_ALLURE%" --clean

  if exist "%OUT_ALLURE%\index.html" (
    echo === Opening Allure report
    start "Allure Report" %ALLURE_CMD% open "%OUT_ALLURE%"
    goto :_done
  )
)

REM ---- Fallbacks
if exist "%OUT_SUREFIRE%\index.html" (
  echo === Opening Surefire report
  start "" "%OUT_SUREFIRE%\index.html"
) else if exist "%OUT_BASE%" (
  start "" "%OUT_BASE%"
)

:_done
popd
exit /b %TEST_EXIT%
