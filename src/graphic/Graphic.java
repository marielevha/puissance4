package graphic;

import engine.Plateau;
import ia.IAMariel;
import mg2d.Fenetre;
import mg2d.Souris;
import mg2d.geometrie.*;
import mg2d.geometrie.Point;
import mg2d.geometrie.Rectangle;
import old.Player;

import java.awt.*;

public class Graphic {
    private static int player, tour = 1;
    private static boolean win = false, added = false;
    private static Plateau plateau;
    private static Fenetre  fenetre;
    private static IAMariel mariel;
    private static Player realPlayer;

    /**
     * Main : méthode de lancement de partie
     * @param args
     */
    public static void main(String[] args) {
        init();
    }
    /*public static void main(String[] args) {
        String s = "6x7-211222112211201112210121212000200000000000".replace('1','0').replace('2','0');
        Plateau plateau = new Plateau(s);
        System.out.print(plateau.toString());
        Fenetre fenetre = new Fenetre("Puissance 4", (plateau.getLineColumn()[1] * 100), (plateau.getLineColumn()[0]) * 100);
        Souris souris = fenetre.getSouris();
        Rectangle rectangle = new Rectangle(Couleur.BLEU, new Point(0,0), new Point(fenetre.getWidth(), fenetre.getHeight()), true);
        fenetre.effacer();
        fenetre.ajouter(rectangle);
        initPlateau(fenetre, plateau);
        fenetre.rafraichir();
        //int player = 2;
        while (!win){
            if (souris.getClicGauche()){
                int place = souris.getPosition().getX();
                if (player == 1){
                    if (!addPoint(plateau, player, place)){
                        player = 2;
                    }
                    //addPoint(plateau, player, place);

                    //System.out.print("Player One");
                }
                else {
                    //IAMariel mariel = new IAMariel();
                    //place = mariel.levelTwoMove(plateau);
                    if (!addPoint(plateau, player, (place * 100))){
                        player = 1;
                    }
                    //addPoint(plateau, player, place);
                    //System.out.print("Player Two");
                }
                initPlateau(fenetre, plateau);
                System.out.println(plateau.toString());
                //addPoint(plateau, 1, 1);
                //System.out.print(souris.getClicGauche());
            }
            fenetre.rafraichir();
        }
        winner();
    }*/
    /*public static void main(String[] args) {
        String s = "6x7-211222112211201112210121212000200000000000".replace('1','0').replace('2','0');
        Plateau plateau = new Plateau(s);
        System.out.print(plateau.toString());
        Fenetre fenetre = new Fenetre("Puissance 4", (plateau.getLineColumn()[1] * 100), (plateau.getLineColumn()[0]) * 100);
        Souris souris = fenetre.getSouris();
        Rectangle rectangle = new Rectangle(Couleur.BLEU, new Point(0,0), new Point(fenetre.getWidth(), fenetre.getHeight()), true);
        fenetre.effacer();
        fenetre.ajouter(rectangle);
        initPlateau(fenetre, plateau);
        fenetre.rafraichir();
        //int player = 2;
        while (!win){
            if (souris.getClicGauche()){
                int place = souris.getPosition().getX();
                if (player == 1){
                    if (!addPoint(plateau, player, place)){
                        player = 2;
                    }
                    //addPoint(plateau, player, place);

                    //System.out.print("Player One");
                }
                else {
                    if (!addPoint(plateau, player, place)){
                        player = 1;
                    }

                    }
                    //addPoint(plateau, player, place);
                    //System.out.print("Player Two");
                }
                initPlateau(fenetre, plateau);
                System.out.println(plateau.toString());
                //addPoint(plateau, 1, 1);
                //System.out.print(souris.getClicGauche());
            }
            fenetre.rafraichir();

        winner();
    }*/

