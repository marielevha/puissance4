package graphic;

import engine.Plateau;
import ia.IAMariel;
import mg2d.Fenetre;
import mg2d.Souris;
import mg2d.geometrie.*;
import mg2d.geometrie.Point;
import mg2d.geometrie.Rectangle;
import engine.player.Player;

import java.awt.*;

public class Graphic {
    private static int player, tour = 1, levelIA = 5, GAME_MODE = 1;
    private static boolean win = false, added = false;
    private static String typePlayer;
    private static Plateau plateau;
    private static Fenetre  fenetre, fenetre1, fenetre2, fenetre3, fenetre4;
    private static Souris souris;
    private static IAMariel mariel;
    private static Player realPlayer, realPlayer2;

    /**
     * Main : lancement du programme
     * @param args
     */
    public static void main(String[] args) {
        Thread game, sound;
        sound = new Thread() {
            @Override
            public void run() {
                super.run();
                Sound.playMusic();
            }
        };
        sound.start();

        game = new Thread() {
            @Override
            public void run() {
                super.run();
                String string = "6x7-" +
                        "1211121" +
                        "2122122" +
                        "1211221" +
                        "2122112" +
                        "0211221" +
                        "0112212";
                string = "6x7-" +
                        "0211200" +
                        "0102200" +
                        "0001100" +
                        "0000200" +
                        "0000200" +
                        "0000000";
                //
                // plateau = new Plateau(string, 0);
                plateau = new Plateau(6, 7);
                //System.out.println(plateau.toString());
                windowGameMode();
                //System.out.println("Left : " + countEmptyCase(4, 5, "left"));
                //System.out.println("Right : " + countEmptyCase(4, 5, "right"));
            }
        };
        game.start();
    }

    /**
     * Init : initialisation de la partie
     */
    public static void init(Plateau plateau) {
        fenetre = new Fenetre("Puissance 4", (plateau.getColumn() * 100), (plateau.getLine()) * 100);
        souris = fenetre.getSouris();
        Rectangle rectangle = new Rectangle(Couleur.BLEU, new Point(0,0), new Point(fenetre.getWidth(), fenetre.getHeight()), true);
        fenetre.effacer();
        fenetre.ajouter(rectangle);
        initPlateau(fenetre, plateau);
        fenetre.rafraichir();

        if (GAME_MODE == 1) {
            modeRealVSIA();
        } else {
            modeRealVSReal();
        }
    }

    /**
     * ModeRealVSIA : lancement du mode de jeu Real VS IA (un jeoueur réel contre une IA)
     */
    public static void modeRealVSIA() {
        /**
         * Initialisation du joueur réel avec le numéro 1
         * @number : 1
         */
        realPlayer = new Player(1);

        /**
         * Initialisation de l'IA avec le numéro 2 et le niveau passé en paramètre
         * @level : levelIA
         * @number : 2
         */
        mariel = new IAMariel(2, levelIA);

        /**
         * Boucle infinie de la partie jusqu'à ce qu'il aiet un gagnant
         * @win : si win = true, partie terminée
         */
        while (!win) {
            if (souris.getClicGauche()) {
                moveReal(realPlayer);
            }
            else {
                moveIA();
            }
            fenetre.rafraichir();
        }
        launchWindow(true);
    }

    /**
     * ModeRealVSReal : lancement du mode de jeu Real VS Real (un jeoueur réel contre un autre)
     */
    public static void modeRealVSReal() {
        /**
         * Initialisation du joueur réel avec le numéro 1
         * @number : 1
         */
        realPlayer = new Player(1);

        /**
         * Initialisation du joueur réel avec le numéro 2
         * @number : 1
         */
        realPlayer2 = new Player(2);

        /**
         * Boucle infinie de la partie jusqu'à ce qu'il aiet un gagnant
         * @win : si win = true, partie terminée
         */
        while (!win) {
            if (souris.getClicGauche()) {
                if (tour == 1) {
                    moveReal(realPlayer);
                    tour = 2;
                }
                else {
                    moveReal(realPlayer2);
                    tour = 1;
                }
                fenetre.rafraichir();
            }
            fenetre.rafraichir();
        }
        launchWindow(true);
    }

