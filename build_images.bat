@echo off
cd /d "%~dp0"  REM Change to the directory of this batch file

pushd ./accounts
mvn spring-boot:build-image -DskipTests
popd

pushd ./cards
mvn spring-boot:build-image -DskipTests
popd

pushd ./loans
mvn spring-boot:build-image -DskipTests
popd

echo Process completed.