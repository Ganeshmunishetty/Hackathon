@echo off
echo Cleaning up git repository...
echo.

echo Removing node_modules from git tracking...
git rm -r --cached node_modules/ 2>nul

echo Removing target directories...
git rm -r --cached java1-user-management/target/ 2>nul
git rm -r --cached java2-city-entities/target/ 2>nul
git rm -r --cached java3-event-processing/target/ 2>nul
git rm -r --cached java4-aggregation/target/ 2>nul
git rm -r --cached api-gateway/target/ 2>nul
git rm -r --cached eureka-server/target/ 2>nul

echo Removing IDE files...
git rm -r --cached .idea/ 2>nul
git rm -r --cached .vscode/ 2>nul
git rm --cached *.iml 2>nul

echo Removing logs...
git rm -r --cached logs/ 2>nul
git rm --cached *.log 2>nul

echo.
echo Done! Now run: git status
echo.
echo To commit in batches, see GIT_SETUP.md
pause

