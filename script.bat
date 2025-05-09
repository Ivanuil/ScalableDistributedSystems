@echo off

:loop
:: Выполняем GET-запрос
curl.exe -X GET http://localhost:8080/get?key=123 >nul 2>&1

echo /n

:: Выполняем PUT-запрос
curl.exe -X PUT http://localhost:8080/put?key=123^&value=val123 >nul 2>&1

echo /n

goto loop
