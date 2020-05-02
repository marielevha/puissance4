package old;

import engine.Plateau;

import java.util.ArrayList;
import java.util.Collections;

public class PlateauCopy extends Plateau {
    public final static int VIDE = 0;
    public final static int JOUEUR1 = 1;
    public final static int JOUEUR2 = 2;
    private int derniereColonne;
    private int nbrLigne;
    private int nbrColonne;
    private int[][] p4;
    private int nbrNoeuds;
    private static Plateau plateau;

    public int[][] getP4() {
        return p4;
    }

    public PlateauCopy(String string) {
        super(string, 0);
        this.nbrLigne = this.getLineColumn()[0];
        this.nbrColonne = this.getLineColumn()[1];
        this.p4 = new int[nbrLigne][nbrColonne];
        this.nbrNoeuds = 0;
    }

    public PlateauCopy(int nbrLigne, int nbrColonne) {
        this.nbrColonne = nbrColonne;
        this.nbrLigne = nbrLigne;
        this.p4 = new int[nbrLigne][nbrColonne];
        this.nbrNoeuds = 0;
        this.init();
        //initCopy();
    }
    public void initCopy(int [][] matrix) {
        for(int j=0 ; j<nbrLigne;j++){
            for (int i=0;i<nbrColonne;i++){
                this.p4[j][i] = matrix[j][i];
            }
        }
    }
    private void init() {
        for(int j=0 ; j<nbrLigne;j++){
            for (int i=0;i<nbrColonne;i++){
                this.p4[j][i] = VIDE;
            }
        }
    }
    public void display(){
        //Read
        for (int i = 0; i < nbrLigne; i++){
            for (int j = 0; j < nbrColonne; j++){
                System.err.print(p4[i][j]);
            }
            System.err.println();
        }
    }



    public void afficher(){
        //this.toString();
        for (int colonne=0;colonne<nbrColonne;colonne++){
            System.out.print("-");
        }
        System.out.println();
        for(int ligne=nbrLigne-1 ; ligne>-1;ligne--){
            for (int colonne=0;colonne<nbrColonne;colonne++){
                if(p4[ligne][colonne]==0){
                    System.out.print(" ");
                }else{
                    System.out.print(p4[ligne][colonne]);
                }

            }
            System.out.println();
        }
        for (int colonne=0;colonne<nbrColonne;colonne++){
            System.out.print("-");
        }
        System.out.println();
    }
    public int jouerCoup(int colonne,int joueur){

        for(int ligne=0; ligne<nbrLigne ;ligne++){
            if(p4[ligne][colonne]==VIDE){
                p4[ligne][colonne] = joueur;
                System.out.println(ligne + "-" + colonne);
                return ligne;
            }
        }
        return -1;
    }
    public boolean placeDispo(int colonne){
        boolean dispo = false;
        for(int ligne=0; ligne<nbrLigne ;ligne++){
            if(p4[ligne][colonne]==VIDE){
                dispo = true;
            }
        }
        return dispo;
    }
    public int cherche(int joueur,int nombre){
        int compteur = 0;
        //horizontales
        for (int ligne = 0; ligne < nbrLigne; ligne++) {
            compteur += chercheAlignes(0, ligne, 1, 0,joueur,nombre);
        }
        //Diagonales
        for (int col = 0; col < nbrColonne ;col++) {
            compteur += chercheAlignes(col, 0, 0, 1,joueur,nombre);
        }
        // Diagonales (cherche depuis la ligne du bas)
        for (int col = 0; col < nbrColonne; col++) {

            // Premi�re diagonale ( / )
            compteur += chercheAlignes(col, 0, 1, 1,joueur,nombre);
            // Deuxi�me diagonale ( \ )
            compteur += chercheAlignes(col, 0, -1, 1,joueur,nombre);

        }

        // Diagonales (cherche depuis les colonnes gauches et droites)
        for (int ligne = 0; ligne < nbrLigne; ligne++) {
            // Premi�re diagonale ( / )
            compteur += chercheAlignes(0, ligne, 1, 1,joueur,nombre);


            // Deuxi�me diagonale ( \ )
            compteur += chercheAlignes(nbrColonne - 1, ligne, -1, 1,joueur,nombre);

        }
        return compteur;
    }
    private int chercheAlignes(int oCol, int oLigne, int dCol, int dLigne,int joueur,int nombre) {

        int compteurJeton = 0;

        int compteurAlignes = 0;
        int curCol = oCol;
        int curRow = oLigne;
        int precedent=-1;

        while ((curCol >= 0) && (curCol < nbrColonne) && (curRow >= 0) && (curRow < nbrLigne)) {
            if (p4[curRow][curCol] != joueur) {

                if ((compteurJeton == nombre)&&(precedent==VIDE||p4[curRow][curCol]==VIDE)){
                    compteurAlignes++;
                }

                // Si la couleur change, on r�initialise le compteur
                compteurJeton = 0;
                precedent = p4[curRow][curCol];
            } else {
                // Sinon on l'incr�mente
                compteurJeton++;
            }

            // On passe � l'it�ration suivante
            curCol += dCol;
            curRow += dLigne;
        }

        // Aucun alignement n'a �t� trouv�
        return compteurAlignes;
    }

