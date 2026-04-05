@echo off
cd src
dir /s /b *.java > sources.txt
javac -cp ".;..\lib\ojdbc11.jar" @sources.txt
