@echo off
setlocal EnableDelayedExpansion

:loop
REM Генерируем случайные числа
SET /A ACCOUNT_ID=%RANDOM% %% 1001

REM Отправляем POST-запрос на "/deposit"
curl.exe -sS -X GET "http://localhost:8080/balance?accountId=!ACCOUNT_ID!"
echo/

goto :loop