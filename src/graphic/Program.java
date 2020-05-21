package graphic;

import mg2d.Fenetre;
import mg2d.Souris;
import mg2d.geometrie.Couleur;
import mg2d.geometrie.Point;
import mg2d.geometrie.Rectangle;
import mg2d.geometrie.Texte;

import java.awt.*;

public class Program {
    public static void main(String[] args) {
        Fenetre fenetre = new Fenetre("Puissance 4 no winning player", 300, 100);
        Souris souris = fenetre.getSouris();
        Rectangle rectangle = new Rectangle(Couleur.BLEU, new Point(0,0), new Point(fenetre.getWidth(), fenetre.getHeight()), true);
        String message = "No winning player !!!";
        Texte text = new Texte(message, new Font("Calibri", Font.BOLD, 24), new Point(150,50));
        fenetre.ajouter(rectangle);
        fenetre.ajouter(text);
        fenetre.rafraichir();
        while (true) {
            if (souris.getClicGauche()) {
                System.out.println(souris.getPosition().getX());
            }
            fenetre.rafraichir();
        }
    }
}
