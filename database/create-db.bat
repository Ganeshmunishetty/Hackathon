@echo off
echo Creating Smart City Database...

set /p DB_USER="PostgreSQL username [postgres]: "
if "%DB_USER%"=="" set DB_USER=postgres

set /p DB_PASSWORD="PostgreSQL password: "

echo Creating database...
psql -U %DB_USER% -c "CREATE DATABASE smartcity_db;" 2>nul

if %ERRORLEVEL% EQU 0 (
    echo Database 'smartcity_db' created successfully!
) else (
    echo Database might already exist or there was an error.
    echo Continuing anyway...
)

echo.
echo Database setup complete!
echo You can now start the services.
pause

