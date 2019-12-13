@echo off
%~dp0/jbr/bin/java.exe -Dfile.encoding=UTF-8 -jar T-3.jar 
pause
exit()

:a
cls
echo L'installeur de java va se lancer (vous n'avez pas la version 11 de java)
start /wait jdk-11.0.5_windows-x64_bin.exe
java -jar src.jar || (goto b)
exit()

:b
del msgbox.vbs
echo x=msgbox("Vos variables d'environnement sont mal configurées. Ajoutez le chemin d'accès de Java11 dans la variable d'environnement PATH, puis relancez le jeu" ,0, "Erreur : Java.exe non trouvé") >> msgbox.vbs
start msgbox.vbs
