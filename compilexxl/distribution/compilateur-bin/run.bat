@echo off
chcp 65001
set /p fichier=Entrez le nom du fichier (dans samples/) :
java -Dfile.encoding=UTF-8 -jar compilateur_compilexxl.jar samples/%fichier%
pause