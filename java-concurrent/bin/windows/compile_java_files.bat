@echo off
rem find java file in src\main
dir ..\..\*.java /s /b | find "src\main" > .\srclist.txt
if not exist ..\..\target\classes (
    mkdir -p ..\..\target\classes
)
rem compile all found files
javac -d ..\..\target\classes -encoding UTF-8 @srclist.txt
rem del temp file
del .\srclist.txt