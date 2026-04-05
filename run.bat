@echo off
echo ===================================================
echo   Compiling the Health Tracker Application...
echo ===================================================
cd src
dir /s /b *.java > sources.txt
javac -cp ".;..\lib\ojdbc11.jar" @sources.txt
if %ERRORLEVEL% neq 0 (
    echo.
    echo [ERROR] Compilation failed. Please check the code for syntax errors.
    pause
    exit /b %ERRORLEVEL%
)

echo.
echo ===================================================
echo   Starting the Application...
echo ===================================================
java -cp ".;..\lib\ojdbc11.jar" Main

cd ..
pause