    public int cherche4() {
        int vainqueur =-1;
        // V�rifie les horizontales ( - )
        for (int ligne = 0; ligne < nbrLigne; ligne++) {
            vainqueur = cherche4alignes(0, ligne, 1, 0);
            if (vainqueur!=-1) {
                return vainqueur ;
            }
        }

        // V�rifie les verticales ( � )
        for (int col = 0; col < nbrColonne ;col++) {
            vainqueur = cherche4alignes(col, 0, 0, 1);
            if (vainqueur!=-1) {
                return vainqueur ;
            }
        }

        // Diagonales (cherche depuis la ligne du bas)
        for (int col = 0; col < nbrColonne; col++) {

            // Premi�re diagonale ( / )
            vainqueur = cherche4alignes(col, 0, 1, 1);
            if (vainqueur!=-1) {
                return vainqueur ;
            }
            vainqueur = cherche4alignes(col, 0, -1, 1);
            // Deuxi�me diagonale ( \ )
            if (vainqueur!=-1) {
                return vainqueur ;
            }
        }

        // Diagonales (cherche depuis les colonnes gauches et droites)
        for (int ligne = 0; ligne < nbrLigne; ligne++) {
            // Premi�re diagonale ( / )
            vainqueur = cherche4alignes(0, ligne, 1, 1);

            if (vainqueur!=-1) {
                return vainqueur ;
            }
            // Deuxi�me diagonale ( \ )
            vainqueur = cherche4alignes(nbrColonne - 1, ligne, -1, 1);
            if (vainqueur!=-1) {
                return vainqueur ;
            }
        }

        // On n'a rien trouv�
        return -1;
    }
    private int cherche4alignes(int oCol, int oLigne, int dCol, int dLigne) {
        int couleur = VIDE;
        int compteur = 0;

        int curCol = oCol;
        int curRow = oLigne;

        while ((curCol >= 0) && (curCol < nbrColonne) && (curRow >= 0) && (curRow < nbrLigne)) {
            if (p4[curRow][curCol] != couleur) {
                // Si la couleur change, on r�initialise le compteur
                couleur = p4[curRow][curCol];
                compteur = 1;
            } else {
                // Sinon on l'incr�mente
                compteur++;
            }

            // On sort lorsque le compteur atteint 4
            if ((couleur != VIDE) && (compteur == 4)) {
                return couleur;
            }

            // On passe � l'it�ration suivante
            curCol += dCol;
            curRow += dLigne;
        }

        // Aucun alignement n'a �t� trouv�
        return -1;
    }
    public boolean isTermine(){
        for(int j=0 ; j<nbrLigne;j++){
            for (int i=0;i<nbrColonne;i++){
                if(this.p4[j][i] ==VIDE){
                    return false;
                }
            }
        }
        return true;
    }

    public int getColonne() {
        return nbrColonne;
    }

