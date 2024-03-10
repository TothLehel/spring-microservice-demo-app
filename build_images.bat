@echo off

rem Array of directories
set "directories=accounts cards loans configserver eurekaserver gatewayserver"

rem Loop through each directory
for %%d in (%directories%) do (
    rem Change directory
    cd %%d
    
    rem Build image
    mvn spring-boot:build-image -DskipTests
    
    rem Move back to parent directory
    cd ..
)

echo Process completed.