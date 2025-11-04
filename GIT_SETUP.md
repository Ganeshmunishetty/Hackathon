# Git Repository Setup Guide

## Problem: Too Many Files

If you're getting "Try uploading fewer than 100 at a time" error, it's likely because:
1. `node_modules/` has thousands of files
2. Maven `target/` directories have build artifacts
3. IDE files are being tracked

## Solution: Clean Up Before Committing

### Step 1: Remove Already Tracked Files

If you've already committed files that should be ignored:

```bash
# Remove node_modules from git tracking (if it was added)
git rm -r --cached node_modules/

# Remove all target directories from git tracking
git rm -r --cached **/target/

# Remove IDE files
git rm -r --cached .idea/
git rm -r --cached .vscode/
git rm -r --cached *.iml

# Remove logs
git rm -r --cached logs/
git rm -r --cached *.log
```

### Step 2: Verify .gitignore is Working

```bash
# Check what files would be committed
git status

# Should NOT see:
# - node_modules/
# - target/ directories
# - .log files
# - .idea/ or .vscode/
```

### Step 3: Commit in Smaller Batches

If you still have too many files, commit by service:

```bash
# Batch 1: Core documentation
git add README.md REPOSITORY_README.md START_HERE.md .gitignore
git commit -m "Add documentation and gitignore"

# Batch 2: Eureka Server
git add eureka-server/
git commit -m "Add Eureka Server"

# Batch 3: User Management Service
git add java1-user-management/
git commit -m "Add User Management Service"

# Batch 4: City Entities Service
git add java2-city-entities/
git commit -m "Add City Entities Service"

# Batch 5: Event Processing Service
git add java3-event-processing/
git commit -m "Add Event Processing Service"

# Batch 6: Aggregation Service
git add java4-aggregation/
git commit -m "Add Aggregation Service"

# Batch 7: API Gateway
git add api-gateway/
git commit -m "Add API Gateway"

# Batch 8: Database scripts
git add database/
git commit -m "Add database scripts"

# Batch 9: Frontend (if keeping it)
git add src/ package.json vite.config.ts tsconfig*.json tailwind.config.js postcss.config.js eslint.config.js index.html
git commit -m "Add frontend application"

# Batch 10: Scripts
git add start-all.* stop-all.sh test-apis.*
git commit -m "Add startup and test scripts"

# Batch 11: Documentation
git add SETUP_GUIDE.md QUICK_START.md DEPLOYMENT_STATUS.md
git commit -m "Add setup guides"
```

### Step 4: Push to Repository

```bash
# Push all commits
git push origin main
# or
git push origin master
```

## Alternative: Exclude Frontend Entirely

If you want to keep frontend in a separate repository:

```bash
# Remove frontend from this repo
git rm -r --cached src/ node_modules/ package*.json vite.config.ts tsconfig*.json tailwind.config.js postcss.config.js eslint.config.js index.html

# Update .gitignore to exclude frontend
echo "src/" >> .gitignore
echo "node_modules/" >> .gitignore
echo "package*.json" >> .gitignore
```

## Quick Fix Script

Create `cleanup-git.sh`:

```bash
#!/bin/bash
echo "Cleaning up git repository..."

# Remove common ignored files
git rm -r --cached node_modules/ 2>/dev/null
git rm -r --cached **/target/ 2>/dev/null
git rm -r --cached .idea/ 2>/dev/null
git rm -r --cached .vscode/ 2>/dev/null
git rm -r --cached logs/ 2>/dev/null
git rm -r --cached *.log 2>/dev/null

echo "Done! Now run: git status"
```

Or `cleanup-git.bat` for Windows:

```batch
@echo off
echo Cleaning up git repository...

git rm -r --cached node_modules/ 2>nul
git rm -r --cached **/target/ 2>nul
git rm -r --cached .idea/ 2>nul
git rm -r --cached .vscode/ 2>nul
git rm -r --cached logs/ 2>nul
git rm -r --cached *.log 2>nul

echo Done! Now run: git status
pause
```

## Recommended Repository Structure

For a cleaner repository, consider:

```
smartcity-backend/          (Backend only)
├── java1-user-management/
├── java2-city-entities/
├── java3-event-processing/
├── java4-aggregation/
├── api-gateway/
├── eureka-server/
├── database/
└── README.md

smartcity-frontend/         (Separate repo)
├── src/
├── package.json
└── ...
```

This way, each repo is smaller and easier to manage.

## Verify Before Pushing

```bash
# Check total files
git ls-files | wc -l

# Should be less than 100 files for backend-only
# If including frontend, might be 100-200 files

# Check file sizes
git ls-files | xargs ls -lh | sort -k5 -hr | head -20
```