    /**
     * InitPlateau : Initialise le plateau graphique du jeu
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
     * AddPoint : méthode permettant d'ajouter un pion au plateau
     * @param plateau
     * @param player
     * @param place
     * @return win : si true partie terminée
     */
    public static boolean addPoint(Plateau plateau, int player, int place) {
        int column = 0;
        if (place > -701 && place <= 100 && (!plateau.fullColumn(0))) {
            win = plateau.addPoint(0, player);
            column = 0;
            added = true;
        }
        else if (place > 100 && place <= 200 && (!plateau.fullColumn(1))) {
            win = plateau.addPoint(1, player);
            column = 1;
            added = true;
        }
        else if (place > 200 && place <= 300 && (!plateau.fullColumn(2))) {
            win = plateau.addPoint(2, player);
            column = 2;
            added = true;
        }
        else if (place > 300 && place <= 400 && (!plateau.fullColumn(3))) {
            win = plateau.addPoint(3, player);
            column = 3;
            added = true;
        }
        else if (place > 400 && place <= 500 && (!plateau.fullColumn(4))) {
            win = plateau.addPoint(4, player);
            column = 4;
            added = true;
        }
        else if (place > 500 && place <= 600 && (!plateau.fullColumn(5))) {
            win = plateau.addPoint(5, player);
            column = 5;
            added = true;
        }
        else if (place > 600 && place <= 700 && (!plateau.fullColumn(6))) {
            win = plateau.addPoint(6, player);
            column = 6;
            added = true;
        }

        if (GAME_MODE == 1) {
            // Ajout du pion dans le plateau utilisé par l'agorithme MinMax
            mariel.addPoint(column, player);
        }
        fenetre.rafraichir();
        return win;
    }

    /**
     * Full : Vérifie si le plateau est plein et lance la méthode de fin de partie
     */
    private static void full() {
        if (plateau.full()) {
            launchWindow(false);
        }
    }

    /**
     * Reload : méthode permettant de relancer la partie
     */
    private static void reload(boolean choice) {
        if (choice) {
            closeWindow(fenetre);
            closeWindow(fenetre2);

            plateau.empty();
            player = 1; tour = 1;
            win = false;
            windowGameMode();
        }
        else {
            closeWindow(fenetre);
            closeWindow(fenetre2);
            System.exit(0);
        }
    }

    /**
     * GameOver :  méthode de fin de partie
     * Si winner = true il y a un gagnant
     * Sinon match null
     * @param winner
     */
    private static void gameOver(boolean winner) {
        String title, message, message1 = null; Couleur couleur, couleur1 = Couleur.ROUGE;
        if (winner) {
            player = (player % 2 == 1 ? 1 : 2);
            couleur = (player%2==1 ? Couleur.JAUNE : Couleur.ROUGE);
            title = "Puissance 4 Winner Player : " + player;
            message = "Winner Player " + (player % 2 == 1 ? "YELLOW" : "RED ");
            message1 = "(" + typePlayer + ")";
            if (couleur == Couleur.ROUGE) {
                couleur1 = Couleur.BLEU;
            }
        }
        else {
            couleur = Couleur.BLEU;
            title = "Puissance 4 no winning player";
            message = "No winning player !!!";
            message1 = "(Equality !!!)";
        }
        fenetre2 = new Fenetre(title, 300, 200);
        Souris souris = fenetre2.getSouris();
        Rectangle rectangle = new Rectangle(couleur, new Point(0,0), new Point(fenetre2.getWidth(), fenetre2.getHeight()), true);
        Texte text = new Texte(message, new Font("Calibri", Font.BOLD, 24), new Point(150,150));
        Texte text0 = new Texte(message1, new Font("Calibri", Font.BOLD, 24), new Point(150,100));
        Texte text1 = new Texte("Play !", new Font("Calibri", Font.ITALIC, 24), new Point(100,25));
        Texte text2 = new Texte("Leave !", new Font("Calibri", Font.ITALIC, 24), new Point(200,25));

        Rectangle rectangle1 = new Rectangle(Couleur.VERT, new Point(50, 0), new Point(150, 50), true);
        Rectangle rectangle2 = new Rectangle(couleur1, new Point(150, 0), new Point(250, 50), true);


        fenetre2.ajouter(rectangle);
        fenetre2.ajouter(rectangle1);
        fenetre2.ajouter(rectangle2);

        fenetre2.ajouter(text);
        fenetre2.ajouter(text0);
        fenetre2.ajouter(text1);
        fenetre2.ajouter(text2);

        fenetre2.rafraichir();
        while (true) {
            if (souris.getClicGauche()) {
                int x = souris.getPosition().getX();
                int y = souris.getPosition().getY();
                if ((x >= 50 && x <= 150) && (y >= 0 && y <= 50)) {
                    //System.out.println(x + "-" + y);
                    reload(true);
                }
                else if ((x >= 150 && x <= 250) && (y >= 0 && y <= 50)) {
                    //System.err.println(x + "-" + y);
                    reload(false);
                }
                //System.out.println("X : " + souris.getPosition().getX());
                //System.out.println("Y : " + souris.getPosition().getY());
            }
            fenetre2.rafraichir();
        }
    }

