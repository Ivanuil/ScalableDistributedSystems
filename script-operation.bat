@echo off
setlocal EnableDelayedExpansion

:loop
REM Генерируем случайные числа
SET /A TX_ID=%RANDOM% * 65536 + %RANDOM%
SET /A ACCOUNT_ID=%RANDOM% %% 1001
SET /A AMOUNT=%RANDOM% %% 491 + 10

REM Отправляем POST-запрос на "/deposit"
curl.exe -sS -X POST "http://localhost:8080/deposit?txId=!TX_ID!&accountId=!ACCOUNT_ID!&amount=!AMOUNT!"

REM Генерируем новые случайные числа
SET /A TX_ID=%RANDOM% * 65536 + %RANDOM%
SET /A ACCOUNT_ID=%RANDOM% %% 1001
SET /A AMOUNT=%RANDOM% %% 491 + 10

REM Отправляем POST-запрос на "/withdraw"
curl.exe -sS -X POST "http://localhost:8080/withdraw?txId=!TX_ID!&accountId=!ACCOUNT_ID!&amount=!AMOUNT!"

goto :loop