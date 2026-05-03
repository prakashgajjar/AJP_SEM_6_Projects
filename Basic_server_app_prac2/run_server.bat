@echo off
echo Starting HostelMart Server...
mvn clean compile exec:java -Dexec.mainClass=com.hostelmart.HostelMartServer
pause
