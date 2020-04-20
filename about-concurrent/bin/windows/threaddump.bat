@echo off
setlocal enabledelayedexpansion


set processName=%1

rem processName
if "%processName%"=="" (
	echo Error: Not get target process name
	echo Suggestion: Please input process name as first param
	exit /b 1
)

rem JAVA_HOME
set java_home=%JAVA_HOME%
if "%java_home%"=="" (
	set java_home=D:\lib\java\jdk8
)

rem jps
set jps=%java_home%\bin\jps.exe
if not exist "%jps%" (
	echo Error: Not found %java_home%\bin\jps.exe
	echo Suggestion: Please check environment variable JAVA_HOME available
	exit /b 1
)

rem jstack
set jstack=%java_home%\bin\jstack.exe
if not exist "%jstack%" (
	echo Error: Not found %java_home%\bin\jstack.exe
	echo Suggestion: Please check environment variable JAVA_HOME available
	exit /b 1
)

set pid=
for /f "usebackq tokens=1,2*" %%i in (`"%jps%" -l`) do (
	rem %%i java pid
	rem %%j java process name
	
	
	rem search process name
	set pname=%%j
	@echo !pname!|findstr "%processName%">nul
	if !errorlevel!==0 (
		set pid=%%i
		echo Found pid !pid!
	)
	
)

if "%pid%"=="" (
	echo Not Found pid by %processName%
	exit /b 1
)


pause