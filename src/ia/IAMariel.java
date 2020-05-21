package ia;

import engine.Plateau;
import ia.minmax.PlatCopy;
import old.PlateauCopy;
import old.Player;

import java.util.Random;

public class IAMariel extends Player{
    private static Random random = new Random();
    private static int MAX_ALIGN = 4, line = 6, column = 7;
    private static int VALUE_GAME_RED = 0, VALUE_GAME_YELLOW = 0, POSITION_WIN = -1, POSITION_BLOCK = -1, POSITION_DEFAULT = -1;
    private static int cmp = 0;
    private static int [][] matrix;
    private static int frontCount = 0, afterCount = 0;
    private static boolean test = true;

    private static Plateau plateau;
    private int level;
    private int number = 2;
    private Type type;
    private static PlateauCopy plateauCopy;
    private static PlatCopy plat;
    private static Player player;// = new Player(2);

    public IAMariel() {
        super(2);
        String string = "6x7-" +
                "0000000" +
                "0000000" +
                "0000000" +
                "0000000" +
                "0000000" +
                "0000000";
        plateau = new Plateau(string, 1);
    }

    public IAMariel(int number, int level) {
        super(number);
        this.level = level;
        this.setTypePlayer("IA");
        if (level == 5) {
            //this.type = Type.MIN_MAX;
            //plateauCopy = new PlateauCopy(line,column);
            plat = new PlatCopy(line, column);
        }
        else if (level == 6) {
            //this.type = Type.ALPHA_BETA;
            plateauCopy = new PlateauCopy(line,column);
        }
    }

    /***
     * Meilleur Coupe
     * @param string
     * @return
     */
    public int bestMove(String string, int player){
        IAMariel.player = new Player(player);
        plateau = new Plateau(string, 0);
        switch (level) {
            case 1 : {
                return levelOneMove();
            }
            case 2 : {
                return levelTwoMove();
            }
            case 3 : {
                return levelThreeMove();
            }
            case 4 : {
                return levelFourMove();
            }
            case 5 : {
                this.setDepth(6);
                return levelFiveMove(plateau);
            }
            /*case 6 : {
                this.setDepth(8);
                return levelSixMove(plateau);
            }*/
            default : {
                this.setDepth(6);
                return levelSixMove(plateau);
            }
        }
    }

    /**
     * Niveau 1, l'IA joue la position la colonne 3 si elle est vide
     * Sinon l'IA joue aléatoirement si la colonne est disponible
     * @return column
     */
    public  int levelOneMove() {
        for (;;) {
            if (plateau.getXY(3, 5).getContent() == 0){
                return 3;
            }
            else {
                int column = random.nextInt(plateau.getColumn());
                if (!plateau.fullColumn(column)){
                    return column;
                }
            }
        }
    }

    /**
     * Niveau 2, l'IA joue la position gagnante
     * Sinon l'IA joue aléatoirement si la colonne est disponible
     * @return column
     */
    public int levelTwoMove(){
        if (winMove(plateau) != -1){
            return winMove(plateau);
        }
        else {
            return levelOneMove();
        }
    }

    /**
     * Niveau 3, l'IA joue la position gagnante
     * Sinon l'IA joue la position blockannte si possible
     * Sinon l'IA joue pour aligner 3 pions si possible
     * Sinon l'IA joue aléatoirement si la colonne est disponible
     * @return column
     */
    public int levelThreeMove(){
        if (winMove(plateau) != -1){
            return winMove(plateau);
        }
        else if (blockMove(plateau) != -1){
            return blockMove(plateau);
        }
        else if (alignThree(plateau) != -1){
            return alignThree(plateau);
        }
        else {
            return levelOneMove();
        }
    }

