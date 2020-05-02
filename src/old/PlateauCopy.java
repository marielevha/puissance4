package old;

import engine.Plateau;

import java.util.ArrayList;
import java.util.Collections;

public class PlateauCopy {
    public final static int VIDE = 0;
    public final static int JOUEUR1 = 1;
    public final static int JOUEUR2 = 2;
    private int lastColumn;
    private int line;
    private int column;
    private int[][] p4;
    private int nbrNoeuds;
    private static Plateau plateau;

    public int[][] getP4() {
        return p4;
    }

    public PlateauCopy(String string) {
        /*super(string, 0);
        this.line = this.getLineColumn()[0];
        this.column = this.getLineColumn()[1];
        this.p4 = new int[line][column];
        this.nbrNoeuds = 0;*/
    }

    /**
     * Constructor
     * @param line
     * @param column
     */
    public PlateauCopy(int line, int column) {
        this.column = column;
        this.line = line;
        this.p4 = new int[line][column];
        this.nbrNoeuds = 0;
        this.init();
        //initCopy();
    }
    public void initCopy(int [][] matrix) {
        for(int j = 0; j< line; j++){
            for (int i = 0; i< column; i++){
                this.p4[j][i] = matrix[j][i];
            }
        }
    }

    /**
     * Init : initialise la matrice correspondante au plateau du jeu
     */
    private void init() {
        for(int j = 0; j< line; j++){
            for (int i = 0; i< column; i++){
                this.p4[j][i] = VIDE;
            }
        }
    }
    public void display1(){
        //Read
        for (int i = 0; i < line; i++){
            for (int j = 0; j < column; j++){
                System.err.print(p4[i][j]);
            }
            System.err.println();
        }
    }


    /**
     * Display : affiche le plateau
     */
    public void display(){
        //this.toString();
        for (int colonne = 0; colonne< column; colonne++){
            System.out.print("-");
        }
        System.out.println();
        for(int ligne = line -1; ligne>-1; ligne--){
            for (int colonne = 0; colonne< column; colonne++){
                if(p4[ligne][colonne]==0){
                    System.out.print(" ");
                }else{
                    System.out.print(p4[ligne][colonne]);
                }

            }
            System.out.println();
        }
        for (int colonne = 0; colonne< column; colonne++){
            System.out.print("-");
        }
        System.out.println();
    }

    /**
     * Add Point : ajoute un pion au plateau
     * @param column
     * @param player
     * @return
     */
    public int addPoint(int column, int player){

        for(int ligne = 0; ligne< line; ligne++){
            if(p4[ligne][column]==VIDE){
                p4[ligne][column] = player;
                System.out.println(ligne + "-" + column);
                return ligne;
            }
        }
        return -1;
    }

