package ia;

import engine.Plateau;
import ia.minmax.MinMax;
import engine.player.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class IAMariel extends Player{
    private static Random random = new Random();
    private static final int line = 6;
    private static final int column = 7;
    private static int POSITION_DEFAULT = -1;
    private static int [][] matrix;
    private static boolean test = true;

    private static Plateau plateau;
    private static int ssdlv;
    private int level;
    private static MinMax plateauMinMax;

    public IAMariel() {}

    public IAMariel(int number, int level) {
        super(number);
        this.level = level;
        this.setTypePlayer("IA");
        ssdlv = number;
        if (level >= 5) {
            this.setDepth(6);
            plateauMinMax = new MinMax(line, column);
        }
    }

    /***
     * BestMove(Meilleur Coupe) : Joue un coup en fonction du niveau de l'IA
     * @param string
     * @return Integer column
     */
    public int bestMove(String string, int player){
        //IAMariel.player = new Player(player);
        this.setNumber(player);
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
                this.setDepth(8);
                return levelFiveMove(plateau);
            }
            default : {
                this.setDepth(8);
                return levelFiveMove(plateau);
            }
        }
    }

    /**
     * Niveau 1, l'IA joue la position la colonne 3 si elle est vide
     * Sinon l'IA joue aléatoirement si la colonne est disponible
     * @return column
     */
    public  int levelOneMove() {
        //for (;;) {
            if (plateau.getXY(3, 5).getContent() == 0) {
                return 3;
            }
            else {
                /**
                 * Récupération de la liste des colonnes disponibles
                 * Permute (mélange) la liste des colonnes
                 * Retourne le choix d'index 0;
                 */
                ArrayList<Integer> choices = plateau.availableColumn();
                Collections.shuffle(choices);
                //System.out.println(choices);
                int c = choices.get(0);
                for (int i = 0; i < choices.size(); i++) {
                    plateau.addPoint(c, this.getNumber());
                    int lastLine = plateau.getLineAdd();
                    if (blockMove(plateau) != -1) {
                        plateau.getXY(c, lastLine).setContent(0);
                        Collections.shuffle(choices);
                    }
                    else {
                        plateau.getXY(c, lastLine).setContent(0);
                        return c;
                    }
                }
                return choices.get(0);
            }
        //}
    }

    /**
     * Niveau 2, l'IA joue la position gagnante
     * Sinon l'IA joue aléatoirement si la colonne est disponible
     * @return column
     */
    public int levelTwoMove() {
        if (winMove(plateau) != -1) {
            return winMove(plateau);
        }
        else {
            return levelOneMove();
        }
    }

    /**
     * Niveau 3, l'IA joue la position gagnante
     * Sinon l'IA joue la position blockannte si possible
     * Sinon l'IA joue pour aligner 3 pions avec possibilité de victoir au prochain coup
     * Sinon l'IA joue aléatoirement si la colonne est disponible
     * @return column
     */
    public int levelThreeMove() {
        if (winMove(plateau) != -1) {
            return winMove(plateau);
        }
        else if (blockMove(plateau) != -1) {
            return blockMove(plateau);
        }
        else {
            int col = alignThree(plateau);
            if (col != -1) {
                return col;
            }
            return levelOneMove();
        }
    }

    /**
     * Niveau 4, l'IA joue la position gagnante
     * Sinon l'IA joue la position blockannte si possible
     * Sinon l'IA joue la position ayant la plus grande valeur d'évaluation disponible
     * @return column
     */
    public int levelFourMove() {
        if (winMove(plateau) != -1){
            //System.out.println("WIN-" + winMove(plateau));
            return winMove(plateau);
        }
        else if (blockMove(plateau) != -1){
            //System.out.println("BLOCK-" + blockMove(plateau));
            return blockMove(plateau);
        }
        else {
            return evalLevelFour(plateau);
        }
    }
    private int evalLevelFour(Plateau plateau) {
        int max = 0; int col = -1;
        if (plateau.totalPoints() > 1) {
            test = true;
            buildMatrix();
            for (int i = 0; i < line; i++) {
                for (int j = 0; j < column; j++) {
                    if (i <= (line - 2)) {
                        if ((j >= 2) && (j <= (column - 3)) && (i >= 1)) {
                            if ((plateau.getXY(j, i).getContent() != 0)
                                    && (plateau.getXY(j, i).getContent()) == (plateau.getXY(j, (i + 1)).getContent())
                                    && (plateau.getXY(j, (i - 1)).getContent() == 0)
                            ) {
                                if ((((plateau.getXY(j, i).getContent() == plateau.getXY((j + 1), (i - 1)).getContent())
                                        && (plateau.getXY(j, i).getContent() == plateau.getXY((j + 2), (i - 1)).getContent()))
                                        && (plateau.getXY((j - 1), i).getContent() != 0))

                                        || ((plateau.getXY(j, i).getContent() == plateau.getXY((j - 1), (i - 1)).getContent())
                                        && (plateau.getXY(j, i).getContent() == plateau.getXY((j - 2), (i - 1)).getContent())
                                        && (plateau.getXY((j + 1), i).getContent() != 0))
                                ) {
                                    System.err.println("TEST TRUE " + i + "-" + j);
                                    return j;
                                }
                                /*else if ((plateau.getXY(j, i).getContent() == plateau.getXY((j - 1), (i - 1)).getContent())
                                        && (plateau.getXY(j, i).getContent() == plateau.getXY((j - 2), (i - 1)).getContent())
                                        && (plateau.getXY((j + 1), i).getContent() != 0)
                                ) {
                                    System.out.println("TEST TRUE " + i + "-" + j);
                                    return j;
                                }*/
                            }
                        }
                        if ((j > 0) && (j <= (column - 3)) && (plateau.getXY(j, i).getContent() != 0)) {
                            /*System.err.println("ENTRY");
                            System.err.println(plateau.toString());
                            System.err.println("ENTRY");*/
                            //System.out.println(i + "-" + j);
                            if (plateau.getXY(j, i).getContent() == plateau.getXY((j + 1), i).getContent()) {
                                int left = countEmptyCase(i, j, "left");
                                int right = countEmptyCase(i, j, "right");
                                System.out.println(left + "line : " + i + "-" + j);
                                System.out.println(right + "line : " + i + "-" + j);

                                int downLeft = 0;// = countFilledCase(i, j, "left");
                                int downRight = 0;// = countFilledCase(i, j, "right");

                                if ((left >= 2) && (right >= 1)) {
                                    downLeft = countFilledCase(i, j, "left");
                                    downRight = countFilledCase(i, j, "right");

                                    //System.err.println(downLeft + "||" + i + "-" + j);
                                    //System.err.println(downRight + "||" + i + "-" + j);
                                    if ((downLeft >= 2) && (downRight >= 1)) {
                                        return (j - 1);
                                    }
                                    else if ((downLeft >= 1) && (downRight >= 2)) {
                                        return (j + 2);
                                    }
                                }
                                else if ((left >= 1) && (right >= 2)) {
                                    downLeft = countFilledCase(i, j, "left");
                                    downRight = countFilledCase(i, j, "right");
                                    //System.out.println("Others lines + (left >= 1) && (right >= 2) " + i + "-" + j);
                                    //System.out.println(downLeft + "||" + i + "-" + j);
                                    //System.out.println(downRight + "||" + i + "-" + j);
                                    if ((downLeft >= 2) && (downRight >= 1)) {
                                        if ((plateau.getXY((j - 1), i).getContent() == 0)) {
                                            System.out.println("Others lines + (downLeft >= 2) && (downRight >= 1) " + i + "-" + j);
                                            return (j - 1);
                                        }
                                    }
                                    else if ((downLeft >= 1) && (downRight >= 2)) {
                                        if ((plateau.getXY((j - 1), i).getContent() == 0)) {
                                            System.err.println("Others lines + (downLeft >= 1) && (downRight >= 2) " + i + "-" + j);
                                            return (j + 2);
                                        }
                                    }
                                }
                            }
                        }
                        else if ((plateau.getXY(j, i).getContent() == 0)
                                && (matrix[i][j] > max)
                                && (plateau.getXY(j, (i+1)).getContent() != 0))
                        {
                            max = matrix[i][j];
                            col = j;
                            //System.err.println("Others lines + max ");
                        }
                    }
                    else {
                        if ((j > 0) && (j <= (column - 3)) && (plateau.getXY(j, i).getContent() != 0) && test) {
                            if (plateau.getXY(j, i).getContent() == plateau.getXY((j + 1), i).getContent()) {
                                /*frontCount += (j == 2 ? 2 : 0);
                                frontCount += (j == 3 ? 3 : 0);

                                afterCount += (j == 2 ? 3 : 0);
                                afterCount += (j == 3 ? 2 : 0);*/
                                int left = countEmptyCase(i, j, "left");
                                int right = countEmptyCase(i, j, "right");
                                //System.out.println(right);
                                //System.out.println(left);
                                if ((left >= 2) && (right >= 1)) {
                                    //System.err.println("Last line + (left >= 2) && (right >= 1) ");
                                    test = false;
                                    return (j - 1);
                                }
                                else if ((left >= 1) && (right >= 2)) {
                                    //System.err.println("Last line + (left >= 1) && (right >= 2) ");
                                    test = false;
                                    return (j + 2);
                                }
                            }
                        }
                        else if (plateau.getXY(j, i).getContent() == 0 && matrix[i][j] > max) {
                            max = matrix[i][j];
                            col = j;
                            //System.err.println("Last line + max ");
                        }
                    }
                }
            }
            //System.out.println("MAX-" + max);
            //System.out.println("COL-" + col);
            return col;
        }
        else {
            return column / 2;
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
    public int levelFiveMove(Plateau plateau) {
        if (winMove(plateau) != -1) {
            return winMove(plateau);
            /*plat.addPoint(col, this.getNumber());
            return col;*/
        }
        else if (blockMove(plateau) != -1) {
            int col = blockMove(plateau);
            //plateauMinMax.getPlateau().addPoint(col, this.getNumber());
            plateauMinMax.addPoint(col, this.getNumber());
            return col;
        }
        else {
            /*if (plateau.getXY(3, 5).getContent() == 0){
                plat.addPoint(3, this.getNumber());
                return 3;
            }
            else {
                plat.MinMaxMove(this);
                //System.err.println(plateau.toString());
                return plat.getLastColumn();
            }*/
            plateauMinMax.move(this);
            //System.err.println(plateau.toString());
            return plateauMinMax.getLastColumn();
        }
    }

    /**
     * WinMove : vérifie si c'est un coup gagnant et retourne la colonne
     * @param plateau
     * @return Integer column
     */
    public int winMove(Plateau plateau) {
        return alignment(plateau,this.getNumber(), 4);
    }

    /**
     * BlockMove : vérifie si on peut blocker l'adversaire et retourne la colonne
     * @param plateau
     * @return Integer column
     */
    public int blockMove(Plateau plateau) {
        int playerAdverse = (this.getNumber() == 1) ? 2 : 1;
        return alignment(plateau, playerAdverse, 4);
    }

    /**
     * AlignThree : vérifie si on peut blocker l'adversaire et retourne la colonne
     * @param plateau
     * @return Integer column
     */
    public int alignThree(Plateau plateau) {
        int col = alignment(plateau, this.getNumber(), 3);
        if (col != -1) {
            plateau.addPoint(col, this.getNumber());
            int lastLine = plateau.getLineAdd();
            if (winMove(plateau) != - 1) {
                plateau.getXY(col, lastLine).setContent(0);
                return col;
            }
            plateau.getXY(col, lastLine).setContent(0);
        }
        return col;
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
     * AddPoint : ajoute un pion à la copie du plateau utilisé
     * par l'algorithme MinMax
     * @param column
     * @param player
     */
    public void addPoint(int column, int player) {
        if ((level == 5) && (player != this.getNumber())) {
            plateauMinMax.addPoint(column, player);
        }
        //plat.display();
        /*else if (level == 6) {
            plateauCopy.addPoint(column, player);
        }*/
    }

    /**
     * Alignment : renvoi la colonne s'il y a un nombre d'alignement passé en paramètre
     * @param plateau
     * @param player
     * @param alignment
     * @return Integer
     */
    private int alignment(Plateau plateau, int player, int alignment) {
        for (int i = 0; i < plateau.getLine(); i++) {
            for (int j = 0; j < plateau.getColumn(); j++) {
                if (plateau.getXY(j, i).getContent() == 0) {
                    //cmp++;
                    plateau.addPoint(j, player);
                    if (plateau.checkHorizontal(plateau.getLineAdd(),alignment) || plateau.checkVertical(j,alignment)
                            || plateau.checkDiagonal(plateau.getLineAdd(), j, alignment)
                            || plateau.checkReverseDiagonal(plateau.getLineAdd(), j, alignment)
                    ) {
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
     * CountEmptyCase : comppte le nombre de cases disponibles
     * à gauche et droite d'une position
     * @param line
     * @param col
     * @param position
     * @return
     */
    private int countEmptyCase(int line, int col, String position) {
        int count = 0;
        if (position.equals("left") && (plateau.getXY((col - 1), line).getContent() == 0)) {
            for (int i = (col - 1); i >= 0; i--) {
                if (plateau.getXY(i, line).getContent() == 0) {
                    count++;
                }
            }
        }
        else if (position.equals("right") && (plateau.getXY((col + 2), line).getContent() == 0)) {
            for (int i = (col + 1); i < plateau.getColumn(); i++) {
                if (plateau.getXY(i, line).getContent() == 0) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * CountFilledCase : comppte le nombre de cases occupées
     * à gauche et droite d'une position
     * @param line
     * @param col
     * @param position
     * @return
     */
    private int countFilledCase(int line, int col, String position) {
        int count = 0;
        if (position.equals("left")  && (plateau.getXY((col - 1), (line + 1)).getContent() != 0)) {
            for (int i = (col - 1); i >= 0; i--) {
                if (plateau.getXY(i, (line + 1)).getContent() != 0) {
                    count++;
                }
            }
        }
        else if (position.equals("right")  && (plateau.getXY((col + 2), (line + 1)).getContent() != 0)) {
            for (int i = (col + 2); i < plateau.getColumn(); i++) {
                if (plateau.getXY(i, (line + 1)).getContent() != 0) {
                    count++;
                }
            }
        }
        return count;
    }

    public static int getSsdlv() {
        return ssdlv;
    }

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
     * DisplayMatrix : affiche la matrice
     */
    public void displayMatrix(){
        for (int i = 0; i < line; i++){
            for (int j = 0; j < column; j++){
                System.err.print(matrix[i][j] + " ");
            }
            System.err.println();
        }
    }

}
