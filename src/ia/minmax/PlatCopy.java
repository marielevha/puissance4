package ia.minmax;

import engine.Plateau;
import old.Player;

import java.util.ArrayList;
import java.util.Collections;

public class PlatCopy {
    public final static int VIDE = 0;
    public final static int JOUEUR1 = 1;
    public final static int JOUEUR2 = 2;
    private int lastColumn;
    private int line;
    private int column;
    private int[][] p4;
    private int nbrNoeuds;
    private static Plateau plateau;

    /**
     * Constructor
     * @param l : nombre de ligne
     * @param c : nombre de colonne
     */
    public PlatCopy(int l, int c) {
        plateau = new Plateau(l, c);
        line = plateau.getLine();
        column = plateau.getColumn();
        //System.err.println(plateau.toString());
    }
    public void display(){
        //System.out.println(plateau.toString());
        for (int colonne = 0; colonne < column; colonne++) {
            System.out.print("-");
        }
        System.out.println();
        for(int ligne = (line -1); ligne > -1; ligne--) {
            for (int colonne = 0; colonne < column; colonne++) {
                if(plateau.getXY(colonne, ligne).getContent() == 0) {
                    System.out.print(" ");
                }else{
                    System.out.print(plateau.getXY(colonne, ligne).getContent());
                }
            }
            System.out.println();
        }
        for (int colonne = 0; colonne < column; colonne++){
            System.out.print("-");
        }
        plateau.toString();
        System.out.println();
    }

