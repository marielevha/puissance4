var inputJoueurPremier = document.getElementById("jouer_premier");
var inputDifficulte = document.getElementById("difficulte");


var inputJoueur1Bot = document.getElementById("joueur1Bot");
var inputJoueur2Bot = document.getElementById("joueur2Bot");
var inputJoueur1Humain = document.getElementById("joueur1Humain");
var inputJoueur2Humain = document.getElementById("joueur2Humain");
var inputJoueur1Algo = document.getElementById("joueur1Algo");
var inputJoueur2Algo = document.getElementById("joueur2Algo");
var inputJoueur1Profondeur = document.getElementById("joueur1Profondeur");
var inputJoueur2Profondeur = document.getElementById("joueur2Profondeur");
var inputJoueur1Heuristique = document.getElementById("joueur1Heuristique");
var inputJoueur2Heuristique = document.getElementById("joueur2Heuristique");



/**
 * Lit tous les paramÃ¨tres entrÃ©s par l'utilisateur
 * 
 * @returns {Boolean} Faux si les parametres sont mal remplis, vrai sinon
 */
function checkParametres() {
    // Joueur 1
    joueur1Bot = (inputJoueurPremier.checked) ? false : true;
    joueur1Nom = "Joueur";
    joueur1Algo = 3; // A-B
    joueur1Profondeur = inputDifficulte.options[inputDifficulte.selectedIndex].value;
    joueur1Heuristique = 1;

    // Joueur 2
    joueur2Bot = (inputJoueurPremier.checked) ? true : false ;
    joueur2Nom = "Bot";
    joueur2Algo = 3; //A-B
    joueur2Profondeur = inputDifficulte.options[inputDifficulte.selectedIndex].value;
    joueur2Heuristique = 1;
	console.debug(joueur2Profondeur);

    if (joueur1Bot) {
        if (joueur1Algo != 1) {
            if (joueur1Algo == 0 || joueur1Profondeur == 0 || joueur1Heuristique == 0) {
                return false;
            }
        }
    }
    if (joueur2Bot) {
        if (joueur2Algo != 1) {
            if (joueur2Algo == 0 || joueur2Profondeur == 0 || joueur2Heuristique == 0) {
                return false;
            }
        }
    }
    return true;
}

/**
 * Ajoute les ecouteurs aux boutons de la partie "Info"
 * 
 * @returns {undefined}
 */
function ajouterEcouteursInfos() {
    // Bouton nouvelle partie
    var boutonNouvellePartie = document.getElementById("nouvellePartie");
    boutonNouvellePartie.addEventListener('click', function(e) {
		jQuery('#etat').hide();
        if (checkParametres()) {
            reinitialiser();
            changerEtat(1, false);
            if (joueur1Bot) {
                jouer();
            }
        } else {
            alert("Verifiez vos paramÃ¨tres");
        }
    }, false);

    // Boutons Humain/Bot qui grise Algo/Heuristique/Profondeur
//    inputJoueur1Humain.addEventListener('click', function() {
//        inputJoueur1Algo.disabled = true;
//        inputJoueur1Profondeur.disabled = true;
//        inputJoueur1Heuristique.disabled = true;
//    }, false);
//    inputJoueur2Humain.addEventListener('click', function() {
//        inputJoueur2Algo.disabled = true;
//        inputJoueur2Profondeur.disabled = true;
//        inputJoueur2Heuristique.disabled = true;
//    }, false);
//    inputJoueur1Bot.addEventListener('click', function() {
//        inputJoueur1Algo.disabled = false;
//        inputJoueur1Profondeur.disabled = false;
//        inputJoueur1Heuristique.disabled = false;
//    }, false);
//    inputJoueur2Bot.addEventListener('click', function() {
//        inputJoueur2Algo.disabled = false;
//        inputJoueur2Profondeur.disabled = false;
//        inputJoueur2Heuristique.disabled = false;
//    }, false);
//    inputJoueur1Algo.addEventListener('change', function() {
//        if (inputJoueur1Algo.options[inputJoueur1Algo.selectedIndex].value == 1) {
//            inputJoueur1Profondeur.disabled = true;
//            inputJoueur1Heuristique.disabled = true;
//        } else {
//            inputJoueur1Profondeur.disabled = false;
//            inputJoueur1Heuristique.disabled = false;
//        }
//    }, false);
//    inputJoueur2Algo.addEventListener('change', function() {
//        if (inputJoueur2Algo.options[inputJoueur2Algo.selectedIndex].value == 1) {
//            inputJoueur2Profondeur.disabled = true;
//            inputJoueur2Heuristique.disabled = true;
//        } else {
//            inputJoueur2Profondeur.disabled = false;
//            inputJoueur2Heuristique.disabled = false;
//        }
//    }, false);
}

/**
 * Change la zone d'etat du jeu
 * 
 * @param {Number} joueur
 * @param {Number} gagnant
 * @returns {undefined}
 */
function changerEtat(joueur, gagnant) {
    var monEtat = document.getElementById("etat");
    if (gagnant) {
        if ((joueur === 1 && !joueur1Bot) || (joueur === 2 && !joueur2Bot)) {
            monEtat.innerHTML = "Vous avez gagnÃ© ! :D";
			jQuery("#etat").removeClass('alert-danger');
			jQuery("#etat").removeClass('alert-info');
			jQuery("#etat").addClass('alert-success');
			jQuery("#etat").show();
        } else if ((joueur === 1 && joueur1Bot) || (joueur === 2 && joueur2Bot)) {
            monEtat.innerHTML = "Vous avez perdu :(";
			jQuery("#etat").removeClass('alert-info');
			jQuery("#etat").removeClass('alert-success');
			jQuery("#etat").addClass('alert-danger');
			jQuery("#etat").show();
        } else {
            monEtat.innerHTML = "Match Nul !";
			jQuery("#etat").removeClass('alert-success');
			jQuery("#etat").removeClass('alert-danger');
			jQuery("#etat").addClass('alert-info');
			jQuery("#etat").show();
        }
//    } else {
//        if (joueur === 1) {
//            monEtat.innerHTML = "Au tour de " + joueur1Nom;
//        } else {
//            monEtat.innerHTML = "Au tour de " + joueur2Nom;
//        }
    }
}

/**
 * Ajoute l'effet aux menus deroulants
 * 
 * @returns {undefined}
 */
function initMenusDeroulants() {
    var regles = document.getElementById("regles");
    var contenuRegles = document.getElementById("contenuRegles");
    contenuRegles.style.display = 'none';

    regles.addEventListener('click', function() {

        contenuRegles.style.display = contenuRegles.style.display === 'none' ? 'block' : 'none';
    }, false);
}
