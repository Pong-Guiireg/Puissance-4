# Puissance-4 🎮

## Résumé du jeu
Puissance 4 est un jeu de stratégie où deux joueurs s'affrontent pour aligner quatre pions de leur couleur sur une grille. Le premier joueur à réussir cet alignement gagne la partie.

## Étape 1
1. **But de l'étape** 🎯
   Le but de cette étape est de produire la base qui permettra le jeu :
   - La structure de données permettant une partie (position des pions dans la grille)
   - Mettre en place une interface en ligne de commande permettant de représenter la grille
   - Mettre en place l'action qui permet de jouer un tour
   - Mettre en place les fonctions qui vérifient les conditions de victoire/d'égalité
   - Mettre en place le choix du premier joueur et l'alternance des tours pour jouer une partie complète

2. **Contraintes** 🔳 et **Problématiques** 🤔
   - Adoptez une démarche Orientée Objet : identifiez les entités logiques, et créez des classes pertinentes.
   - Quelles en sont les propriétés ?
   - Quelles en sont les méthodes ?
   - Quels types d'accesseurs utiliser ? private, ou public ?
   - L'action de lancement d'un tour pourra être causée par une action locale ou un message reçu par le réseau. Comment évitez-vous de dupliquer le code ?
   - Quelle structure de données allez-vous utiliser pour représenter la grille (2 dimensions) ? Avec quelle facilité pourrez-vous accéder à la valeur stockée à des coordonnées précises (ex : x = 2, y = 4) ? Dans quel sens progressent les indices horizontaux et verticaux ?

3. **Représentation du jeu en ligne de commande** ⭕❌
   Un petit exemple de ce qu'on peut représenter en ligne de commande :
   ```
   Début de la partie
   #        #
   #        #
   #        #
   #        #
   #        #
   #        #
   ##########
    abcdefgh
   Joueur X : Quelle colonne choisissez-vous ?
   > d
   #        #
   #        #
   #        #
   #        #
   #        #
   #   X    #
   ##########
    abcdefgh
   Joueur O : Quelle colonne choisissez-vous ?
   > d
   #        #
   #        #
   #        #
   #        #
   #   O    #
   #   X    #
   ##########
    abcdefgh
   Joueur X : Quelle colonne choisissez-vous ?
   > e
   #        #
   #        #
   #        #
   #   O    #
   #   XX   #
   ##########
    abcdefgh
   Joueur O : Quelle colonne choisissez-vous ?
   > c
   #        #
   #        #
   #        #
   #   O    #
   #  OXX   #
   ##########
    abcdefgh
   ```

4. **Condition de victoire** 🥇
   Il est utile de vérifier la condition de victoire à la fin de chaque tour : Il faut vérifier sur les 4 axes la somme totale des pions alignés contigus autour du pion qui vient d'être posé.
   - Exemple : Si un pion jaune vient d'être posé en position marquée en rose, il faudra additionner les pions jaunes directement à sa droite et à sa gauche. Effectuez également cette vérification en vertical et sur les deux diagonales.

5. **Astuces** 💡
   - N'oubliez pas de vérifier qu'une colonne n'est pas pleine avant de faire le tour, il est possible que le tour ne soit pas valide !
   - Vérifiez également si toutes les colonnes sont pleines et personne n'a gagné : dans ce cas, c'est une égalité et la partie s'arrête !
   - Le nombre de pions à aligner doit être configurable facilement, ne le hardcodez pas !

6. **Finition de l'étape** ✅
   L'étape est considérée finie quand :
   - On peut jouer une partie entière en ligne de commande, en local
   - Les conditions de victoire et d'égalité fonctionnent
   - Vous avez bien testé le programme
   - Un tag `step-1` a été rajouté sur le commit pertinent sur le dépôt git

## Étape 2
1. **But de l'étape** 🎯
   Il s'agira ici d'ajouter à notre jeu une interface graphique à l'aide de JavaFX.

2. **Contraintes** 🔳
   - L'interface sera réalisée avec JavaFX
   - Un message s'affichera en fin de partie avec impossibilité de jouer un tour supplémentaire avec un bouton pour revenir au menu principal

3. **Astuces** 💡
   - Avant de commencer, pensez à vérifier que votre code est suffisamment modulaire pour remplacer la partie affichage aisément
   - Assurez-vous de pouvoir générer un seul .jar avec votre projet final

4. **Finition de l'étape** ✅
   L'étape est considérée terminée si :
   - Le projet dispose d'une interface graphique qui se lance au lancement de l'application
   - Le projet s'exporte en un seul .jar
   - Un tag `step-2` a été rajouté sur le commit pertinent sur le dépôt git
