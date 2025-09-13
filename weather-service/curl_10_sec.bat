@echo off
for /l %%x in (1, 1, 60) do (
    echo %time%
    curl -s "http://localhost:8080/weather?city=London" | jq .
    timeout /t 10 /nobreak
)
