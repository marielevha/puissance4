package ia;

import engine.Plateau;

import java.util.Random;

public class IAMariel {
    private static Random random = new Random();
    private static int MAX_ALIGN = 4, line = 6, column = 7;
    private static int VALUE_GAME_RED = 0, VALUE_GAME_YELLOW = 0, POSITION_WIN = -1, POSITION_BLOCK = -1, POSITION_DEFAULT = -1;
    private static int cmp = 0;
    private static int [][] matrix;

    private static Plateau plateau;
    private final int level;


    public IAMariel(int level) {
        this.level = level;
        //this.plateau = new Plateau(string);
    }

    public int bestMove(String string){
        plateau = new Plateau(string);
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
            default : {
                return levelOneMove();
            }
        }
    }

    /**
     *
     * @return
     */
    public  int levelOneMove(){
        for (;;) {
            int column = random.nextInt(plateau.getLineColumn()[1]);
            //System.err.println(column);
            if (!plateau.fullColumn(column)){
                //plateau.addPoint(column, 2);
                return column;
            }
        }
    }

    /**
     *
     * @return
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
     *
     * @return
     */
    public int levelThreeMove(){
        /*System.err.println(plateau.toString());
        int win = winMove(plateau);*/

        //System.err.println(win + "||" + block);

        //int three = alignThree(plateau);
        /*if (win != -1){
            System.err.println("win : "+win);
            return win;
        }
        else {
            int block = blockMove(plateau);
            if (block != -1) {
                System.err.println("block : "+block);
                return block;
            }
            else {
                int result = alignThree(plateau);
                if (result != -1){
                    return result;
                }
                else {
                    return levelOneMove();
                }
            }
        }*/

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


    public int levelFourMove(){
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
            //POSITION_DEFAULT = -1;
        }

        /*readMatrix();

        System.err.println("POSITION_WIN :: " + POSITION_WIN);
        System.err.println("POSITION_BLOCK :: " + POSITION_BLOCK);
        System.err.println("POSITION_DEFAULT :: " + POSITION_DEFAULT);
        System.err.println("VALUE_CASE_RED :: " + VALUE_GAME_RED);
        System.err.println("VALUE_CASE_YELLOW :: " + VALUE_GAME_YELLOW);*/
        //return -1;
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
}