    /**
     * Niveau 4, l'IA joue la position gagnante
     * Sinon l'IA joue la position blockannte si possible
     * Sinon l'IA joue la position ayant la plus grande valeur d'évaluation disponible
     * @return column
     */
    public int levelFourMove(){
        int max = 0; int col = -1;
        //evaluate(plateau);
        if (winMove(plateau) != -1){
            System.out.println("WIN-" + winMove(plateau));
            return winMove(plateau);
        }
        else if (blockMove(plateau) != -1){
            System.out.println("BLOCK-" + blockMove(plateau));
            return blockMove(plateau);
        }
        else {
            buildMatrix();
            frontCount = 0; afterCount = 0;
                for (int i = 0; i < line; i++) {
                    for (int j = 0; j < column; j++) {
                        if (i <= (line - 2)) {
                            //System.out.println("Second if");
                            if ((plateau.getXY(j, i).getContent() == 0)
                                    && (matrix[i][j] > max)
                                    && (plateau.getXY(j, (i+1)).getContent() != 0))
                            {
                                max = matrix[i][j];
                                col = j;
                            }
                        }
                        else {
                            if ((j > 0) && (j <= (column - 2)) && (plateau.getXY(j, i).getContent() != 0) && test) {
                                if (plateau.getXY(j, i).getContent() == plateau.getXY((j + 1), i).getContent()) {
                                    frontCount += (j == 2 ? 2 : 0);
                                    frontCount += (j == 3 ? 3 : 0);

                                    afterCount += (j == 2 ? 3 : 0);
                                    afterCount += (j == 3 ? 2 : 0);
                                    if ((frontCount >= 2) && (afterCount >= 1)) {
                                        test = false;
                                        return (j - 1);
                                    }
                                    else if ((frontCount >= 1) && (afterCount >= 2)) {
                                        test = false;
                                        return (j + 2);
                                    }
                                }
                            }
                            else if (plateau.getXY(j, i).getContent() == 0 && matrix[i][j] > max) {
                                max = matrix[i][j];
                                col = j;
                            }
                        }
                    }
                }
                //System.out.println("MAX-" + max);
                //System.out.println("COL-" + col);
                return col;
            //}
        }
    }

