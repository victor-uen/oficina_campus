@echo off
cd /d "%~dp0.."
call mvn --batch-mode package
if errorlevel 1 exit /b 1
echo Abra http://127.0.0.1:8080 no navegador.
java -jar apresentacao-backend\target\oficina-campus.jar %*
