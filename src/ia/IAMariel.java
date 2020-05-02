package ia;

import engine.Plateau;
import old.PlateauCopy;
import old.Player;
import old.TypePlayer;

import java.util.Random;

public class IAMariel extends Player{
    private static Random random = new Random();
    private static int MAX_ALIGN = 4, line = 6, column = 7;
    private static int VALUE_GAME_RED = 0, VALUE_GAME_YELLOW = 0, POSITION_WIN = -1, POSITION_BLOCK = -1, POSITION_DEFAULT = -1;
    private static int cmp = 0;
    private static int [][] matrix;

    private static Plateau plateau;
    private int level;
    private int number = 2;
    private Type type;
    private static PlateauCopy plateauCopy;
    private static Player player = new Player(2);

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

    public IAMariel(int level) {
        super(2);
        this.level = level;
        if (level == 5) {
            this.type = Type.MIN_MAX;
            plateauCopy = new PlateauCopy(line,column);
        }
        else if (level == 6) {
            this.type = Type.ALPHA_BETA;
            plateauCopy = new PlateauCopy(line,column);
        }
    }

    /***
     * Meilleur Coupe
     * @param string
     * @return
     */
    public int bestMove(String string){
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
                return levelFiveMove(plateau);
            }
            case 6 : {
                return levelSixMove(plateau);
            }
            default : {
                return -1;
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
                int column = random.nextInt(plateau.getLineColumn()[1]);
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
        if (plateau.getXY(3, 5).getContent() == 0) {
            return 3;
        }
        evaluate(plateau);
        if (POSITION_WIN != -1){
            return POSITION_WIN;
        }
        else if (POSITION_BLOCK != -1){
            return POSITION_BLOCK;
        }
        else {
            return POSITION_DEFAULT;
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
        if (winMove(plateau) != -1){
            return winMove(plateau);
        }
        else if (blockMove(plateau) != -1){
            return blockMove(plateau);
        }
        else {
            if (plateau.getXY(3, 5).getContent() == 0){
                return 3;
            }
            else {
                try {
                    Thread.currentThread();
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return plateauCopy.jouerMinMax(player);
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
                return plateauCopy.jouerAB(player);
            }
        }
    }
    /**
     *
     * @param plateau
     */
    public void evaluate(Plateau plateau){
        line = plateau.getLineColumn()[0];
        column = plateau.getLineColumn()[1];
        buildMatrix(line, column);

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
            matrix[plateau.getLineAdd()][k] = 278;
        }
        else if (blockMove(plateau) != -1){
            POSITION_BLOCK = l;
            matrix[plateau.getLineAdd()][l] = 277;
        }
        else {
            max(plateau);
        }
    }

    /**
     *
     * @param plateau
     * @return
     */
    public int winMove(Plateau plateau){
        for (int i = 0; i < plateau.getLineColumn()[0]; i++){
            for (int j = 0; j < plateau.getLineColumn()[1]; j++){
                if (plateau.getXY(j, i).getContent() == 0){
                    //cmp++;
                    plateau.addPoint(j, 2);
                    if (plateau.checkHorizontal(plateau.getLineAdd(), 4) || plateau.checkVertical(j, 4)
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
     *
     * @param plateau
     * @return
     */
    public int blockMove(Plateau plateau){
        for (int i = 0; i < plateau.getLineColumn()[0]; i++){
            for (int j = 0; j < plateau.getLineColumn()[1]; j++){
                if (plateau.getXY(j, i).getContent() == 0){
                    //cmp++;
                    plateau.addPoint(j, 1);
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
     *
     * @param plateau
     * @return
     */
    public int alignThree(Plateau plateau){
        for (int i = 0; i < plateau.getLineColumn()[0]; i++){
            for (int j = 0; j < plateau.getLineColumn()[1]; j++){
                if (plateau.getXY(j, i).getContent() == 0){
                    plateau.addPoint(j, 2);
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
     *
     * @param line
     * @param column
     */
    public void buildMatrix(int line, int column) {
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
     *
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
    public String max(Plateau plateau) {
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

    public int[][] buildMatrix(){
        //line = data[0]; column = data[1];
        matrix = new int[line][column];
        for (int i = line - 1; i >= 0; i--){
            for (int j = 0; j < column; j++){
                matrix[i][j] = plateau.getXY(j, i).getContent();
            }
        }

        //Read
        /*for (int i = 0; i < line; i++){
            for (int j = 0; j < column; j++){
                System.err.print(matrix[i][j]);
            }
            System.err.println();
        }
        System.err.println("\n");*/
        return matrix;
    }
    public void addPoint(int column, int player){
        if (level > 4){
            plateauCopy.jouerCoup(column, player);
        }
    }
}
