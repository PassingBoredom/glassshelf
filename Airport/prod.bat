setlocal enableextensions enabledelayedexpansion

@echo off
cls
:start
REM Check if the script is already running in a new instance
if not "%1"=="newInstance" (
    start cmd /k "%~dpnx0" newInstance
    exit
)
color 0b
title Work-a-Holic

rem Setup locations first
set "home=%cd%"
set "install=%CATALINA_HOME%"

:prompt
echo 1: Build
echo 2: Start-Server
echo 3: Start-SQL
echo 4: Kill-Server
echo 5: Kill-SQL
echo 6: Exit
echo 7: Preset FlyHigh
set /p input=

if "%input%" == "1" goto build
if "%input%" == "2" goto startServer
if "%input%" == "3" goto startSQL
if "%input%" == "4" goto killServer
if "%input%" == "5" goto killSQL
if "%input%" == "6" goto exit
if "%input%" == "7" goto speed

goto prompt

:build
set /p "project=Project Name:"
if not defined project ( 
    echo Missing Name 
    goto prompt 
) ELSE ( 
    rem Removing old version in production
    if exist "%install%\webapps\%project%.war" ( 
        del "%install%\webapps\%project%.war"
    )

    if exist "%install%\webapps\%project%\" ( 
        rmdir "%install%\webapps\%project%\" /s /q
    )
) 

set /p "route=Folder to build:"
if not defined route ( 
    echo Missing Name 
    goto prompt 
) ELSE ( 
	goto findFiles
)

echo "Porting File"
move "%project%.war" "%install%\webapps\"
goto prompt

:startServer
start cmd /c "%CATALINA_HOME%\bin\startup.bat"
goto prompt

:startSQL
start cmd /c "%home%\start-sql.bat.lnk"
goto prompt

:killServer
taskkill /F /IM java.exe
goto prompt 

:killSQL
start cmd /c "%home%\stop-sql.bat.lnk"
goto prompt 

:exit 
exit

:javaCompile
cd "%compileHere%"
javac -cp "!CATALINA_HOME!\lib\*" "!cd!\*.java"
goto deploy

:findFiles
cd "%route%\WEB-INF\classes"
set "currentDir=%cd%"

:TraverseLoop
set "subDir="
for /D %%i in ("%currentDir%\*") do (
    if not defined subDir (
        set "subDir=%%i"
    ) else (
        echo More than one subdirectory found in !currentDir!. Exiting loop.
        goto :eof
    )
)

if not defined subDir (
    echo No subdirectory found in !currentDir!. Exiting loop.
	set "compileHere=!currentDir!"
    goto :javaCompile
)

set "currentDir=!subDir!"
goto TraverseLoop

:deploy 
cd "%home%"
echo Building WAR
7z a -tzip "%project%.war" "!home!\!route!\*"

echo Deploying WAR
move "%project%.war" "%install%\webapps\"
goto prompt

rem routine for present build of "FlyHigh"
:speed 
rem Removing old version in production
if exist "%install%\webapps\FlyHigh.war" ( 
	del "%install%\webapps\FlyHigh.war"
)

if exist "%install%\webapps\FlyHigh\" ( 
	rmdir "%install%\webapps\FlyHigh\" /s /q
)

cd "build\WEB-INF\classes"
set "currentDir=%cd%"
:speedLoop
set "subDir="
for /D %%i in ("%currentDir%\*") do (
    if not defined subDir (
        set "subDir=%%i"
    ) else (
        echo More than one subdirectory found in !currentDir!. Exiting loop.
        goto :eof
    )
)

if not defined subDir (
    echo No subdirectory found in !currentDir!. Exiting loop.
	set "compileHere=!currentDir!"
    goto :setDeploy
)

set "currentDir=!subDir!"
goto speedLoop

:setDeploy
cd "%compileHere%"
javac -cp "!CATALINA_HOME!\lib\*" "!cd!\*.java"

rem sending WAR
echo Building WAR
7z a -tzip "FlyHigh.war" "!home!\build\*"

echo Deploying WAR
move "FlyHigh.war" "%install%\webapps\"
goto :prompt

rem end of preset build

:eof
echo This needs to be fixed!