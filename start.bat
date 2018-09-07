echo off
cd %~dp0
mvn clean compile exec:java test
pause