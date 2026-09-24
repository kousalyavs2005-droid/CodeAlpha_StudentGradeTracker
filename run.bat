@echo off
title Student Grade Tracker
if not exist out mkdir out

echo Compiling Student Grade Tracker...
javac -d out ^
 src\com\studentgradetracker\Main.java ^
 src\com\studentgradetracker\model\*.java ^
 src\com\studentgradetracker\repository\*.java ^
 src\com\studentgradetracker\service\*.java ^
 src\com\studentgradetracker\controller\*.java ^
 src\com\studentgradetracker\view\*.java ^
 src\com\studentgradetracker\util\*.java

if errorlevel 1 (
    echo.
    echo Compilation failed. Please check that Java 17+ is installed.
    pause
    exit /b 1
)

echo Starting application...
java -cp out com.studentgradetracker.Main
pause