    /**
     * Init : initialisation de la partie
     */
    public static void init() {
        String string = "6x7-" +
                "0000000" +
                "0000000" +
                "0000000" +
                "0000000" +
                "0000000" +
                "0000000";
        //plateau = new Plateau(string, 0);
        plateau = new Plateau(6, 7);
        //System.out.print(plateau.toString());
        fenetre = new Fenetre("Puissance 4", (plateau.getLineColumn()[1] * 100), (plateau.getLineColumn()[0]) * 100);
        Souris souris = fenetre.getSouris();
        Rectangle rectangle = new Rectangle(Couleur.BLEU, new Point(0,0), new Point(fenetre.getWidth(), fenetre.getHeight()), true);
        fenetre.effacer();
        fenetre.ajouter(rectangle);
        initPlateau(fenetre, plateau);
        fenetre.rafraichir();

        /**
         * Initialisation du joueur réel avec le numéro 1
         * @number : 1
         */
        realPlayer = new Player(1);
        /**
         * Initialisation de l'IA avec le numéro 2 et le niveau 5
         * @level : 5
         * @number : 2
         */
        mariel = new IAMariel(5, 2);

        /**
         * Boucle infinie de la partie jusqu'à ce qu'il aiet un gagnant
         * @win : si win = true, partie terminée
         */
        while (!win) {
            //System.err.println("win : " + win);
            if (souris.getClicGauche()) {
                player = realPlayer.getNumber();
                int place = souris.getPosition().getX();
                if (player == realPlayer.getNumber() && tour == 1) {
                    //mariel.addPoint();
                    // Si plateau n'est pas plein jouer le coup
                    if (!plateau.noCheck()) {
                        if (!addPoint(plateau, player, place) && added) {
                            player = mariel.getNumber(); tour = 2;
                        }
                    }
                }
                //System.out.println("Treatment Player");
                /*else {

                    place = mariel.bestMove(plateau.toStringIA()) + 1;
                    System.err.println(place);
                    if (!addPoint(plateau, player, (place * 100))){
                        player = 1;
                    }
                }*/
                initPlateau(fenetre, plateau);
                System.out.println(plateau.toString());
            }
            else {
                try {
                    Thread.sleep(10);
                    if (player == mariel.getNumber() && tour == 2) {
                        System.err.println("Treatment IA");
                        //IAMariel mariel = new IAMariel(3);
                        int place = mariel.bestMove(plateau.toStringIA()) + 1;
                        //int place = (new Random().nextInt(7) + 1) * 100;
                        //System.err.println(place);
                        // Si plateau n'est pas pleine jouer le coup
                        if (!plateau.noCheck()) {
                            if (!addPoint(plateau, player, (place * 100)) && added) {
                                player = realPlayer.getNumber();    tour = 1;
                            }
                        }
                        /*IAMariel mariel = new IAMariel(4);
                        place = mariel.bestMove(plateau.toStringIA()) + 1;
                        System.err.println(place);
                        if (!addPoint(plateau, player, (place * 100))){
                            player = 1;
                        }*/
                        //initPlateau(fenetre, plateau);
                        initPlateau(fenetre, plateau);
                        System.out.println(plateau.toString());
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            fenetre.rafraichir();
        }
        winner();
    }

    /**
     * Init plateau : Initialise le plateau graphique du jeu
     * @param fenetre
     * @param plateau
     */
    public static void initPlateau(Fenetre fenetre, Plateau plateau) {
        int space = 45, add = 100;
        int spaceY = -space;
        for (int i = plateau.getLineColumn()[0] - 1; i >= 0; i--) {
            int spaceX = -space;
            for (int j = 0; j < plateau.getLineColumn()[1]; j++) {
                if (plateau.getXY(j, i).getContent() == 0) {
                    Cercle cercle = new Cercle(Couleur.BLANC, new Point((spaceX + add), (spaceY + add)),space,true);
                    fenetre.ajouter(cercle);
                }
                else if (plateau.getXY(j, i).getContent() == 1) {
                    Cercle cercle = new Cercle(Couleur.JAUNE, new Point((spaceX + add), (spaceY + add)),space,true);
                    fenetre.ajouter(cercle);
                }
                else if (plateau.getXY(j, i).getContent() == 2) {
                    Cercle cercle = new Cercle(Couleur.ROUGE, new Point((spaceX + add), (spaceY + add)),space,true);
                    fenetre.ajouter(cercle);
                }
                spaceX += add;
            }
            spaceY += add;
        }
    }

    /**
     * Add Point : méthode permettant d'ajouter un pion au plateau
     * @param plateau
     * @param player
     * @param place
     * @return win : si true partie terminée
     */
    public static boolean addPoint(Plateau plateau, int player, int place) {
        if (place > -701 && place <= 100 && (!plateau.fullColumn(0))) {
            win = plateau.addPoint(0, player);
            // Ajout du pion dans le plateau utilisé par l'agorithme MinMax
            mariel.addPoint(0, player);
            added = true;
        }
        else if (place > 100 && place <= 200 && (!plateau.fullColumn(1))) {
            win = plateau.addPoint(1, player);
            mariel.addPoint(1, player);
            added = true;
        }
        else if (place > 200 && place <= 300 && (!plateau.fullColumn(2))) {
            win = plateau.addPoint(2, player);
            mariel.addPoint(2, player);
            added = true;
        }
        else if (place > 300 && place <= 400 && (!plateau.fullColumn(3))) {
            win = plateau.addPoint(3, player);
            mariel.addPoint(3, player);
            added = true;
        }
        else if (place > 400 && place <= 500 && (!plateau.fullColumn(4))) {
            win = plateau.addPoint(4, player);
            mariel.addPoint(4, player);
            added = true;
        }
        else if (place > 500 && place <= 600 && (!plateau.fullColumn(5))) {
            win = plateau.addPoint(5, player);
            mariel.addPoint(5, player);
            added = true;
        }
        else if (place > 600 && place <= 700 && (!plateau.fullColumn(5))) {
            win = plateau.addPoint(6, player);
            mariel.addPoint(6, player);
            added = true;
        }
        else {
            added = false;
        }
        return win;
    }

    /**
     * Winner :  méthode de fin de partie
     * Si type = true il y a un gagnant
     * Sinon match null
     */
    private static void winner() {
        player = (player % 2 == 1 ? 1 : 2);
        Fenetre fenetre = new Fenetre("Puissance 4 Winner Player : " + player, 300, 100);
        Rectangle rectangle = new Rectangle((player%2==1 ? Couleur.JAUNE : Couleur.ROUGE), new Point(0,0), new Point(fenetre.getWidth(), fenetre.getHeight()), true);
        String message = "Winner Player " + (player % 2 == 1 ? "YELLOW" : "RED");
        Texte text = new Texte(message, new Font("Calibri", Font.BOLD, 24), new Point(150,50));
        fenetre.ajouter(rectangle);
        fenetre.ajouter(text);
        fenetre.rafraichir();
    }

    /**
     * Reload : méthode permettant de relancer la partie
     */
    private static void reload(int choice) {
        if (choice == 1) {
            win = false;
            plateau.empty();
        }
        fenetre.fermer();
    }
}
