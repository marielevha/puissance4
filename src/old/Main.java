package old;

import ia.IAMariel;

public class Main {
    public static final int nbrLignes = 6;
    public static final int nbrColonnes = 7;
    private static PlateauCopy plateau;
    private static boolean IATermine;
    //private PanelPlateau panelPlateau;
    //private PanelBarreEtat barreEtat;
    //private PanelAffichage panelAffichage;
    //private Ecouteur ecouteur;
    private static Player joueur1,joueur2;
    public static void main(String[] args) {
        init();
    }
    private static void init() {
        joueur1 = new Player(1);
        joueur2 = new Player(2);
        //ecouteur = new Ecouteur(joueur1,joueur2);
        plateau = new PlateauCopy(nbrLignes,nbrColonnes);

        IAMariel mariel = new IAMariel();
        int[][] matrix = mariel.buildMatrix();

        plateau.initCopy(matrix);
        //plateau.display();

        //plateau.jouerCoup(3, joueur1.getNumber());
        plateau.display();
        //jouerIA();
        plateau.afficher();

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
            plateau.jouerMinMax(joueur2);
        }else{
            plateau.jouerAB(joueur2);
        }
        try {
            Thread.currentThread();
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //Ecouteur.this.barreEtat.setNombreNoeud(plateau.getNbrNoeuds());
        //Ecouteur.this.panelPlateau.jouerCoup(Ecouteur.this.plateau.getDerniereColonne(), Ecouteur.this.joueurEncours.getNumero());

        IATermine = true;
        //changementJoueurEnCours();
        //notify();
    }
}
