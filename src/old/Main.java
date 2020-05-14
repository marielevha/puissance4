package old;

import ia.IAMariel;
import ia.minmax.PlatCopy;

import java.util.ArrayList;

public class Main {
    //ArrayList<Integer> choix = new ArrayList<Integer>();
    public static ArrayList<Integer> choice = new ArrayList<Integer>();
    public static final int nbrLignes = 6;
    public static final int nbrColonnes = 7;
    private static PlatCopy plateau;
    private static boolean IATermine;
    //private PanelPlateau panelPlateau;
    //private PanelBarreEtat barreEtat;
    //private PanelAffichage panelAffichage;
    //private Ecouteur ecouteur;
    private static Player joueur1,joueur2;
    public static void main(String[] args) {
        init();
        list();
    }
    private static void init() {
        joueur1 = new Player(1);
        joueur2 = new Player(2);
        //ecouteur = new Ecouteur(joueur1,joueur2);
        plateau = new PlatCopy(nbrLignes,nbrColonnes);
        //System.out.println(plateau.toString() + "ToString");
        plateau.display();

        IAMariel mariel = new IAMariel();
        int[][] matrix = mariel.buildMatrix1();

        /*for (int i = 0; i < 6; i++){
            for (int j = 0; j < 7; j++){
                if (matrix[i][j] != 0){
                    System.err.println(i + "-" + j);
                }
                System.err.print(matrix[i][j]);
            }
            System.err.println();
        }
        System.err.println("\n");*/

        //plateau.initCopy(matrix);

        /*matrix = plateau.getP4();
        for (int i = 0; i < 6; i++){
            for (int j = 0; j < 7; j++){
                if (matrix[i][j] != 0){
                    System.out.println(i + "-" + j);
                }
                System.out.print(matrix[i][j]);
            }
            System.out.println();
        }
        System.out.println("\n");*/

        //plateau.display();

        plateau.addPoint(3, joueur1.getNumber());
        /*for (int i = 0; i < 6; i++){
            if (i >= 3){
                for (int j = 0; j < 3; j++){
                    plateau.addPoint(i, joueur1.getNumber());
                    plateau.addPoint(i, joueur2.getNumber());
                }
            }
            plateau.addPoint(i, joueur1.getNumber());
            plateau.addPoint(i, joueur2.getNumber());
        }
        plateau.display();*/
        jouerIA();
        plateau.addPoint(3, joueur1.getNumber());
        jouerIA();
        //plateau.jouerCoup(4, joueur1.getNumber());
        //jouerIA();
        //plateau.jouerCoup(1, joueur1.getNumber());
        //jouerIA();
        plateau.display();

        //plateau.display(); System.err.println("\n");
        /*plateau.jouerCoup(3, joueur2.getNumber());
        plateau.jouerCoup(3, joueur2.getNumber());
        plateau.jouerCoup(2, joueur1.getNumber());
        plateau.jouerCoup(4, joueur1.getNumber());
        plateau.display();*/
        //jouerIA();*/

        //plateau.afficher();
        //plateau.display();
        //plateau.jouerAB(joueur1);
        //plateau.afficher();
        //panelPlateau = new PanelPlateau(nbrLignes,nbrColonnes,ecouteur);
        //barreEtat = new PanelBarreEtat();
        //panelAffichage = new PanelAffichage();
        //this.add(panelPlateau,BorderLayout.WEST);
        //this.add(panelAffichage,BorderLayout.EAST);
        //this.add(barreEtat,BorderLayout.SOUTH);
        //ecouteur.setP4(plateau);
        //ecouteur.setPlateau(panelPlateau);
        //ecouteur.setBarreEtat(barreEtat);
        //ecouteur.setAffichage(panelAffichage);
    }
    public static synchronized void jouerIA(){
        //barreEtat.activerProgressBar();
        IATermine = false;
        if(joueur2.getType()==TypePlayer.MinMax){
            //plateau.jouerMinMax(joueur2);
            choice.add(plateau.MinMaxMove(joueur2));
        }else{
            plateau.MinMaxMove(joueur2);
        }
        try {
            Thread.currentThread();
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //Ecouteur.this.barreEtat.setNombreNoeud(plateau.getNbrNoeuds());
        //Ecouteur.this.panelPlateau.jouerCoup(Ecouteur.this.plateau.getDerniereColonne(), Ecouteur.this.joueurEncours.getNumero());

        IATermine = true;
        //changementJoueurEnCours();
        //notify();
    }
    public static void list(){
        System.out.println("Choice : " + choice.toString());
    }
}
