package engine;

import ia.IAMariel;

import java.util.Random;

public class Game {
    private static Random random = new Random();
    public static void main(String[] args) {
        /*for (int i = 0; i <= 10; i++){
            System.out.println(random.nextInt((7 - 1) + 1) + 1);
        }*/
        /*String string = "6x7-211022112211201112210121212000200001210121";
        string = "6x7-211222112211211112210121212000000000000000";
        Plateau plateau = new Plateau(string);w
        IAMariel mariel = new IAMariel();
        for (int i = 0; i <= 10; i++){
            System.out.println(mariel.levelOneMove(plateau));
        }
        System.out.println(mariel.levelOneMove(plateau));*/
        //Case();
        String string = "6x7-211022112211201112210121212000200001210121";
        string = "6x7-" +
                "1121222" +
                "1101000" +
                "1201000" +
                "2001000" +
                "0002000" +
                "0000000";
        Plateau plateau = new Plateau(string);
        //Plateau plateau = new Plateau(string.replace('0','2'));
        System.out.print(plateau.toString());

        System.out.print('\n');
        String test = "211222112211201112220121212000200000000000";
        //System.out.print(plateau.fullColumn(1));

        //plateau.addPoint(1,1);
        //plateau.addPoint(1,2);
        //plateau.getXY(2, 1).setContent(0);
        //System.out.println(plateau.toString());
        //System.err.println(plateau.toStringIA().replace(" ",""));
        //String stringIA = plateau.toStringIA();
        //System.err.println(stringIA);
        //Plateau plateau1 = new Plateau(stringIA);
        //System.err.println(plateau1.toString());
        //System.out.println(plateau.fullColumn(1));


        IAMariel mariel = new IAMariel(4);
        mariel.buildMatrix(6, 7);
        mariel.readMatrix();
        String max = mariel.max(plateau);
        System.out.println(max);
        //System.out.println(mariel.bestMove(plateau));
        //int result = mariel.levelThreeMove(plateau);
        //System.out.println(result);
        //System.err.println(plateau.addPoint(result, 2));
        //System.err.println(plateau.toString());
        //System.out.println(mariel.levelThreeMove(plateau));
        //System.err.println(plateau.checkHorizontal(plateau.getLineAdd()));*/

        //plateau.getXY(0, 1).setContent(0);
        //System.err.println(plateau.toString());

        /*
        System.err.println(plateau.toString());

        //System.err.print(plateau.checkVertical(5));

        System.out.print(plateau.checkVertical(3));
        //plateau.win();
        //plateau.checkDiagonal(3, 4);
        //plateau.checkReverseDiagonal(4,5);
        /*plateau.checkReverseDiagonal(2,6);
        plateau.checkReverseDiagonal(2,5);
        plateau.checkReverseDiagonal(2,4);
        plateau.checkReverseDiagonal(2,3);
        plateau.checkReverseDiagonal(2,2);
        plateau.checkReverseDiagonal(2,1);
        plateau.checkReverseDiagonal(2,0);*/
        //plateau.noCheck();

        //System.err.println(plateau.getXY(2,1));
        //System.err.println(plateau.getXY(2,1));
        //System.err.println(plateau.getXY(3,1));
        /*int row = 6;
        int column = 6;

        int [][] map = new int [row][column];
        Random random = new Random();

        //load maps '.'
        for (int i = 0; i < row; i++){
            for (int j = 0; j < column; j++){
                map[i][j] = random.nextInt(10);
            }
        }

        for (int i = 0; i < row; i++){
            for (int j = 0; j < column; j++){
                System.out.print(map[i][j] + " - ");
            }
            System.out.println();
        }

        getXY(2,3, map);*/
        //Line();
    }
    private static int getRandomNumberInRange(int min, int max) {
        /*if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }
        Random r = new Random();*/
        return random.nextInt((max - min) + 1) + min;
    }
    public static int getXY(int x, int y, int [][] map){
        /*for (int i = 0; i < x; i++){
            for (int j = 0; j < y; j++){
                //System.err.print(map[x - 1][y - 1] + " - ");
            }
            System.err.println();
            System.err.print(map[x - 1][y - 1] + " - ");
        }*/
        System.err.print(map[x][y]);
        return 0;
    }
    public static void Case(){
        Case aCase = new Case();
        System.out.println(aCase.getContent());

        Case aCase1 = new Case(2);
        System.out.println(aCase1.getContent());

        Case aCase2 = new Case(aCase1);
        System.out.println(aCase2.getContent());

        System.err.println(aCase.toString());
        System.err.println(aCase1.toString());
        System.err.println(aCase2.toString());
    }
    public static void Line(){
        String s = "222212";
        Line ligne = new Line(s.length());
        ligne.setLine(s);
        //ligne.win();
        //System.out.print(ligne.toString());
        System.out.print(ligne.win(4));
        //System.err.println(ligne.getX(0));
        //System.err.println(ligne.toString());
    }
}
