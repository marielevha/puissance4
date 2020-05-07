package ia;

import engine.Line;
import engine.Plateau;

public class MinMax {
    private static int [][] matrix;
    private static int column, line, columnMax, player = 2, currentPlayer = 2, depthPlayer, heuristicPlayer = 1;
    private static Plateau plateau;


    public MinMax(String string) {
        plateau = new Plateau(string, 0);
        line = plateau.getLine();
        column = plateau.getColumn();
        //System.err.println(plateau.toStringIA());
    }

    public int minMax(){
        treatment(100, player, currentPlayer);
        return columnMax;
    }

    public int treatment(int depth, int player, int currentPlayer){
        int playerAdverse = (player == 1) ? 2 : 1;
        int currentPlayerAdverse = (currentPlayer == 1) ? 2 : 1;
        int[] win = checkVictory();
        /*if (win[0] != -1){
            if (win[1] == player){
                return (1000 - depth);
            }
            else {
                return (-1000 + depth);
            }
        }*/

        //Verify depth
        if ((currentPlayer == 1 && depth == depthPlayer) || (currentPlayer == 2 && depth == depthPlayer)) {
            if ((currentPlayer == 1 && heuristicPlayer == 1) || (currentPlayer == 2 && heuristicPlayer == 1)) {
                return new Evaluate().evaluate(plateau, currentPlayer);
            }
            else {
                return new Evaluate().evaluate(plateau, currentPlayer);
            }
        }

        depth++;

        //If the player is the current player
        if (player == currentPlayer) {
            int max = -10000;
            int col = 0;
            for (int i = 0; i < column; i++){
                boolean bool = plateau.fullColumn(i);
                if (!bool) {
                    plateau.addPoint(i, player);
                    int value = treatment(depth, playerAdverse, currentPlayer);
                    if (max < value) {
                        max = value;
                        col = i;
                    }
                    plateau.getXY(i, plateau.getLineAdd()).setContent(0);
                    //plateau.
                }
            }
            columnMax = col;
            return max;
        }
        else {
            int min = 10000;
            for (int i = 0; i < column; i++) {
                boolean bool = plateau.fullColumn(i);
                if (!bool) {
                    plateau.addPoint(i, currentPlayerAdverse);
                    int value = treatment(depth, playerAdverse, currentPlayer);
                    if (min > value) {
                        min = value;
                        //col = i;
                    }
                    plateau.getXY(i, plateau.getLineAdd()).setContent(0);
                    //plateau.
                }
            }
            return min;
        }
        //return -1;
    }

    public int[] checkVictory(){
        int player = 2; int[] data = new int[2];
        data[0] = -1;
        for (int i = 0; i < line; i++){
            for (int j = 0; j < column; j++){
                if (plateau.getXY(j, i).getContent() == 0){
                    //cmp++;
                    plateau.addPoint(j, player);
                    if (plateau.checkHorizontal(plateau.getLineAdd(), 4) || plateau.checkVertical(j, 4)
                            || plateau.checkDiagonal(plateau.getLineAdd(), j, 4)
                            || plateau.checkReverseDiagonal(plateau.getLineAdd(), j, 4)
                    ){
                        //cmp--;
                        //System.err.println("WINNER");
                        plateau.getXY(j, plateau.getLineAdd()).setContent(0);
                        data[0] = j; data[1] = player;
                        return data;
                    }
                    //cmp--;
                    plateau.getXY(j, plateau.getLineAdd()).setContent(0);
                }
            }
        }
        //buildMatrix();
        //Balayage horizontal _
        /*for (int ligne = 0; ligne <= 3; ligne++) {
            for (int colonne = 5; colonne >= 0; colonne--) {
                if (matrix[colonne][ligne] != 0
                        && matrix[ligne][colonne] == matrix[ligne + 1][colonne]
                        && matrix[ligne][colonne] == matrix[ligne + 2][colonne]
                        && matrix[ligne][colonne] == matrix[ligne + 3][colonne]) {
                    return matrix[ligne][colonne];
                }
            }
        }*/
        /*for (int i = 0; i < line; i++){
            for (int j = 0; j < column; j++){
                if (plateau.getXY(j, i).getContent() == plateau.getXY(j, i).getContent()
                    == plateau.getXY(j, i).getContent())
            }
        }*/
        return data;
    }

    public void buildMatrix(){
        //line = data[0]; column = data[1];
        matrix = new int[line][column];
        for (int i = 0; i < line; i++){
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
        }*/
    }
}
