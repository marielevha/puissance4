package ia;

import engine.Plateau;

import java.util.Random;

public class IAMariel {
    private static Random random = new Random();
    private static int MAX_ALIGN = 4, line = 6, column = 7;
    private static int VALUE_GAME_RED = 0, VALUE_GAME_YELLOW = 0, POSITION_WIN = -1, POSITION_BLOCK = -1, POSITION_DEFAULT = -1;
    private static int cmp = 0;
    private static int [][] matrix;

    private int getMaxAlign() {
        return MAX_ALIGN;
    }

    private int setMaxAlign() {
        return MAX_ALIGN = 3;
    }

    /*public static void main(String[] args) {
        for (int i = 0; i <= 20; i++){
            System.out.println(random.nextInt((7 - 1) + 1));
        }
    }*/

    public  int bestMove(Plateau plateau){
        return levelOneMove(plateau);
    }
    public  int levelOneMove(Plateau plateau){
        for (;;) {
            int column = random.nextInt(plateau.getLineColumn()[1]);
            //System.err.println(column);
            if (!plateau.fullColumn(column)){
                //plateau.addPoint(column, 2);
                return column;
            }
        }
    }
    public int levelTwoMove(Plateau plateau){
        int rep = winMove(plateau);
        if (rep != -1){
            return rep;
        }
        else {
            return levelOneMove(plateau);
        }
    }
    public int levelThreeMove(Plateau plateau){
        int win = winMove(plateau);
        if (win != -1){
            return win;
        }
        else {
            int block = blockMove(plateau);
            if (block != -1){
                return block;
            }
            else {
                return levelOneMove(plateau);
            }
            /*rep = blockMove(plateau);
            if (rep != -1){
                return rep;
            }
            else {
                rep = alignThree(plateau);
                if (rep != -1){
                    return rep;
                }
                else {
                    return levelOneMove(plateau);
                }
            }*/
        }
        /*if (winMove(plateau) != -1){
            return winMove(plateau);
        }
        else if (alignThree(plateau) != -1){
            return alignThree(plateau);
        }
        else {
            return levelOneMove(plateau);
        }*/
    }
    public int levelFourMove(Plateau plateau){
        /*if (winMove(plateau) != -1){
            return winMove(plateau);
        }
        else if (alignThree(plateau) != -1){
            return alignThree(plateau);
        }
        else {
            return levelOneMove(plateau);
        }*/
        return -1;
    }


    public int winMove(Plateau plateau){
        for (int i = 0; i < plateau.getLineColumn()[0]; i++){
            for (int j = 0; j < plateau.getLineColumn()[1]; j++){
                if (plateau.getXY(j, i).getContent() == 0 && cmp == 0){
                    cmp++;
                    plateau.addPoint(j, 2);
                    if (plateau.checkHorizontal(plateau.getLineAdd(), getMaxAlign()) || plateau.checkVertical(j, getMaxAlign())
                            || plateau.checkDiagonal(plateau.getLineAdd(), j, getMaxAlign())
                            || plateau.checkReverseDiagonal(plateau.getLineAdd(), j, getMaxAlign())
                    ){
                        //plateau.getXY(j, plateau.getLineAdd()).setContent(0);
                        return j;
                    }
                    cmp--;
                    plateau.getXY(j, plateau.getLineAdd()).setContent(0);
                }
            }
        }
        return -1;
    }
    public int blockMove(Plateau plateau){
        for (int i = 0; i < plateau.getLineColumn()[0]; i++){
            for (int j = 0; j < plateau.getLineColumn()[1]; j++){
                if (plateau.getXY(j, i).getContent() == 0 && cmp == 0){
                    cmp++;
                    plateau.addPoint(j, 1);
                    if (plateau.checkHorizontal(plateau.getLineAdd(), getMaxAlign()) || plateau.checkVertical(j, getMaxAlign())
                            || plateau.checkDiagonal(plateau.getLineAdd(), j, getMaxAlign())
                            || plateau.checkReverseDiagonal(plateau.getLineAdd(), j, getMaxAlign())
                    ){
                        plateau.getXY(j, plateau.getLineAdd()).setContent(0);
                        return j;
                    }
                    cmp--;
                    plateau.getXY(j, plateau.getLineAdd()).setContent(0);
                }
            }
        }
        return -1;
    }
    public int alignThree(Plateau plateau){
        for (int i = 0; i < plateau.getLineColumn()[0]; i++){
            for (int j = 0; j < plateau.getLineColumn()[1]; j++){
                if (plateau.getXY(j, i).getContent() == 0 && cmp == 0){
                    cmp++;
                    //System.err.println(i + " " + j);
                    //break;
                    plateau.addPoint(j, 2);
                    //System.err.println(plateau.getLineAdd());
                    //System.err.println(plateau.getLineAdd() + "-" + j);
                    //break;
                    if (plateau.checkHorizontal(plateau.getLineAdd(), setMaxAlign()) || plateau.checkVertical(j, setMaxAlign())
                            || plateau.checkDiagonal(plateau.getLineAdd(), j, setMaxAlign())
                            || plateau.checkReverseDiagonal(plateau.getLineAdd(), j, setMaxAlign())
                    ){
                        plateau.getXY(j, plateau.getLineAdd()).setContent(0);
                        return j;
                    }
                    cmp--;
                    plateau.getXY(j, plateau.getLineAdd()).setContent(0);
                }
            }
        }
        return -1;
    }

