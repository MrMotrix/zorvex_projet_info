<h1 style="text-align:center;">L'Interpreteur Zorvex</h1>

**Version** : 1.0.0  

**Auteurs** : AHAMADA Houzaime, OJEDA Martin, NDOUR Mouhamed 

**Date de la dernière version** : 8 avril 2025

<h1 style="text-align:center;">Le Langage Zorvex</h1>
// to be done
<h1 style="text-align:center;">L'Installation</h1>
// to be done
<h1 style="text-align:center;">L'Interface</h1>


L'interface de ZorvexInterpreter est interactive et ergonomique pour l'utilisateur. 

Après avoir installé et lancé le programme, une fenêtre vide avec un bouton au milieu s'affiche sur l'écran de l'ordinateur. 

Pour télécharger le fichier à interpréter, la procédure est simple :
Cliquez sur le bouton « Ouvrir Fichier »
Dans le navigateur de fichiers pop-up, allez dans le dossier du fichier que vous voulez télécharger.
Cliquez sur “sélectionner”

**Note :** Seuls les fichiers avec une extension .txt peuvent être téléchargés dans l'application. Assurez-vous que votre fichier possède cette extension.

![Différents rendus possibles sur le canevas](openScene.png)
<p style="text-align:center;">Scene pour charger un fichier</p>


Une fois le fichier chargé, une interface composée de trois cases vous est présentée. 
Dans le panneau de droite se trouve le canevas, qui est l'endroit où les éléments graphiques seront rendus après qu'une instruction l’indique.
Dans le panneau supérieur gauche se trouve le panneau d'instructions, où se trouve le programme chargé, avec un numéro de ligne attribué à chaque ligne, ainsi qu'un panneau de points d'arrêt 
Dans le panneau inférieur gauche se trouve la console, où seront affichés les résultats de sortie du programme (le cas échéant), ainsi que les éventuels messages d'erreur en cas d'exception. 

Vous pouvez ajouter un point d'arrêt sur n'importe quelle ligne du programme. Il vous suffit de cliquer sur le bouton noir situé à gauche de la ligne où vous souhaitez ajouter un point d'arret. Il deviendra rouge pour indiquer qu'il est activé. De même, si vous souhaitez supprimer un point d'arret à n'importe quel endroit du programme, vous pouvez sélectionner un point rouge qui deviendra noir pour indiquer qu'il est désactivé.


    *insert image of the second scene*

Une fois que vous avez défini les points d'arrêt souhaités, cliquez sur «Commencer» pour lancer l'exécution.

Si vous avez défini un point d'arrêt, le programme s'arrêtera à cette ligne et la mettra en évidence une fois son exécution terminée. Vous remarquerez que cinq boutons sont apparus au centre du panneau gauche de l'interface avec les légendes suivantes :

- “>” : Bouton pour avancer vers la prochaine instruction à être interprété (qui n’est pas forcément la ligne suivante). 
- “<” : Bouton pour revenir vers l’état précédent du programme avant l’exécution de la dernière ligne. 
- “A” : Bouton pour arrêter l’exécution du programme. Attention : Cette instruction efface le contenu du canevas. 
- “C” : Bouton pour continuer l'exécution du programme jusqu’à trouver un prochain point d'arrêt, ou jusqu’à la fin du programme. 
- “R” : Bouton pour recommencer l'exécution du programme

![Vue du programme arreté dans un point d'arrêt](bkpScene.png)
<p style="text-align:center;">Vue du programme arrêté dans un point d'arrêt</p>


Pendant l'exécution, en fonction du type d'instruction interprétée, une action différente sera effectuée sur le canevas. Par exemple, toute instruction d’assignation d’une valeur à un objet ou une variable déclenche le rendu de cet objet sur le canevas. De plus, la définition d’une fonction affichera un objet composé de deux encadrés bleu et gris, représentant le nom de la fonction et les paramètres qu’elle prend en entrée. Aussi, l’appel à une fonction avec des paramètres définis créera un objet similaire mais de couleur rouge, avec les variables affichant explicitement la valeur qu’elles contiennent (et leur évolution imminente).

![Différents rendus possibles sur le canevas](multipleRenders.png)
<p style="text-align:center;">Différents rendus possibles sur le canevas</p>


Si une erreur est détectée pendant l'exécution du programme, celle-ci sera interrompue, et un message détaillé du type d’erreur sera envoyé via la console.

Pour consulter les informations sur le produit, cliquez sur le menu supérieur, dans la section « Aide », « A propos ». En outre, vous y trouverez les coordonnées des développeurs au cas où vous auriez trouvé des bugs ou des erreurs.