    public void rejouer() {
        this.init();
    }
    public int getNbrJeton(){
        int compteur = 0;
        for(int j=0 ; j<nbrLigne;j++){
            for (int i=0;i<nbrColonne;i++){
                if(this.p4[j][i]!=VIDE){
                    compteur++;
                }
            }
        }
        return compteur;
    }
    public synchronized int jouerAB(Player player/*, PanelAffichage panelAffichage*/){
        if(getNbrJeton()>1){

            this.nbrNoeuds = 0;
            int alpha =  -10000000;
            int beta = 10000000;
            int colonne=-1;
            int profondeur=player.getLevel();
            boolean continuer = true;
            ArrayList<Integer> list = new ArrayList<Integer>();
            for(int i= 0; i<nbrColonne;i++){
                list.add(i);
            }
            Collections.shuffle(list);
            ///ArrayList<PanelMiniPlateau> miniPlateaux = new ArrayList<PanelMiniPlateau>();
            for(int i=0;i<nbrColonne && continuer;i++){
                int colonneATester = (int) list.get(i);
                if(placeDispo(colonneATester)){
                    this.jouerCoup(colonneATester,player.getNumber());
                    int evaluation = this.minAB(profondeur-1,player.getNumber(),player,alpha,beta);



                    if(evaluation>alpha){
                        alpha = evaluation;
                        colonne =colonneATester;
                        System.out.println(player+" peut joue pos"+colonneATester+" profondeur : "+profondeur+" eval = "+evaluation);
                        this.afficher();
                        ///miniPlateaux.add(new PanelMiniPlateau(p4,colonneATester,evaluation));
                    }else{
                        System.out.println(player+" peut joue pos"+colonneATester+" profondeur : "+profondeur+" ELAGAGE reaslisee");
                        ///miniPlateaux.add(new PanelMiniPlateau(p4,colonneATester));
                    }


                    this.annulerCoup(colonneATester);
                    if(alpha>=beta){
                        continuer = false;
                    }
                }
            }
            ///panelAffichage.ajoutPanel(miniPlateaux);
            this.jouerCoup(colonne, player.getNumber());
            System.out.println(colonne);
            this.derniereColonne = colonne;
            System.out.println("Nombre de noeud parcourus : "+nbrNoeuds);
            System.out.println();
            return this.derniereColonne;
        }else{
            this.jouerCoup(nbrColonne/2, player.getNumber());
            this.derniereColonne = nbrColonne/2;
            this.nbrNoeuds=0;
            return this.derniereColonne;
        }
    }


    private int maxAB(int profondeur,int joueur,Player joueurEnCours,int alpha,int beta) {

        this.nbrNoeuds++;
        if(joueur==1){
            joueur = 2;
        }else{
            joueur = 1;
        }
        if(profondeur==0 || this.isTermine()||this.cherche4()!=-1){
            int eval = this.eval2(joueurEnCours,profondeur);
            return eval;
        }
        for(int i=0;i<nbrColonne;i++){
            if(placeDispo(i)){
                this.jouerCoup(i,joueur);
                int evaluation = this.minAB(profondeur-1, joueur,joueurEnCours, alpha, beta);
                //System.out.println(joueur+" peut joue pos"+i+" profondeur : "+profondeur+" eval = "+evaluation);

                if(evaluation>alpha){
                    alpha = evaluation;
                }
                this.annulerCoup(i);
                if(alpha>=beta){
                    //System.out.println("Elagage Max profondeur "+profondeur);
                    return beta;
                }
            }
        }
        return alpha;
    }
    private int minAB(int profondeur, int joueur,Player joueurEnCours,int alpha,int beta) {

        this.nbrNoeuds++;
        if(joueur==1){
            joueur = 2;
        }else{
            joueur = 1;
        }
        if(profondeur==0 || this.isTermine()||this.cherche4()!=-1){
            int eval = this.eval2(joueurEnCours,profondeur);
            return eval;
        }
        for(int i=0;i<nbrColonne ;i++){
            if(placeDispo(i)){
                this.jouerCoup(i,joueur);
                int evaluation = this.maxAB(profondeur-1, joueur,joueurEnCours, alpha, beta);
                //System.out.println(joueur+" peut joue pos"+i+" profondeur : "+profondeur+" eval = "+evaluation);

                if(evaluation<beta){
                    beta = evaluation;
                }
                this.annulerCoup(i);
                if(beta<=alpha){
                    //System.out.println("Elagage Min profondeur "+profondeur);
                    return alpha;
                }
            }

        }
        return beta;
    }

