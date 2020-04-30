package ia;

import engine.Plateau;

public class Evaluate {
    private static int [][] matrix;
    private static int EVALUATE_GAME = 0;
    private static final int line = 6, column = 7;
    public Evaluate() {
        buildMatrix(line, column);
    }

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

    public int evaluate(Plateau plateau,int player){
        int player_adverse = (player == 1) ? 2 : 1;
        int evaluation = 0;
        //Détermine
        for (int i = 0; i < line; i++){
            for (int j = 0; j < column; j++){
                if (plateau.getXY(j, i).getContent() == player){
                    evaluation += matrix[i][j];
                }
                else if (plateau.getXY(j, i).getContent() == player_adverse){
                    evaluation -= matrix[i][j];
                }
            }
        }
        return evaluation;
    }
}