    //String string = "3;4;5;7;5;4;3;4;6;8;10;8;6;4;5;8;11;13;11;8;5;5;8;11;13;11;8;5;4;6;8;10;8;6;4;3;4;5;7;5;4;3";

    public void evaluate(Plateau plateau){
        line = plateau.getLineColumn()[0];
        column = plateau.getLineColumn()[1];
        buildMatrix(line, column);

        for (int i = 0; i < line; i++){
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
        }

        int k = winMove(plateau);
        int l = blockMove(plateau);

        if (k != -1){
            POSITION_WIN = k;
            matrix[plateau.getLineAdd()][k] = 278;
        }
        else if (l != -1){
            POSITION_BLOCK = l;
            matrix[plateau.getLineAdd()][l] = 277;
        }
        else {
            max(plateau);
            //POSITION_DEFAULT = -1;
        }

        //readMatrix();

        System.err.println("POSITION_WIN :: " + POSITION_WIN);
        System.err.println("POSITION_BLOCK :: " + POSITION_BLOCK);
        System.err.println("POSITION_DEFAULT :: " + POSITION_DEFAULT);
        System.err.println("VALUE_CASE_RED :: " + VALUE_GAME_RED);
        System.err.println("VALUE_CASE_YELLOW :: " + VALUE_GAME_YELLOW);
        //return -1;
    }
    public void buildMatrix(int line, int column) {
        String string = "3;4;5;7;5;4;3;4;6;8;10;8;6;4;5;8;11;13;11;8;5;5;8;11;13;11;8;5;4;6;8;10;8;6;4;3;4;5;7;5;4;3";
        String[] str = string.split(";");

        int place = 0;
        matrix = new int[line][column];
        for (int i = 0; i < line; i++){
            for (int j = 0; j < column; j++){
                matrix[i][j] = Integer.parseInt(str[place]);
                place ++;
            }
        }

        //Read

    }
    private void readMatrix(){
        for (int i = 0; i < line; i++){
            for (int j = 0; j < column; j++){
                System.err.print(matrix[i][j] + " ");
            }
            System.err.println();
        }
    }

    public  void max(Plateau plateau) {
        int max = matrix[0][0];   // start with the first value
        for (int i = 0; i < line; i++){
            for (int j = 0; j < column; j++){
                if (plateau.getXY(j, i).getContent() == 0 && matrix[i][j] > max){
                    max = matrix[i][j];
                    POSITION_DEFAULT = j;
                    /*if (matrix[i][j] > max){
                   max = matrix[i][j];
               }*/
                }
            }
        }
        //System.err.println();
        //return maximum;
    }//end method max
}
