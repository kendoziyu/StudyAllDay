@echo off

set processName=%1

rem processName
:waitInput
if "%processName%"=="" (
	set /p processName=Please input Java process Name:
	goto waitInput
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
	pause
	exit /b 1
)

set pid=
setlocal enabledelayedexpansion
for /f "usebackq tokens=1,2*" %%i in (`"%jps%" -l`) do (
	rem %%i java pid
	rem %%j java process name

	rem search process name
	set pname=%%j
	@echo !pname!|findstr "%processName%">nul
	if !errorlevel!==0 (
		set pid=%%i
	)
)


if "!pid!"=="" (
	echo Not Found pid by %processName%
	pause
	exit /b 1
) else (
	echo Found pid !pid!
)

rem jstack
set jstack=%java_home%\bin\jstack.exe
if not exist "%jstack%" (
	echo Error: Not found %java_home%\bin\jstack.exe
	echo Suggestion: Please check environment variable JAVA_HOME available
	pause
	exit /b 1
)

if not exist D:\logs\. (
	mkdir D:\logs
)
rem generate tdump file. echo off
cmd /q /c  "%jstack%" -l !pid!>D:\logs\!pid!.tdump
if !errorlevel!==0 (
	echo output file: D:\logs\!pid!.tdump
)

endlocal

pause