    /**
     * TotalPoints : compte le nombre de pions dans le plateau
     * @return Integer count
     */
    public int totalPoints() {
        int count = 0;
        for (int i = 0; i < line; i++) {
            for (int j = 0; j < column; j++) {
                if (plateau.getXY(j, i).getContent() != VIDE) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Evaluation : retourne une évaluation du plateau en fonction du nombre de pions alignés par les joueurs
     * @param player
     * @param depth
     * @return Integer evaluation
     */
    public int evaluation(Player player, int depth) {
        int winner = search4();
        if (winner == player.getNumber()) {
            return (100000 - (player.getDepth() - depth));
        }
        else if (winner == -1) {
            int playerAdverse = (player.getNumber() == 1) ? 2 : 1;
            /*if(player.getNumber() == 1){
                playerAdverse = 0;
            }else{
                playerAdverse = 1;
            }*/
            return (search(player.getNumber(), 3) * 100)
                    - (search(playerAdverse,3) * 100)
                    + (search(player.getNumber(),2) * 50)
                    - (search(playerAdverse,2) * 50);
        }
        else {
            return (-100000 + (player.getDepth() - depth));
        }
    }

    /**
     * CancelMove : annule un coup jouer en remplaçant le numéro du joueur par 0
     * @param column
     */
    private void cancelMove(int column) {
        int row = line -1;
        /*while(p4[row][column] == VIDE){
            row--;
        }
        this.p4[row][column] = VIDE;*/
        while (plateau.getXY(column, row).getContent() == VIDE) {
            row--;
        }
        plateau.getXY(column, row).setContent(VIDE);
    }

    /**
     * AddPoint : ajoute un pion du joueur au plateau dans la colonne indiquée
     * @param column
     * @param player
     * @return
     */
    public int addPoint(int column, int player) {
        for (int i = 0; i < line; i++) {
            if (plateau.getXY(column, i).getContent() == VIDE) {
                plateau.getXY(column, i).setContent(player);
                //System.out.println(i + "-" + column);
                return i;
            }
        }
        return -1;
    }

    /**
     * FullColumn :
     * @param column
     * @return
     */
    public boolean fullColumn(int column) {
        boolean available = false;
        /*for(int ligne = 0; ligne < line; ligne++) {
            if(p4[ligne][colonne] == VIDE){
                available = true;
            }
        }*/
        for (int i = 0; i < line; i++) {
            if (plateau.getXY(column, i).getContent() == VIDE) {
                available = true;
            }
        }
        return available;
    }

    /**
     * Full : vérifie si le plateau est plein
     * Si plein true, sinon false
     * @return boolean
     */
    public boolean full() {
        /*for(int j = 0; j< line; j++){
            for (int i = 0; i< column; i++){
                if(this.p4[j][i] ==VIDE){
                    return false;
                }
            }
        }*/
        for (int i = 0; i < line; i++) {
            for (int j = 0; j < column; j++) {
                if (plateau.getXY(j, i).getContent() == VIDE) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Search : retourne les alignement vertical, horizontal et les 2 diagonales d'un joueur
     * @param player
     * @param number
     * @return Integer count
     */
    public int search(int player, int number) {
        int count = 0;
        //horizontales
        for (int ligne = 0; ligne < line; ligne++) {
            count += searchAlignment(0, ligne, 1, 0,player,number);
        }
        //Diagonales
        for (int col = 0; col < column; col++) {
            count += searchAlignment(col, 0, 0, 1,player,number);
        }
        // Diagonales (cherche depuis la ligne du bas)
        for (int col = 0; col < column; col++) {

            // Premi�re diagonale ( / )
            count += searchAlignment(col, 0, 1, 1,player,number);
            // Deuxi�me diagonale ( \ )
            count += searchAlignment(col, 0, -1, 1,player,number);

        }

        // Diagonales (cherche depuis les colonnes gauches et droites)
        for (int ligne = 0; ligne < line; ligne++) {
            // Premi�re diagonale ( / )
            count += searchAlignment(0, ligne, 1, 1,player,number);


            // Deuxi�me diagonale ( \ )
            count += searchAlignment(column - 1, ligne, -1, 1,player,number);

        }
        return count;
    }
    /**
     * SearchAlignment : retourne le nombre d'alignement d'un joueur passé en paramètre
     * Selon la direction définie
     * @param oCol
     * @param oLine
     * @param dCol
     * @param dLine
     * @param player
     * @param number
     * @return Integer countAligns
     */
    private int searchAlignment(int oCol, int oLine, int dCol, int dLine, int player, int number) {

        int countPieces = 0;

        int countAligns = 0;
        int curCol = oCol;
        int curRow = oLine;
        int previous = -1;

        while ((curCol >= 0) && (curCol < column) && (curRow >= 0) && (curRow < line)) {
            if (plateau.getXY(curCol, curRow).getContent() != player) {
                if ((countPieces == number) && (previous == VIDE || plateau.getXY(curCol, curRow).getContent() == VIDE)){
                    countAligns++;
                }
                // Si la couleur change, on r�initialise le compteur
                countPieces = 0;
                previous = plateau.getXY(curCol, curRow).getContent();
            } else {
                // Sinon on l'incr�mente
                countPieces++;
            }

            // On passe � l'it�ration suivante
            curCol += dCol;
            curRow += dLine;
        }

        // Aucun alignement n'a �t� trouv�
        return countAligns;
    }

    /**
     * Search4 : balaillage vertical, horizontal et les 2 diagonales du plateau
     * Pour vérifier s'il y a un gagnant
     * @return Integer winner, sinon -1
     */
    public int search4() {
        int winner = -1;
        // V�rifie les horizontales ( - )
        for (int ligne = 0; ligne < line; ligne++) {
            winner = search4Alignment(0, ligne, 1, 0);
            if (winner != -1) {
                return winner ;
            }
        }

        // V�rifie les verticales ( � )
        for (int col = 0; col < column; col++) {
            winner = search4Alignment(col, 0, 0, 1);
            if (winner != -1) {
                return winner ;
            }
        }

        // Diagonales (cherche depuis la ligne du bas)
        for (int col = 0; col < column; col++) {

            // Premi�re diagonale ( / )
            winner = search4Alignment(col, 0, 1, 1);
            if (winner != -1) {
                return winner ;
            }
            winner = search4Alignment(col, 0, -1, 1);
            // Deuxi�me diagonale ( \ )
            if (winner != -1) {
                return winner ;
            }
        }

        // Diagonales (cherche depuis les colonnes gauches et droites)
        for (int ligne = 0; ligne < line; ligne++) {
            // Premi�re diagonale ( / )
            winner = search4Alignment(0, ligne, 1, 1);

            if (winner != -1) {
                return winner ;
            }
            // Deuxi�me diagonale ( \ )
            winner = search4Alignment(column - 1, ligne, -1, 1);
            if (winner != -1) {
                return winner ;
            }
        }

        // On n'a rien trouv�
        return -1;
    }

    /**
     * Search4Alignment : Vérifie s'ill y a un gagnant et retourne son numéro
     * Sinon retourne -1
     * @param oCol
     * @param oLine
     * @param dCol
     * @param dLine
     * @return Integer player
     */
    private int search4Alignment(int oCol, int oLine, int dCol, int dLine) {
        int player = VIDE;
        int count = 0;

        int curCol = oCol;
        int curRow = oLine;

        while ((curCol >= 0) && (curCol < column) && (curRow >= 0) && (curRow < line)) {
            //plateau.getXY(curCol, curRow);
            if (plateau.getXY(curCol, curRow).getContent() != player) {
                // Si la player change, on r�initialise le count
                player = plateau.getXY(curCol, curRow).getContent();
                //p4[curRow][curCol];
                count = 1;
            } else {
                // Sinon on l'incr�mente
                count++;
            }

            // On sort lorsque le count atteint 4
            if ((player != VIDE) && (count == 4)) {
                return player;
            }

            // On passe � l'it�ration suivante
            curCol += dCol;
            curRow += dLine;
        }

        // Aucun alignement n'a �t� trouv�
        return -1;
    }

    /**
     * Algorithm MinMax : retourne la colonne à jouer
     * @param player
     * @return column
     */
    public synchronized int MinMaxMove(Player player) {
        if(totalPoints()>1) {
            this.nbrNoeuds = 0;
            int max = -10000000;
            ArrayList<Integer> choices = new ArrayList<Integer>();
            int col = -1;
            int depth = player.getDepth();

            ///ArrayList<PanelMiniPlateau> miniPlateaux = new ArrayList<PanelMiniPlateau>();
            for(int i = 0; i< column; i++){
                if(fullColumn(i)) {
                    this.addPoint(i, player.getNumber());
                    int evaluation = this.min((depth - 1), player.getNumber(), player);

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
                    //System.err.println("Evaluation < max :" + max);

                    //System.err.println(choices.get(0));
                    this.cancelMove(i);
                }
            }
            Collections.shuffle(choices);
            //System.out.println(choices);
            col = choices.get(0);
            this.addPoint(col, player.getNumber());
            this.lastColumn = col;
            //System.err.println(this.lastColumn +" Nombre de noeud parcourus : "+nbrNoeuds);
            System.out.println();
            //display();
            return this.lastColumn;
        }
        else {
            this.addPoint((column / 2), player.getNumber());
            this.lastColumn = (column / 2);
            this.nbrNoeuds=0;
            //display();
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
        if(profondeur == 0 || full() || this.search4() != -1){
            int eval = this.evaluation(currentPlayer, profondeur);
            return eval;
        }
        int max = -10000000;
        for(int i = 0; i < column; i++){
            if(fullColumn(i)){
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
        if(profondeur ==0 || full() || this.search4() != -1){
            int eval = this.evaluation(currentPlayer,profondeur);
            return eval;
        }
        int min = 10000000;
        for(int i = 0; i < column; i++){
            if(fullColumn(i)){
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

}
