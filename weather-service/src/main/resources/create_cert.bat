@echo off
setlocal

echo ===========================================
echo Generating Root CA for 10 years (3650 days)
echo ===========================================
openssl genrsa -out myCA.key 4096
openssl req -x509 -new -nodes -key myCA.key -sha256 -days 3650 -out myCA.crt -subj "/C=IN/ST=MH/L=Pune/O=MyDevCA/OU=Dev/CN=My Dev Root CA"

echo ===========================================
echo Generating Server Private Key
echo ===========================================
openssl genrsa -out server.key 2048

echo ===========================================
echo Creating SAN config file
echo ===========================================

echo ==============================
echo Generating CSR with SAN
echo ==============================
openssl req -new -key server.key -out server.csr -config san.cnf

echo ==============================
echo Signing Server Certificate with CA
echo ==============================
openssl x509 -req -in server.csr -CA myCA.crt -CAkey myCA.key -CAcreateserial -out server.crt -days 365 -sha256 -extfile san.cnf -extensions req_ext

echo ==============================
echo Creating PKCS12 Keystore
echo ==============================
openssl pkcs12 -export -in server.crt -inkey server.key -out server.p12 -name mycert -CAfile myCA.crt -caname root -password pass:changeit

echo.
echo ======================================
echo ✅ Done!
echo Generated files:
echo   - myCA.key   (Root CA private key)
echo   - myCA.crt   (Root CA certificate - import this into Windows Trust Store)
echo   - server.key (Server private key)
echo   - server.crt (Server TLS certificate signed by CA)
echo   - server.p12 (PKCS12 keystore for Spring Boot)
echo ======================================
pause
endlocal
