@echo off
setlocal EnableExtensions EnableDelayedExpansion

:: UTF-8 so filenames with ��/�� work
chcp 65001 >nul

:: Work from this script�s folder
set "SELF_DIR=%~dp0"
pushd "%SELF_DIR%"

REM ===== Arguments (all optional) ===========================================
REM %1 => collection file (default: Reqres.in.postman_collection.json)
REM %2 => environment file (default: Reqres.in.postman_environment.json)
REM %3 => folder name to run (quote it if it has spaces or &)
REM %4 => iteration count (default: 1)
REM ==========================================================================

set "COLLECTION=%~1"
if not defined COLLECTION set "COLLECTION=Reqres.in.postman_collection.json"

set "ENVFILE=%~2"
if not defined ENVFILE set "ENVFILE=Reqres.in.postman_environment.json"

set "FOLDER=%~3"
set "ITER=%~4"
if not defined ITER set "ITER=1"

:: Resolve to absolute paths
for %%I in ("%COLLECTION%") do set "COLLECTION=%%~fI"
for %%I in ("%ENVFILE%")  do set "ENVFILE=%%~fI"

:: If defaults don�t match due to encoding, auto-detect sensible candidates
if not exist "%COLLECTION%" for /f "delims=" %%F in ('dir /b "*Happy Path*collection.json" 2^>nul') do set "COLLECTION=%SELF_DIR%%%F"
if not exist "%ENVFILE%"  for /f "delims=" %%F in ('dir /b "*Clean v3*environment.json" 2^>nul')  do set "ENVFILE=%SELF_DIR%%%F"

:: Verify files
if not exist "%COLLECTION%" (
  echo Collection not found: "%COLLECTION%"
  popd & exit /b 2
)
if not exist "%ENVFILE%" (
  echo Environment not found: "%ENVFILE%"
  popd & exit /b 3
)

:: Find newman (prefer global, else npx)
set "NEWMAN_CMD="
where newman >nul 2>nul && set "NEWMAN_CMD=newman"
if not defined NEWMAN_CMD (
  where npx >nul 2>nul && set "NEWMAN_CMD=npx newman"
)
if not defined NEWMAN_CMD (
  echo Neither "newman" nor "npx" found. Install Node.js and newman.
  popd & exit /b 4
)

:: Timestamp for report dir
for /f "usebackq delims=" %%T in (`powershell -NoProfile -Command "(Get-Date).ToString('yyyyMMdd_HHmmss')"`) do set "RUNID=%%T"
if not defined RUNID (
  set "RUNID=%DATE:/=-%_%TIME::=%"
  set "RUNID=!RUNID: =0!"
  set "RUNID=!RUNID:.=!"
)

set "REPORT_DIR=%SELF_DIR%reports\%RUNID%"
mkdir "%REPORT_DIR%" 2>nul

echo Running:
echo   Collection: %COLLECTION%
echo   Environment: %ENVFILE%
if defined FOLDER echo   Folder: %FOLDER%
echo   Iterations: %ITER%
echo   Reports -> %REPORT_DIR%
echo.

:: Run newman
if defined FOLDER (
  call %NEWMAN_CMD% run "%COLLECTION%" -e "%ENVFILE%" --iteration-count %ITER% --folder "%FOLDER%" ^
    -r cli,htmlextra,junit,json ^
    --reporter-htmlextra-export "%REPORT_DIR%\report.html" ^
    --reporter-htmlextra-title "Smart Garage API" ^
    --reporter-junit-export "%REPORT_DIR%\junit.xml" ^
    --reporter-json-export "%REPORT_DIR%\report.json" ^
    --delay-request 50 --timeout-request 60000
) else (
  call %NEWMAN_CMD% run "%COLLECTION%" -e "%ENVFILE%" --iteration-count %ITER% ^
    -r cli,htmlextra,junit,json ^
    --reporter-htmlextra-export "%REPORT_DIR%\report.html" ^
    --reporter-htmlextra-title "Recres.in API" ^
    --reporter-junit-export "%REPORT_DIR%\junit.xml" ^
    --reporter-json-export "%REPORT_DIR%\report.json" ^
    --delay-request 50 --timeout-request 60000
)

set "EXITCODE=%ERRORLEVEL%"

echo.
echo Finished. Exit code: %EXITCODE%
echo HTML:   %REPORT_DIR%\report.html
echo JUnit:  %REPORT_DIR%\junit.xml
echo JSON:   %REPORT_DIR%\report.json

:: Auto-open the HTML report (wait briefly in case of slow IO)
set "REPORT_HTML=%REPORT_DIR%\report.html"
for /l %%S in (1,1,5) do if not exist "%REPORT_HTML%" timeout /t 1 >nul
if exist "%REPORT_HTML%" (
  start "" "%REPORT_HTML%"
) else (
  if exist "%REPORT_DIR%" start "" "%REPORT_DIR%"
)

popd
exit /b %EXITCODE%