    /**
     * Algorithm MinMax
     * Niveau 5, l'IA joue la position gagnante
     * Sinon l'IA joue la position blockannte si possible
     * Sinon l'IA joue selon l'evelution de l'algorithme MinMax
     * @param plateau
     * @return column
     */
    public int levelFiveMove(Plateau plateau){
        //plat = new PlatCopy();
        if (winMove(plateau) != -1){
            //System.err.println("WIN");
            return winMove(plateau);
        }
        else if (blockMove(plateau) != -1){
            //System.err.println("BLOCK");
            return blockMove(plateau);
        }
        else {
            this.setDepth(6);
            if (plateau.getXY(3, 5).getContent() == 0){
                return 3;
            }
            else {
                int col = 0;
                try {
                    Thread.currentThread();
                    Thread.sleep(10);

                    col = plat.MinMaxMove(this);
                    for (int i = 0; i < 5; i++) {
                        //System.out.println("entry");
                        //col = plat.MinMaxMove(this);
                        plateau.addPoint(col,this.getNumber());
                        int lastLine = plateau.getLineAdd();

                        int block = blockMove(plateau);
                        if ((block != -1) && (plateau.availableColumn() >= 2)) {
                            //System.out.println("bad move ");
                            plateau.getXY(col, lastLine).setContent(0);
                            col = plat.MinMaxMove(this);
                            //System.err.println("MOVE : " + col);
                        }
                        else {
                            //System.err.println("MOVE : " +plateau.toString());
                            plateau.getXY(col, lastLine).setContent(0);
                            return col;
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.err.println(plateau.toString());
                return col;
                /*/if (plateau.addPoint(col,this.getNumber())) {
                System.out.println("a " + plateau.toString());
                plateau.addPoint(col,this.getNumber());
                System.out.println("p " + plateau.toString());
                int lastLine = plateau.getLineAdd();
                //plateau.getXY(col, lastLine).setContent(0);
                System.out.println("last " + lastLine);
                 block = blockMove(plateau);
                if (block != -1) {
                    System.out.println("bad move ");
                    plateau.getXY(col, lastLine).setContent(0);
                    col = plat.MinMaxMove(this);
                }
                return col;*/
                //}
                //System.err.println("Plat : " + plat.MinMaxMove(player));
                //return plateauCopy.MinMaxMove(player);
                //return col;
            }
        }
    }

    /**
     * Algorithm AlphaBeta
     * Niveau 5, l'IA joue la position gagnante
     * Sinon l'IA joue la position blockannte si possible
     * Sinon l'IA joue selon l'evelution de l'algorithme AlphaBeta
     * @param plateau
     * @return column
     */
    public int levelSixMove(Plateau plateau){
        if (winMove(plateau) != -1){
            return winMove(plateau);
        }
        else if (blockMove(plateau) != -1){
            return blockMove(plateau);
        }
        else {
            this.setDepth(8);
            if (plateau.getXY(3, 5).getContent() == 0){
                return 3;
            }
            else {
                try {
                    Thread.currentThread();
                    Thread.sleep(10);
                    //player.setType(TypePlayer.AlphaBeta);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return plateauCopy.AlphaBetaMove(this);
            }
        }
    }

    /**
     *
     * @param plateau
     */
    public void evaluate(Plateau plateau){
        line = plateau.getLine();
        column = plateau.getColumn();
        buildMatrix();

        //Détermine
        /*for (int i = 0; i < line; i++){
            for (int j = 0; j < column; j++){
                if (plateau.getXY(j, i).getContent() == 2){
                    VALUE_GAME_RED += matrix[i][j];
                    VALUE_GAME_YELLOW -= matrix[i][j];
                }
                else if (plateau.getXY(j, i).getContent() == 1){
                    VALUE_GAME_RED -= matrix[i][j];
                    VALUE_GAME_YELLOW += matrix[i][j];
                }
            }
        }*/

        int k = winMove(plateau);
        int l = blockMove(plateau);

        if (winMove(plateau) != -1){
            POSITION_WIN = k;
            //matrix[plateau.getLineAdd()][k] = 278;
        }
        else if (blockMove(plateau) != -1){
            POSITION_BLOCK = l;
            //matrix[plateau.getLineAdd()][l] = 277;
        }
        /*else {
            //POSITION_DEFAULT = levelOneMove();
            max(plateau);
        }*/
    }

    /**
     * WinMove : vérifie si c'est un coup gagnant et retourne la colonne
     * @param plateau
     * @return Integer column
     */
    public int winMove(Plateau plateau) {
        for (int i = 0; i < plateau.getLine(); i++){
            for (int j = 0; j < plateau.getColumn(); j++){
                if (plateau.getXY(j, i).getContent() == 0){
                    //cmp++;
                    plateau.addPoint(j, this.getNumber());
                    if (plateau.checkHorizontal(plateau.getLineAdd(), 4)
                            || plateau.checkVertical(j, 4)
                            || plateau.checkDiagonal(plateau.getLineAdd(), j, 4)
                            || plateau.checkReverseDiagonal(plateau.getLineAdd(), j, 4)
                    ){
                        //cmp--;
                        //System.err.println("WINNER");
                        plateau.getXY(j, plateau.getLineAdd()).setContent(0);
                        return j;
                    }
                    //cmp--;
                    plateau.getXY(j, plateau.getLineAdd()).setContent(0);
                }
            }
        }
        return -1;
    }

    /**
     * BlockMove : vérifie si on peut blocker l'adversaire et retourne la colonne
     * @param plateau
     * @return Integer column
     */
    public int blockMove(Plateau plateau) {
        int playerAdverse = (this.getNumber() == 1) ? 2 : 1;
        for (int i = 0; i < plateau.getLine(); i++){
            for (int j = 0; j < plateau.getColumn(); j++){
                if (plateau.getXY(j, i).getContent() == 0){
                    //cmp++;
                    plateau.addPoint(j, playerAdverse);
                    if (plateau.checkHorizontal(plateau.getLineAdd(),4) || plateau.checkVertical(j,4)
                            || plateau.checkDiagonal(plateau.getLineAdd(), j, 4)
                            || plateau.checkReverseDiagonal(plateau.getLineAdd(), j, 4)
                    ){
                        //System.err.println("BLOCK");
                        plateau.getXY(j, plateau.getLineAdd()).setContent(0);
                        //cmp--;
                        return j;
                    }
                    //cmp--;
                    plateau.getXY(j, plateau.getLineAdd()).setContent(0);
                }
            }
        }
        return -1;
    }

    /**
     * AlignThree : vérifie si on peut blocker l'adversaire et retourne la colonne
     * @param plateau
     * @return Integer column
     */
    public int alignThree(Plateau plateau){
        for (int i = 0; i < plateau.getLine(); i++){
            for (int j = 0; j < plateau.getColumn(); j++){
                if (plateau.getXY(j, i).getContent() == 0){
                    plateau.addPoint(j, this.getNumber());
                    if (plateau.checkHorizontal(plateau.getLineAdd(), 3) || plateau.checkVertical(j, 3)
                            || plateau.checkDiagonal(plateau.getLineAdd(), j, 3)
                            || plateau.checkReverseDiagonal(plateau.getLineAdd(), j, 3)
                    ){
                        plateau.getXY(j, plateau.getLineAdd()).setContent(0);
                        return j;
                    }
                    plateau.getXY(j, plateau.getLineAdd()).setContent(0);
                }
            }
        }
        return -1;
    }

    /**
     * BuildMatrix : construit la la matrice d'évaluation de jeu
     */
    public void buildMatrix() {
        String string = "3;4;5;7;5;4;3;" +
                "4;6;8;10;8;6;4;" +
                "5;8;11;13;11;8;5;" +
                "5;8;11;13;11;8;5;" +
                "4;6;8;10;8;6;4;" +
                "3;4;5;7;5;4;3";
        String[] str = string.split(";");

        int place = 0;
        matrix = new int[line][column];
        for (int i = 0; i < line; i++){
            for (int j = 0; j < column; j++){
                matrix[i][j] = Integer.parseInt(str[place]);
                place ++;
            }
        }
    }

    /**
     * ReadMatrix : affiche la matrice
     */
    public void readMatrix(){
        for (int i = 0; i < line; i++){
            for (int j = 0; j < column; j++){
                System.err.print(matrix[i][j] + " ");
            }
            System.err.println();
        }
    }

    /**
     *
     * @param plateau
     * @return
     */
    public String max1(Plateau plateau) {
        int max = matrix[0][0];   // start with the first value
        int x = 0, y = 0;
        for (int i = 0; i < line; i++){
            for (int j = 0; j < column; j++){
                if (i <= 4){
                    /*if (i <= 1){
                        System.err.println("test");
                    }*/
                    if (plateau.getXY(j, i).getContent() == 0
                            && matrix[i][j] > max
                            && plateau.getXY(j, (i+1)).getContent() != 0 )
                    {
                        /*if (i <= 1){
                            max = 0;
                            //System.err.println("i : " + i);
                        }
                        else {
                            max = matrix[i][j];
                        }*/
                        max = matrix[i][j];
                        x = j; y = i;
                        POSITION_DEFAULT = j;
                    }
                }
                else {
                    if (plateau.getXY(j, i).getContent() == 0
                            && matrix[i][j] > max)
                    {
                        max = matrix[i][j];
                        x = j; y = i;
                        POSITION_DEFAULT = j;
                    }
                }
            }
            //return max;
        }
        //System.err.println();
        //return maximum;
        return "max : " + max + " || x-y : " + x + "-" +y;
    }//end method max

    public void max(Plateau plateau){
        int max = 0;
        //POSITION_DEFAULT = 0;
        for (int i = 0; i < line; i++) {
            for (int j = 0; j < column; j++) {
                if (plateau.getXY(j, i).getContent() == 0 && matrix[i][j] > max) {
                    max = matrix[i][j];
                    POSITION_DEFAULT = j;
                }
            }
        }
        System.err.println("DEFAULT : " + POSITION_DEFAULT);
    }
    public int[][] buildMatrix1(){
        //line = data[0]; column = data[1];
        matrix = new int[line][column];
        for (int i = line - 1; i >= 0; i--){
            for (int j = 0; j < column; j++){
                matrix[i][j] = plateau.getXY(j, i).getContent();
            }
        }
        return matrix;
    }

    /**
     * AddPoint : ajoute un pion à la copie du plateau utilisé
     * par l'algorithme MinMax
     * @param column
     * @param player
     */
    public void addPoint(int column, int player){
        if (level > 4){
            //plateauCopy.addPoint(column, player);
            plat.addPoint(column, player);
        }
    }

    /*if ((j > 0) && (j <= (column - 2)) && (plateau.getXY(j, i).getContent() != 0)) {
                            System.out.println("First if");
                            //frontCount += 1;    afterCount += 1;
                            if ((
                                    plateau.getXY(j, i).getContent() == plateau.getXY((j + 1), i).getContent())
                                    && (i <= (line - 2))
                                    && plateau.getXY(j, i).getContent() != this.getNumber())
                            {
                                if (((plateau.getXY((j - 1), (i+1)).getContent() != 0) && (plateau.getXY((j - 2), (i+1)).getContent() != 0) && (plateau.getXY((j + 2), (i+1)).getContent() != 0))
                                || ((plateau.getXY((j - 1), (i+1)).getContent() != 0) && (plateau.getXY((j + 2), (i+1)).getContent() != 0) && (plateau.getXY((j + 3), (i+1)).getContent() != 0))) {
                                    System.err.println("First if -> if");
                                     frontCount += (j == 2 ? 2 : 0);
                                    frontCount += (j == 3 ? 3 : 0);

                                    afterCount += (j == 2 ? 3 : 0);
                                    afterCount += (j == 3 ? 2 : 0);

                                    if ((frontCount >= 2) && (afterCount >= 1)) {
                                        System.err.println("COUP-" + (j - 1));
                                        return (j - 1);
                                    }
                                    else if ((frontCount >= 1) && (afterCount >= 2)) {
                                        System.err.println("COUP+" + (j + 2));
                                        return (j + 2);
                                    }
                                }

                                //frontCount += 1;



                                //System.err.println("ENTRY TEST-" + j);
                                //System.err.println("FRONT-" + frontCount);
                                //System.err.println("AFTER-" + afterCount);
                                //System.err.println("ENTRY TEST-" + (j + 1));
                            }
                        }*/
}
