# Compilateur CompileXXL

## Groupe 5 - L3 MIASHS, parcours MIAGE (2025-2026)

**Membres du groupe :**

* BABACHANAKH Kateryna
* SHKRED Diana
* CIFTCI Mehmet Can
* SAIDANI Alyssa

---

## Description

Ce projet consiste en la réalisation d’un compilateur pour un langage simplifié.
Le compilateur effectue les étapes suivantes :

* Analyse lexicale et syntaxique
* Construction d’un arbre syntaxique abstrait
* Construction d’une table des symboles (TDS)
* Génération de code assembleur BETA

---

## Contenu du dossier

* `compilateur_compilexxl.jar` : version exécutable du compilateur
* `run.bat` : script de lancement sous Windows
* `run.sh` : script de lancement sous Linux / Mac
* `samples/` : fichiers d’exemple pour tester le compilateur

---

## Prérequis

* Java installé (version 17 ou supérieure recommandée)

Vérification :

```
java -version
```

---

## Exécution du compilateur

### Méthode 1 : via script (Windows)

Double-cliquer sur :

```
run.bat
```

Puis entrer le nom d’un fichier dans le dossier `samples`, par exemple :

```
ex1_programme_minimal.txt
```

---

### Méthode 2 : via ligne de commande

Dans le dossier `compilateur-bin`, exécuter :

```
java -jar compilateur_compilexxl.jar samples/ex1_programme_minimal.txt
```

---

### Méthode 3 : Linux / WSL

```
java -jar compilateur_compilexxl.jar samples/ex1_programme_minimal.txt
```

(Assurez-vous que Java est installé)

---

## Fichiers de test

Le dossier `samples` contient plusieurs programmes permettant de tester différentes fonctionnalités :

* ex1_programme_minimal.txt
* ex2_variables_globales.txt
* ex3_expression.txt
* ex4_ecriture.txt
* ex5_ecriture.txt
* ex6_variables_locales_et_parametres.txt
* ex7_conditionnelle.txt
* ex8_iteration.txt
* ex9_recursivite.txt

---

## Remarques

* Le compilateur fonctionne sans configuration complexe grâce à la distribution binaire.
* Les scripts permettent un lancement rapide, mais l’exécution directe avec `java -jar` est également possible.
* Le projet a été testé sous Windows et Linux (WSL).

---

## Conclusion

Ce compilateur offre une implémentation complète des principales étapes de la compilation, et sa distribution le rend facile à utiliser pour tous.
