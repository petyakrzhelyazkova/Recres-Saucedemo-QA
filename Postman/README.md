# Postman API Tests — Recres.in

This folder contains **API tests** for [Reqres.in](https://reqres.in/) built in **Postman**.

## 🧩 Content
- `Reqres.in.postman_collection.json` — API test collection  
- `Reqres.in.postman_environment.json` — Environment variables  
- `windows-postman-run-and-report.bat` — Script for automatic run and Allure report generation  
- `/reports` — Folder for exported or generated reports

## 🚀 How to Run
### Option 1 — Postman UI
1. Import both the collection and the environment files.
2. Run the requests manually or via the collection runner.

### Option 2 — Newman CLI
```bash
newman run Reqres.in.postman_collection.json -e Reqres.in.postman_environment.json
```

### Option 3 — Windows Script (automated)
```bash
windows-postman-run-and-report.bat
```
This script runs the full collection and opens the Allure report automatically.

## 🧾 Tested Scenarios
- List users and validate JSON response  
- Extract user details by ID  
- Handle missing user validation  
- Create and delete a new user  
- Parameterized base URL for flexibility
