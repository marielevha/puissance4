package graphic;

import engine.Plateau;
import ia.IAMariel;
import mg2d.Fenetre;
import mg2d.Souris;
import mg2d.geometrie.*;
import mg2d.geometrie.Point;
import mg2d.geometrie.Rectangle;

import java.awt.*;
import java.util.Random;

public class Graphic {
    private static int player = 1, tour = 1;
    private static boolean win = false;

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        String s = "6x7-211222112211201112210121212000000000000000".replace('1','0').replace('2','0');
        Plateau plateau = new Plateau(s, 0);
        //System.out.print(plateau.toString());
        Fenetre fenetre = new Fenetre("Puissance 4", (plateau.getLineColumn()[1] * 100), (plateau.getLineColumn()[0]) * 100);
        Souris souris = fenetre.getSouris();
        Rectangle rectangle = new Rectangle(Couleur.BLEU, new Point(0,0), new Point(fenetre.getWidth(), fenetre.getHeight()), true);
        fenetre.effacer();
        fenetre.ajouter(rectangle);
        initPlateau(fenetre, plateau);
        fenetre.rafraichir();
        while (!win){
            //System.err.println("win : " + win);
            if (souris.getClicGauche()){
                int place = souris.getPosition().getX();
                if (player == 1){
                    if (!addPoint(plateau, player, place)){
                        player = 2;
                    }
                }
                else {
                    IAMariel mariel = new IAMariel(4);
                    place = mariel.bestMove(plateau.toStringIA()) + 1;
                    System.err.println(place);
                    if (!addPoint(plateau, player, (place * 100))){
                        player = 1;
                    }
                }
                initPlateau(fenetre, plateau);
                System.out.println(plateau.toString());
            }
            fenetre.rafraichir();
        }
        winner();
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
     *
     * @param fenetre
     * @param plateau
     */
    public static void initPlateau(Fenetre fenetre, Plateau plateau){
        int space = 45, add = 100;
        int spaceY = -space;
        for (int i = plateau.getLineColumn()[0] - 1; i >= 0; i--){
            int spaceX = -space;
            for (int j = 0; j < plateau.getLineColumn()[1]; j++){
                if (plateau.getXY(j, i).getContent() == 0){
                    Cercle cercle = new Cercle(Couleur.BLANC, new Point((spaceX + add), (spaceY + add)),space,true);
                    fenetre.ajouter(cercle);
                }
                else if (plateau.getXY(j, i).getContent() == 1){
                    Cercle cercle = new Cercle(Couleur.JAUNE, new Point((spaceX + add), (spaceY + add)),space,true);
                    fenetre.ajouter(cercle);
                }
                else if (plateau.getXY(j, i).getContent() == 2){
                    Cercle cercle = new Cercle(Couleur.ROUGE, new Point((spaceX + add), (spaceY + add)),space,true);
                    fenetre.ajouter(cercle);
                }
                spaceX += add;
            }
            spaceY += add;
        }
    }

    /**
     *
     * @param plateau
     * @param player
     * @param place
     * @return
     */
    public static boolean addPoint(Plateau plateau, int player, int place){
        if (place > -701 && place <= 100){
            win = plateau.addPoint(0, player);
        }
        else if (place > 100 && place <= 200){
            win = plateau.addPoint(1, player);
        }
        else if (place > 200 && place <= 300){
            win = plateau.addPoint(2, player);
        }
        else if (place > 300 && place <= 400){
            win = plateau.addPoint(3, player);
        }
        else if (place > 400 && place <= 500){
            win = plateau.addPoint(4, player);
        }
        else if (place > 500 && place <= 600){
            win = plateau.addPoint(5, player);
        }
        else if (place > 600 && place <= 700){
            win = plateau.addPoint(6, player);
        }
        /*else if (place > 700 && place <= 800){
            win = plateau.addPoint(7, player);
        }
        for (int i = 0; i < 7; i++){

        }*/
        return win;
    }

    /**
     *
     */
    public static void winner(){
        player = (player % 2 == 1 ? 1 : 2);
        Fenetre fenetre = new Fenetre("Puissance 4 Winner Player : " + player, 300, 100);
        Rectangle rectangle = new Rectangle((player%2==1 ? Couleur.JAUNE : Couleur.ROUGE), new Point(0,0), new Point(fenetre.getWidth(), fenetre.getHeight()), true);
        String message = "Winner Player " + (player % 2 == 1 ? "YELLOW" : "RED");
        Texte text = new Texte(message, new Font("Calibri", Font.BOLD, 24), new Point(150,50));
        fenetre.ajouter(rectangle);
        fenetre.ajouter(text);
        fenetre.rafraichir();
    }
}
