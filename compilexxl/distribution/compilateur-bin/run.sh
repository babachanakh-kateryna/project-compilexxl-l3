#!/bin/bash
echo "Entrez le nom du fichier (dans samples/) :"
read fichier
java -jar compilateur_compilexxl.jar samples/$fichier
