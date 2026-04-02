# CompileXXL - Compilateur L3 MIASHS

## Groupe 5 – L3 MIASHS, parcours MIAGE (2025-2026)

- BABACHANAKH Kateryna  
- SHKRED Diana  
- CIFTCI Mehmet Can  
- SAIDANI Alyssa  

---

## Description du projet

Le projet **COMPILEXXL** consiste à développer un compilateur pour un langage impératif simplifié, capable de traduire un programme en code assembleur BETA exécutable avec BSim.

Ce projet est **interdisciplinaire** et s’inscrit dans deux enseignements :
- Compilation (analyse lexicale, syntaxique, arbre abstrait, TDS, génération de code)
- Gestion de projet (PBS, WBS, Gantt, Trello)

---

## Fonctionnalités du compilateur

Le compilateur réalise les étapes suivantes :

- Analyse lexicale avec JFlex
- Analyse syntaxique avec CUP
- Construction d’un arbre abstrait
- Construction d’une table des symboles (TDS)
- Génération de code assembleur BETA

---

## Exécution du compilateur

### Prérequis

- Java installé (version 17 ou supérieure)

---

### Exécution en ligne de commande

```bash
java -jar compilateur_compilexxl.jar samples/ex1_programme_minimal.txt