    public synchronized int jouerMinMax(Player joueur/*, PanelAffichage panelAffichage*/){
        if(getNbrJeton()>1){
            this.nbrNoeuds = 0;
            int max = -10000000;
            ArrayList<Integer> choix = new ArrayList<Integer>();
            int colonne=-1;
            int profondeur=joueur.getLevel();

            ///ArrayList<PanelMiniPlateau> miniPlateaux = new ArrayList<PanelMiniPlateau>();
            for(int i=0;i<nbrColonne ;i++){
                if(placeDispo(i)){
                    this.jouerCoup(i,joueur.getNumber());
                    int evaluation = this.min(profondeur-1,joueur.getNumber(),joueur);

                    System.out.println("Joueur "+joueur+" a joue "+i+" eval = "+evaluation);
                    ///miniPlateaux.add(new PanelMiniPlateau(p4,i,evaluation));


                    if(evaluation>max){
                        max = evaluation;
                        choix.clear();
                        choix.add(i);
                        //System.err.println("Evaluation > max :" + max);
                    }
                    else if(evaluation==max){
                        choix.add(i);
                        //System.err.println("Evaluation == max :" + max);
                    }
                    else {
                        //System.err.println("Evaluation < max :" + max);
                    }
                    //System.err.println(choix.get(0));
                    this.annulerCoup(i);
                }
            }
            ///panelAffichage.ajoutPanel(miniPlateaux);
            Collections.shuffle(choix);
            System.out.println(choix);
            colonne = choix.get(0);
            this.jouerCoup(colonne, joueur.getNumber());
            this.derniereColonne = colonne;
            //System.err.println(this.derniereColonne +" Nombre de noeud parcourus : "+nbrNoeuds);
            System.out.println();
            return this.derniereColonne;
        }
        else {
            this.jouerCoup(nbrColonne/2, joueur.getNumber());
            this.derniereColonne = nbrColonne/2;
            this.nbrNoeuds=0;
            return this.derniereColonne;
        }
    }

    private int max(int profondeur,int joueur,Player joueurEnCours) {
        this.nbrNoeuds++;
        if(joueur==1){
            joueur = 2;
        }else{
            joueur = 1;
        }
        if(profondeur==0 || this.isTermine()||this.cherche4()!=-1){
            int eval = this.eval2(joueurEnCours,profondeur);
            return eval;
        }
        int max = -10000000;
        for(int i=0;i<nbrColonne ;i++){
            if(placeDispo(i)){
                this.jouerCoup(i,joueur);
                int evaluation = this.min(profondeur-1,joueur,joueurEnCours);
                //System.out.println("Joueur "+joueur+" a joue "+i+" eval = "+evaluation);

                if(evaluation>max){
                    max = evaluation;
                }
                this.annulerCoup(i);
            }

        }
        return max;
    }
    private int min(int profondeur, int joueur,Player joueurEnCours) {
        this.nbrNoeuds++;
        if(joueur==1){
            joueur = 2;
        }else{
            joueur = 1;
        }
        if(profondeur==0 || this.isTermine()||this.cherche4()!=-1){
            int eval = this.eval2(joueurEnCours,profondeur);
            return eval;
        }
        int min = 10000000;
        for(int i=0;i<nbrColonne ;i++){
            if(placeDispo(i)){
                this.jouerCoup(i,joueur);
                int evaluation = this.max(profondeur-1,joueur,joueurEnCours);
                //System.out.println("Joueur "+joueur+" a joue "+i+" eval = "+evaluation);

                if(evaluation<min){
                    min = evaluation;
                }
                this.annulerCoup(i);
            }

        }
        return min;
    }
    private int eval(Player joueur,int profondeur) {
        int vainqueur = this.cherche4();
        //System.out.println("Evalutaion joueur "+joueur);
        //this.afficher();
        if(vainqueur==joueur.getNumber()){
            return (1000-(joueur.getLevel()-profondeur));
        }else if (vainqueur== -1){
            return 0;
        }else{
            return (-1000+(joueur.getLevel()-profondeur));
        }
    }
    private int eval2(Player joueur,int profondeur) {
        int vainqueur = this.cherche4();

        //System.out.println("Evalutaion joueur "+joueur);
        //this.afficher();

        if(vainqueur==joueur.getNumber()){
            //System.out.println("Vainqueur");
            return 100000-(joueur.getLevel()-profondeur);
        }else if (vainqueur== -1){
            //System.out.println("Nul");
            int adversaire;
            if(joueur.getNumber() == 1){
                adversaire = 0;
            }else{
                adversaire =1;
            }
            int eval = cherche(joueur.getNumber(),3)*100-cherche(adversaire,3)*100+cherche(joueur.getNumber(),2)*50-cherche(adversaire,2)*50;
            //System.out.println("Evaluation : "+eval);
            return eval;

        }else{
            //System.out.println("Perdant");
            return -100000+(joueur.getLevel()-profondeur);
        }

    }
    private void annulerCoup(int i) {
        int ligne =nbrLigne-1;
        while(p4[ligne][i]==VIDE){
            ligne--;
        }
        this.p4[ligne][i] = VIDE;

    }
    public int getDerniereColonne(){
        return derniereColonne;
    }
    public int getNbrNoeuds(){
        return nbrNoeuds;
    }
}
