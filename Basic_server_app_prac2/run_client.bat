@echo off
echo Starting HostelMart Client...
mvn clean compile exec:java -Dexec.mainClass=com.hostelmart.HostelMartClient
pause
