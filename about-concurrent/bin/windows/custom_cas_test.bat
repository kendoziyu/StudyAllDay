@echo on
call compile_java_files.bat

setlocal enabledelayedexpansion
for /f "usebackq delims=" %%a in (`dir ..\..\src\main\ /a:d /b /s`) do (
    set classpath=%%a\.;!classpath!
)
echo start /d ..\..\src\main\java java -cp %classpath% helloworld.CustomCASTest
start /d ..\..\src\main\java java -cp %classpath% helloworld.CustomCASTest
rem 延迟
ping -n 1 127.0.0.1 > nul
call threaddump.bat CustomCASTest
pause