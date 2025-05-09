@echo off
setlocal enabledelayedexpansion

:loop
REM Генерация случайного числа от 0 до 100
SET /A RANDOM_KEY=%RANDOM% %% 1001

REM Выполнение GET-запроса с использованием случайного ключа
curl.exe -X GET http://localhost:8080/get?key=!RANDOM_KEY! > nul 2>&1

REM Генерация случайного числа от 0 до 100 для значения value
SET /A RANDOM_VALUE=%RANDOM% %% 1001

REM Выполнение PUT-запроса с использованием случайного ключа и случайного значения
curl.exe -X PUT http://localhost:8080/put?key=!RANDOM_KEY!^&value=val!RANDOM_VALUE! > nul 2>&1

goto loop