    /**
     *
     * @param colonne
     * @return
     */
    public boolean placeDispo(int colonne){
        boolean dispo = false;
        for(int ligne = 0; ligne< line; ligne++){
            if(p4[ligne][colonne]==VIDE){
                dispo = true;
            }
        }
        return dispo;
    }
    public int cherche(int joueur,int nombre){
        int compteur = 0;
        //horizontales
        for (int ligne = 0; ligne < line; ligne++) {
            compteur += chercheAlignes(0, ligne, 1, 0,joueur,nombre);
        }
        //Diagonales
        for (int col = 0; col < column; col++) {
            compteur += chercheAlignes(col, 0, 0, 1,joueur,nombre);
        }
        // Diagonales (cherche depuis la ligne du bas)
        for (int col = 0; col < column; col++) {

            // Premi�re diagonale ( / )
            compteur += chercheAlignes(col, 0, 1, 1,joueur,nombre);
            // Deuxi�me diagonale ( \ )
            compteur += chercheAlignes(col, 0, -1, 1,joueur,nombre);

        }

        // Diagonales (cherche depuis les colonnes gauches et droites)
        for (int ligne = 0; ligne < line; ligne++) {
            // Premi�re diagonale ( / )
            compteur += chercheAlignes(0, ligne, 1, 1,joueur,nombre);


            // Deuxi�me diagonale ( \ )
            compteur += chercheAlignes(column - 1, ligne, -1, 1,joueur,nombre);

        }
        return compteur;
    }
    private int chercheAlignes(int oCol, int oLigne, int dCol, int dLigne,int joueur,int nombre) {

        int compteurJeton = 0;

        int compteurAlignes = 0;
        int curCol = oCol;
        int curRow = oLigne;
        int precedent=-1;

        while ((curCol >= 0) && (curCol < column) && (curRow >= 0) && (curRow < line)) {
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
        for (int ligne = 0; ligne < line; ligne++) {
            vainqueur = cherche4alignes(0, ligne, 1, 0);
            if (vainqueur!=-1) {
                return vainqueur ;
            }
        }

        // V�rifie les verticales ( � )
        for (int col = 0; col < column; col++) {
            vainqueur = cherche4alignes(col, 0, 0, 1);
            if (vainqueur!=-1) {
                return vainqueur ;
            }
        }

        // Diagonales (cherche depuis la ligne du bas)
        for (int col = 0; col < column; col++) {

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
        for (int ligne = 0; ligne < line; ligne++) {
            // Premi�re diagonale ( / )
            vainqueur = cherche4alignes(0, ligne, 1, 1);

            if (vainqueur!=-1) {
                return vainqueur ;
            }
            // Deuxi�me diagonale ( \ )
            vainqueur = cherche4alignes(column - 1, ligne, -1, 1);
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

        while ((curCol >= 0) && (curCol < column) && (curRow >= 0) && (curRow < line)) {
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
        for(int j = 0; j< line; j++){
            for (int i = 0; i< column; i++){
                if(this.p4[j][i] ==VIDE){
                    return false;
                }
            }
        }
        return true;
    }

    public int getColonne() {
        return column;
    }

    public void rejouer() {
        this.init();
    }

    /**
     * Total points : compte le nombre de pions du plateau
     * @return count
     */
    public int totalPoints(){
        int count = 0;
        for(int i = 0; i < line; i++){
            for (int j = 0; j < column; j++){
                if(this.p4[i][j]!= VIDE){
                    count++;
                }
            }
        }
        return count;
    }
    public synchronized int AlphaBetaMove(Player player){
        if(totalPoints()>1){

            this.nbrNoeuds = 0;
            int alpha =  -10000000;
            int beta = 10000000;
            int colonne=-1;
            int profondeur=player.getLevel();
            boolean continuer = true;
            ArrayList<Integer> list = new ArrayList<Integer>();
            for(int i = 0; i< column; i++){
                list.add(i);
            }
            Collections.shuffle(list);
            ///ArrayList<PanelMiniPlateau> miniPlateaux = new ArrayList<PanelMiniPlateau>();
            for(int i = 0; i< column && continuer; i++){
                int colonneATester = (int) list.get(i);
                if(placeDispo(colonneATester)){
                    this.addPoint(colonneATester,player.getNumber());
                    int evaluation = this.minAB(profondeur-1,player.getNumber(),player,alpha,beta);



                    if(evaluation>alpha){
                        alpha = evaluation;
                        colonne =colonneATester;
                        System.out.println(player+" peut joue pos"+colonneATester+" profondeur : "+profondeur+" eval = "+evaluation);
                        this.display();
                        ///miniPlateaux.add(new PanelMiniPlateau(p4,colonneATester,evaluation));
                    }else{
                        System.out.println(player+" peut joue pos"+colonneATester+" profondeur : "+profondeur+" ELAGAGE reaslisee");
                        ///miniPlateaux.add(new PanelMiniPlateau(p4,colonneATester));
                    }


                    this.cancelMove(colonneATester);
                    if(alpha>=beta){
                        continuer = false;
                    }
                }
            }
            ///panelAffichage.ajoutPanel(miniPlateaux);
            this.addPoint(colonne, player.getNumber());
            System.out.println(colonne);
            this.lastColumn = colonne;
            System.out.println("Nombre de noeud parcourus : "+nbrNoeuds);
            System.out.println();
            return this.lastColumn;
        }else{
            this.addPoint(column /2, player.getNumber());
            this.lastColumn = column /2;
            this.nbrNoeuds=0;
            return this.lastColumn;
        }
    }
    private int maxAB(int profondeur,int player,Player currentPlayer,int alpha,int beta) {

        this.nbrNoeuds++;
        if(player == 1){
            player = 2;
        }else{
            player = 1;
        }
        if(profondeur==0 || this.isTermine()||this.cherche4()!=-1){
            int eval = this.evaluation(currentPlayer,profondeur);
            return eval;
        }
        for(int i = 0; i< column; i++){
            if(placeDispo(i)){
                this.addPoint(i,player);
                int evaluation = this.minAB(profondeur-1, player,currentPlayer, alpha, beta);
                //System.out.println(player+" peut joue pos"+i+" profondeur : "+profondeur+" eval = "+evaluation);

                if(evaluation>alpha){
                    alpha = evaluation;
                }
                this.cancelMove(i);
                if(alpha>=beta){
                    //System.out.println("Elagage Max profondeur "+profondeur);
                    return beta;
                }
            }
        }
        return alpha;
    }
    private int minAB(int profondeur, int player,Player currentPlayer,int alpha,int beta) {

        this.nbrNoeuds++;
        if(player==1){
            player = 2;
        }else{
            player = 1;
        }
        if(profondeur==0 || this.isTermine()||this.cherche4()!=-1){
            int eval = this.evaluation(currentPlayer,profondeur);
            return eval;
        }
        for(int i = 0; i< column; i++){
            if(placeDispo(i)){
                this.addPoint(i,player);
                int evaluation = this.maxAB(profondeur-1, player,currentPlayer, alpha, beta);
                //System.out.println(player+" peut joue pos"+i+" profondeur : "+profondeur+" eval = "+evaluation);

                if(evaluation<beta){
                    beta = evaluation;
                }
                this.cancelMove(i);
                if(beta<=alpha){
                    //System.out.println("Elagage Min profondeur "+profondeur);
                    return alpha;
                }
            }

        }
        return beta;
    }

    /**
     * Algorithm MinMax : retourne la colonne à jouer
     * @param player
     * @return column
     */
    public synchronized int MinMaxMove(Player player){
        if(totalPoints()>1){
            this.nbrNoeuds = 0;
            int max = -10000000;
            ArrayList<Integer> choices = new ArrayList<Integer>();
            int col = -1;
            int profondeur = player.getLevel();

            ///ArrayList<PanelMiniPlateau> miniPlateaux = new ArrayList<PanelMiniPlateau>();
            for(int i = 0; i< column; i++){
                if(placeDispo(i)) {
                    this.addPoint(i, player.getNumber());
                    int evaluation = this.min((profondeur - 1), player.getNumber(), player);

                    //System.out.println("Joueur " + player + " a joue " + i + " eval = " + evaluation);
                    ///miniPlateaux.add(new PanelMiniPlateau(p4,i,evaluation));

                    if(evaluation > max){
                        max = evaluation;
                        choices.clear();
                        choices.add(i);
                        //System.err.println("Evaluation > max :" + max);
                    }
                    else if(evaluation == max){
                        choices.add(i);
                        //System.err.println("Evaluation == max :" + max);
                    }
                    else {
                        //System.err.println("Evaluation < max :" + max);
                    }
                    //System.err.println(choices.get(0));
                    this.cancelMove(i);
                }
            }
            Collections.shuffle(choices);
            System.out.println(choices);
            col = choices.get(0);
            this.addPoint(col, player.getNumber());
            this.lastColumn = col;
            //System.err.println(this.lastColumn +" Nombre de noeud parcourus : "+nbrNoeuds);
            System.out.println();
            return this.lastColumn;
        }
        else {
            this.addPoint(column /2, player.getNumber());
            this.lastColumn = column /2;
            this.nbrNoeuds=0;
            return this.lastColumn;
        }
    }

    /**
     * Partie Max de MinMax
     * @param profondeur
     * @param player
     * @param currentPlayer
     * @return max
     */
    private int max(int profondeur, int player, Player currentPlayer){
        this.nbrNoeuds++;
        /*if(player == 1){
            player = 2;
        }else{
            player = 1;
        }*/
        player = (player == 1) ? 2 : 1;
        if(profondeur == 0 || this.isTermine() || this.cherche4() != -1){
            int eval = this.evaluation(currentPlayer, profondeur);
            return eval;
        }
        int max = -10000000;
        for(int i = 0; i < column; i++){
            if(placeDispo(i)){
                this.addPoint(i, player);
                int evaluation = this.min((profondeur - 1), player, currentPlayer);
                //System.out.println("Joueur "+player+" a joue "+i+" eval = "+evaluation);

                if(evaluation > max){
                    max = evaluation;
                }
                this.cancelMove(i);
            }
        }
        return max;
    }

    /**
     * Partie Min de MinMax
     * @param profondeur
     * @param player
     * @param currentPlayer
     * @return min
     */
    private int min(int profondeur, int player, Player currentPlayer) {
        this.nbrNoeuds++;
        /*if(player == 1){
            player = 2;
        }else{
            player = 1;
        }*/
        player = (player == 1) ? 2 : 1;
        if(profondeur ==0 || this.isTermine() || this.cherche4() != -1){
            int eval = this.evaluation(currentPlayer,profondeur);
            return eval;
        }
        int min = 10000000;
        for(int i = 0; i < column; i++){
            if(placeDispo(i)){
                this.addPoint(i, player);
                int evaluation = this.max((profondeur - 1), player, currentPlayer);
                //System.out.println("Joueur "+player+" a joue "+i+" eval = "+evaluation);

                if(evaluation < min){
                    min = evaluation;
                }
                this.cancelMove(i);
            }

        }
        return min;
    }

    private int eval(Player joueur, int profondeur) {
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

    /**
     * Evaluation : ///
     * @param player
     * @param profondeur
     * @return Integer evaluation
     */
    private int evaluation(Player player, int profondeur) {
        int winner = this.cherche4();

        //System.out.println("Evalutaion player "+player);
        //this.afficher();

        if(winner == player.getNumber()){
            //System.out.println("Vainqueur");
            return (100000 - (player.getLevel() - profondeur));
        }else if (winner== -1){
            //System.out.println("Nul");
            int playerAdverse = (player.getNumber() == 1) ? 2 : 1;
            if(player.getNumber() == 1){
                playerAdverse = 0;
            }else{
                playerAdverse = 1;
            }
            int evaluation = (cherche(player.getNumber(), 3) * 100)
                           - (cherche(playerAdverse,3) * 100)
                           + (cherche(player.getNumber(),2) * 50)
                           - (cherche(playerAdverse,2) * 50);
            //System.out.println("Evaluation : "+evaluation);
            return evaluation;

        }else{
            //System.out.println("Perdant");
            return (-100000 + (player.getLevel() - profondeur));
        }

    }

    /**
     * Annuler Coup : Vide la case indiquée
     * @param column
     */
    private void cancelMove(int column) {
        int ligne = line -1;
        while(p4[ligne][column] == VIDE){
            ligne--;
        }
        this.p4[ligne][column] = VIDE;

    }
    public int getLastColumn(){
        return lastColumn;
    }
    public int getNbrNoeuds(){
        return nbrNoeuds;
    }
}
