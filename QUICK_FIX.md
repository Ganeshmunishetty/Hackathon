# Quick Fix for "Too Many Files" Error

## The Problem
You're trying to push to a repository but getting: "Try uploading fewer than 100 at a time"

**Main culprit:** `node_modules/` directory has thousands of files!

## Quick Solution (3 Steps)

### Step 1: Run Cleanup Script

**Windows:**
```bash
cleanup-git.bat
```

**Linux/Mac:**
```bash
chmod +x cleanup-git.sh
./cleanup-git.sh
```

This removes already-tracked files that should be ignored.

### Step 2: Check What's Left

```bash
git status
```

You should see much fewer files now. If you still see `node_modules/`, it means it was already committed.

### Step 3: Commit in Small Batches

If you still have too many files, commit by service:

```bash
# 1. Documentation first
git add README.md .gitignore *.md
git commit -m "Add documentation"

# 2. Eureka
git add eureka-server/
git commit -m "Add Eureka Server"

# 3. Services one by one
git add java1-user-management/
git commit -m "Add User Management Service"

git add java2-city-entities/
git commit -m "Add City Entities Service"

# ... continue for each service
```

## Alternative: Exclude Frontend

If you want to keep backend and frontend separate:

```bash
# Remove frontend from tracking
git rm -r --cached src/ node_modules/ package.json package-lock.json

# Update .gitignore
echo "src/" >> .gitignore
echo "node_modules/" >> .gitignore
```

## What Should Be Committed?

✅ **DO Commit:**
- All Java source files (.java)
- pom.xml files
- application.yml files
- README and documentation
- .gitignore
- Database scripts

❌ **DON'T Commit:**
- node_modules/ (has thousands of files)
- target/ directories (Maven build output)
- .log files
- IDE files (.idea/, .vscode/, *.iml)

## After Cleanup

```bash
# Check file count
git ls-files | wc -l

# Should be under 200 files for backend + frontend
# Or under 100 files for backend only
```

Now you can push!

```bash
git push origin main
```

