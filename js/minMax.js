var colonneMax;


/**
 * Retourne la colonne determinÃ©e par l'algorithme Min Max
 * 
 * @returns {Number} colonne Ã  jouer
 */
function coupMinMax() {
    algoMinMax(0, joueurCourant);
    return colonneMax;
}

/**
 * Algorithme Min Max
 * 
 * @param {type} profondeur
 * @param {type} joueur
 * @returns {Number} min ou max selon le joueur
 */
function algoMinMax(profondeur, joueur) {
    var joueurAdverse = (joueur === 1) ? 2 : 1;
    var joueurCourantAdverse = (joueurCourant === 1) ? 2 : 1;

    // Verifie si victoire
    var victoire = checkVictoire();
    if (victoire !== 0) {
        if (victoire == joueurCourant) {
            return (1000 - profondeur);
        } else {
            return (-1000 + profondeur);
        }
    }

    // Verifie profondeur
    if ((joueurCourant == 1 && profondeur == joueur1Profondeur) || (joueurCourant == 2 && profondeur == joueur2Profondeur) || checkNul()) {
        if (joueurCourant == 1 && joueur1Heuristique == 1 || joueurCourant == 2 && joueur2Heuristique == 1) {
            return heuristique1(joueurCourant);
        } else {
            return heuristique2(joueurCourant);
        }
    }

    profondeur++;
    // Si le joueur est le joueur courant
    if (joueur == joueurCourant) {
        var max = -10000;
        var colonne = 0;
        for (var i = 0; i < 7; i++) {
            var ligne = ligneVide(i);
            // Si la colonne est jouable
            if (ligne >= 0) {
                // On simule le coup
                grille[i][ligne] = joueur;
                var valeur = algoMinMax(profondeur, joueurAdverse);
                // Si on a la valeur max, on mÃ©morise la colonne correspondante
                if (max < valeur) {
                    max = valeur;
                    colonne = i;
                }
                // Retour Ã  l'Ã©tat antÃ©rieur
                grille[i][ligne] = 0;
            }
        }
        colonneMax = colonne;
        return max;
    } else {
        // Si le joueur est le joueur adverse
        var min = 10000;
        for (var i = 0; i < 7; i++) {
            var ligne = ligneVide(i);
            // Si la colonne est jouable
            if (ligne >= 0) {
                // On simule le coup du joueur adverse
                grille[i][ligne] = joueurCourantAdverse;
                var valeur = algoMinMax(profondeur, joueurAdverse);
                if (min > valeur) {
                    min = valeur;
                }
                // Retour Ã  l'Ã©tat antÃ©rieur
                grille[i][ligne] = 0;
            }
        }
        return min;
    }
}
