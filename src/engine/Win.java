package engine;

public class Win {
    private static int [][] matrix;
    //private Plateau plateau;
    private static int line, column;

    public void BuildMatrix(Line[] lines, int [] data) {
        line = data[0]; column = data[1];
        matrix = new int[line][column];
        for (int i = 0; i < line; i++){
            for (int j = 0; j < column; j++){
                matrix[i][j] = lines[i].getX(j).getContent();
            }
        }

        //Read
        for (int i = 0; i < line; i++){
            for (int j = 0; j < column; j++){
                System.err.print(matrix[i][j]);
            }
            System.err.println();
        }

    }
    public boolean verify(){
       return false;
    }
}
