@echo off
echo Starting E-Clinic Frontend...
echo.

cd frontend

if not exist node_modules (
    call npm install

) else (
    echo Dependencies installed alr
)

echo.
echo http://localhost:3000
echo Ctrl+C stop
echo.

call npm start

pause
