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
    private static Souris souris;
    private static IAMariel mariel;
    private static Player realPlayer;

    /**
     * Main : méthode de lancement de partie
     * @param args
     */
    public static void main(String[] args) {
        init();
        //test(1);
    }

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
        fenetre = new Fenetre("Puissance 4", (plateau.getColumn() * 100), (plateau.getLine()) * 100);
        souris = fenetre.getSouris();
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
                System.err.println(souris.getPosition().getX() + "-" + souris.getPosition().getY());
                if (player == realPlayer.getNumber() && tour == 1) {
                    //mariel.addPoint();
                    // Si plateau n'est pas plein jouer le coup
                    if (!plateau.full()) {
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
                        int place = mariel.bestMove(plateau.toStringIA(), mariel.getNumber()) + 1;
                        //int place = (new Random().nextInt(7) + 1) * 100;
                        //System.err.println(place);
                        // Si plateau n'est pas pleine jouer le coup
                        if (!plateau.full()) {
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
        for (int i = plateau.getLine() - 1; i >= 0; i--) {
            int spaceX = -space;
            for (int j = 0; j < plateau.getColumn(); j++) {
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

    private static void test(int player) {
        Rectangle rectangle, rectangle1, rectangle2;
        Texte text, text1, text2;
        player = (player % 2 == 1 ? 1 : 2);
        Fenetre fenetre = new Fenetre("Puissance 4 Winner Player : " + player, 300, 200);
        Souris souris = fenetre.getSouris();
        rectangle = new Rectangle((player%2==1 ? Couleur.JAUNE : Couleur.ROUGE), new Point(0,0), new Point(fenetre.getWidth(), fenetre.getHeight()), true);
        String message = "Winner Player " + (player % 2 == 1 ? "YELLOW" : "RED");

        
        rectangle1 = new Rectangle(Couleur.VERT, new Point(50, 0), new Point(150, 50), true);
        rectangle2 = new Rectangle(Couleur.ROUGE, new Point(150, 0), new Point(250, 50), true);
        fenetre.ajouter(rectangle);
        fenetre.ajouter(rectangle1);
        fenetre.ajouter(rectangle2);

        text = new Texte(message, new Font("Calibri", Font.BOLD, 24), new Point(150,100));
        text1 = new Texte("Play !", new Font("Calibri", Font.ITALIC, 24), new Point(100,25));
        text2 = new Texte("Leave !", new Font("Calibri", Font.ITALIC, 24), new Point(200,25));
        fenetre.ajouter(text);
        fenetre.ajouter(text1);
        fenetre.ajouter(text2);

        fenetre.rafraichir();
        int x, y; boolean test = true;
        boolean s = souris.getClicGauche();
        System.out.println("x : " );
        /*while(true) {
            if (souris.getClicGauche()) {
                x = souris.getPosition().getX();
                y = souris.getPosition().getY();
                System.out.println("x : " + x);
                System.out.println("y : " + y);
                System.err.println(souris.getPosition().getX() + "-" + souris.getPosition().getY());
            }
        }*/
    }
}