    /**
     * LaunchWindow : méthode permettant le lancement de la fenetre fin de partie
     * @param value
     */
    private static void launchWindow(boolean value){
        try {
            Thread.sleep(100);
            gameOver(value);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * CloseWindow : méthode permettant la fermeture d'une fenetre passée en paramètre
     * @param fenetre
     */
    private static void closeWindow(Fenetre fenetre) {
        fenetre.effacer();
        fenetre.fermer();
        fenetre.rafraichir();
    }

    /**
     * WidowLevel : construit et affiche la fenetre permettant aux joueur de choisir le niveau de l'IA qu'il souhaite défiée
     */
    private static void widowLevel() {
        fenetre3 = new Fenetre("CHOOSE LEVEL IA", 300, 200);
        Souris souris = fenetre3.getSouris();
        Rectangle rectangle = new Rectangle(Couleur.BLEU, new Point(0,0), new Point(fenetre3.getWidth(), fenetre3.getHeight()), true);
        Texte text1 = new Texte("Level 5 : MinMax", new Font("Calibri", Font.ITALIC, 18), new Point(125,20));
        Texte text2 = new Texte("Level 4", new Font("Calibri", Font.ITALIC, 18), new Point(125,55));
        Texte text3 = new Texte("Level 3", new Font("Calibri", Font.ITALIC, 18), new Point(125,95));
        Texte text4 = new Texte("Level 2", new Font("Calibri", Font.ITALIC, 18), new Point(125,135));
        Texte text5 = new Texte("Level 1", new Font("Calibri", Font.ITALIC, 18), new Point(125,175));

        int size = 50, height = 35;
        Rectangle rectangle1 = new Rectangle(Couleur.VERT, new Point(size, 5), new Point(200, height), true);
        Rectangle rectangle2 = new Rectangle(Couleur.VERT, new Point(size, 40), new Point(200, 75), true);
        Rectangle rectangle3 = new Rectangle(Couleur.VERT, new Point(size, 80), new Point(200, 115), true);
        Rectangle rectangle4 = new Rectangle(Couleur.VERT, new Point(size, 120), new Point(200, 155), true);
        Rectangle rectangle5 = new Rectangle(Couleur.VERT, new Point(size, 160), new Point(200, 195), true);

        fenetre3.ajouter(rectangle);
        fenetre3.ajouter(rectangle1);
        fenetre3.ajouter(rectangle2);
        fenetre3.ajouter(rectangle3);
        fenetre3.ajouter(rectangle4);
        fenetre3.ajouter(rectangle5);

        fenetre3.ajouter(text1);
        fenetre3.ajouter(text2);
        fenetre3.ajouter(text3);
        fenetre3.ajouter(text4);
        fenetre3.ajouter(text5);

        fenetre3.rafraichir();
        while (true) {
            if (souris.getClicGauche()) {
                int x = souris.getPosition().getX();
                int y = souris.getPosition().getY();
                if ((x >= 35 && x <= 200) && (y >= 160 && y <= 195)) {
                    levelIA = 1;
                    //System.err.println(levelIA);
                    closeWindow(fenetre3);
                    init(plateau);
                    break;
                }
                else if ((x >= 35 && x <= 200) && (y >= 120 && y <= 155)) {
                    levelIA = 2;
                    //System.err.println(levelIA);
                    closeWindow(fenetre3);
                    init(plateau);
                }
                else if ((x >= 35 && x <= 200) && (y >= 80 && y <= 115)) {
                    levelIA = 3;
                    //System.err.println(levelIA);
                    closeWindow(fenetre3);
                    init(plateau);
                }
                else if ((x >= 35 && x <= 200) && (y >= 40 && y <= 75)) {
                    levelIA = 4;
                    //System.err.println(levelIA);
                    closeWindow(fenetre3);
                    init(plateau);
                }
                else if ((x >= 35 && x <= 200) && (y >= 5 && y <= 35)) {
                    levelIA = 5;
                    //System.err.println(levelIA);
                    closeWindow(fenetre3);
                    init(plateau);
                }
                //System.out.println("X : " + souris.getPosition().getX());
                //System.out.println("Y : " + souris.getPosition().getY());
            }
            fenetre3.rafraichir();
        }
    }

    /**
     * WindowGameMode : construit et affiche la fenetre permettant aux joueur de choisir le mode de jeu
     * 2 modes de jeux sont disponible : (Real VS IA) Jouer contre une IA ou (Real VS Real) donnant la possibilité à 2 joueur
     * d'y jouer sur la meme fenetre
     */
    private static void windowGameMode() {
        fenetre4 = new Fenetre("CHOOSE GAME MODE", 300, 200);
        Souris souris = fenetre4.getSouris();
        Rectangle rectangle = new Rectangle(Couleur.BLEU, new Point(0,0), new Point(fenetre4.getWidth(), fenetre4.getHeight()), true);
        Texte text3 = new Texte("Real VS Real", new Font("Calibri", Font.ITALIC, 18), new Point(125,95));
        Texte text4 = new Texte("Real VS IA", new Font("Calibri", Font.ITALIC, 18), new Point(125,135));

        int size = 50;
        Rectangle rectangle3 = new Rectangle(Couleur.VERT, new Point(size, 80), new Point(200, 115), true);
        Rectangle rectangle4 = new Rectangle(Couleur.VERT, new Point(size, 120), new Point(200, 155), true);


        fenetre4.ajouter(rectangle);
        fenetre4.ajouter(rectangle3);
        fenetre4.ajouter(rectangle4);

        fenetre4.ajouter(text3);
        fenetre4.ajouter(text4);

        fenetre4.rafraichir();
        while (true) {
            if (souris.getClicGauche()) {
                int x = souris.getPosition().getX();
                int y = souris.getPosition().getY();
                if ((x >= 35 && x <= 200) && (y >= 120 && y <= 155)) {
                    GAME_MODE = 1;
                    //System.err.println(GAME_MODE);
                    closeWindow(fenetre4);
                    widowLevel();
                }
                else if ((x >= 35 && x <= 200) && (y >= 80 && y <= 115)) {
                    GAME_MODE = 2;
                    //System.err.println(GAME_MODE);
                    closeWindow(fenetre4);
                    init(plateau);
                }
                //System.out.println("X : " + souris.getPosition().getX());
                //System.out.println("Y : " + souris.getPosition().getY());
            }
            fenetre4.rafraichir();
        }
    }

    /**
     * MoveIA : cette méthode permet de à l'IA de jouer son tour
     */
    public static void moveIA() {
        try {
            Thread.sleep(0);
            if (player == mariel.getNumber() && tour == 2) {
                typePlayer = mariel.getTypePlayer();
                //System.err.println("Treatment IA");
                int place = mariel.bestMove(plateau.toStringIA(), mariel.getNumber());// + 1;
                System.err.println("Treatment IA -> " + place);
                // Si plateau n'est pas pleine jouer le coup
                if (!plateau.full()) {
                    if (!plateau.fullColumn(place)) {
                        win = plateau.addPoint(place, player);
                        added = true;
                        initPlateau(fenetre, plateau);
                        if (!win) {
                            full();
                            // Ajoute un pion dans la copy du plateau utilisé par l'agorithme MinMax
                            mariel.addPoint(place, player);
                            player = realPlayer.getNumber();    tour = 1;
                        }
                    }
                }
                else {
                    launchWindow(false);
                }
                //initPlateau(fenetre, plateau);
                initPlateau(fenetre, plateau);
                //System.err.println(plateau.toString());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * MoveReal : cette méthode permet de à un joueur réel de jouer son tour
     * @param realPlayer
     */
    public static void moveReal(Player realPlayer) {
        player = realPlayer.getNumber();
        int place = souris.getPosition().getX();
        if (player == realPlayer.getNumber() && (tour == 1 || tour == 2)) {
            typePlayer = realPlayer.getTypePlayer();
            // Si plateau n'est pas plein jouer le coup
            if (!plateau.full()) {
                if (!addPoint(plateau, player, place) && added) {
                    initPlateau(fenetre, plateau);
                    fenetre.rafraichir();
                    full();
                    if (GAME_MODE == 1) {
                        player = mariel.getNumber(); tour = 2;
                    }
                }
            }
            else {
                launchWindow(false);
            }
        }
        initPlateau(fenetre, plateau);
        //System.out.println(plateau.toString());
    }

    private static void test() {
        String string = "6x7-" +
                "1211122" +
                "2111212" +
                "1221212" +
                "1222111" +
                "2111222" +
                "1221200";
        string = "6x7-" +
                "0122211" +
                "0011210" +
                "0021120" +
                "0002000" +
                "0000000" +
                "0000000";
        //plateau = new Plateau(string, 0);
        //System.err.println(plateau.availableColumn());
        //plateau = new Plateau(6, 7);
        //System.out.print(plateau.toString());
        //init(plateau);
        //test();
        //widowLevel();
        //modeRealVSReal();*
        //windowGameMode();
    }
    private static void winner(boolean winner) {
        if (winner) {
            player = (player % 2 == 1 ? 1 : 2);
            fenetre1 = new Fenetre("Puissance 4 Winner Player : " + player, 300, 100);
            Rectangle rectangle = new Rectangle((player%2==1 ? Couleur.JAUNE : Couleur.ROUGE), new Point(0,0), new Point(fenetre1.getWidth(), fenetre1.getHeight()), true);
            String message = "Winner Player " + (player % 2 == 1 ? "YELLOW" : "RED");
            Texte text = new Texte(message, new Font("Calibri", Font.BOLD, 24), new Point(150,50));
            fenetre1.ajouter(rectangle);
            fenetre1.ajouter(text);
            fenetre1.rafraichir();
        }
        else {
            //fenetre.rafraichir();
            /*try {
                Thread.sleep(1000);
                fenetre.effacer();
                fenetre.fermer();
            } catch (Exception e) {
                e.printStackTrace();
            }*/
            fenetre1 = new Fenetre("Puissance 4 no winning player", 300, 100);
            Rectangle rectangle = new Rectangle(Couleur.BLEU, new Point(0,0), new Point(fenetre1.getWidth(), fenetre1.getHeight()), true);
            String message = "No winning player !!!";
            Texte text = new Texte(message, new Font("Calibri", Font.BOLD, 24), new Point(150,50));
            fenetre1.ajouter(rectangle);
            fenetre1.ajouter(text);
            fenetre1.rafraichir();
            //test();
        }
    }
}
