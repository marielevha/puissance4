package engine;

public class Plateau {
    private static final int ALIGN = 4;
    private Line[] lines;
    private static int row, column;
    private static int [] data;
    private static int [][] matrix;
    private boolean end = true;
    private static int lineAdd;

    /**
     *
     * @return
     */
    public int getLineAdd() {
        return lineAdd;
    }

    /**
     *
     */
    public Plateau() {
        this.lines = new Line[1];
        this.lines[0] = new Line();
        //System.out.println(this.tabLigne[1].toString());
    }

    /**
     *
     * @param line
     * @param column
     */
    public Plateau(int line, int column) {
        this.lines = new Line[line];
        for (int i = 0; i < line; i++){
            this.lines[i] = new Line(column);
        }
        //System.out.println(this.tabLigne[0].toString());
    }

    /**
     *
     * @param plateau
     */
    public Plateau(Plateau plateau) {
        this.lines = plateau.lines;
    }

    /**
     *
     * @param string
     */
    public Plateau(String string) {
        //int line, column;
        /*this.tabLigne = new Ligne[column];
        for (int i = 0; i < column; i++){
            this.tabLigne[i] = new Ligne(line);
        }
        //System.out.println(this.tabLigne[0].toString());*/
        // "6x7-211222112211201112210121212000200000000000";
        String[] split0 = string.split("-");
        String[] split1 = split0[0].split("x");

        row = Integer.parseInt(split1[0]);
        column = Integer.parseInt(split1[1]);
        /*System.out.println(split0[0]);
        System.out.println("line : " + line);
        System.out.println("column : " + column);*/

        this.lines = new Line[row];
        int cmp = 0;
        /*for (int i = 0; i < split0[1].length(); i += column){
            this.tabLigne[cmp] = new Ligne(column);
            this.tabLigne[cmp].setLigne(split0[1].substring(i, i + column));
            System.out.println(this.tabLigne[cmp].toString());
            cmp++;
        }*/

        cmp = 0;
        System.err.println("################");

        //String data = "65165189516151351891512189";
        //System.err.println(split0[1].substring(data.length() - 7, 7));

        for (int i = split0[1].length(); i > 0; i -= column){
            //System.err.println(split0[1].substring(i - column, i));
            //System.err.println(i + " " + (i - column));
            this.lines[cmp] = new Line(column);
            this.lines[cmp].setLine(split0[1].substring(i - column, i));
            //System.err.println(this.tabLigne[cmp].toString());
            cmp++;
        }
    }

    /**
     *
     * @param x
     * @param y
     * @return
     */
    public Case getXY(int x, int y){
        return this.lines[y].getX(x);
    }

    /**
     *
     * @param col
     * @return
     */
    public boolean fullColumn(int col){
        if (lines[0].getX(col).getContent() != 0){
            return true;
        }
        else {
            return false;
        }
        //System.err.println(" first : " + tabLigne[0].toString().charAt(col));
        //System.err.println(" second : " + tabLigne[0].getX(col).getContent());
        //return lines[0].getX(col).getContent() != 0;
    }

    /**
     *
     * @param numColumn
     * @param numPlayer
     * @return
     */
    public boolean addPoint(int numColumn, int numPlayer){
        boolean verify = false;
        for (int i =  this.lines.length - 1; i >= 0; i--){
            if (!verify){
                //System.out.print( this.tabLigne[i].toString() + " i : " + i + " ");
                /*for (int j = 0; j < this.tabLigne[i].getTaille(); j++){
                    if (this.tabLigne[i].getX(numColumn).getContent() == 0){
                        this.tabLigne[i].setX(numColumn, numPlayer);
                        verify = true;
                    }
                }*/
                /*for (int j = 0; j <= this.tabLigne[i].getTaille(); j++){
                    System.err.print( this.tabLigne[i].getX(j).getContent() + " j : " + j + " ");
                    if (this.tabLigne[i].getX(numColumn).getContent() == 0){
                        this.tabLigne[i].setX(numColumn, numPlayer);
                        verify = true;
                    }
                }*/
                for (int j = 0; j < this.lines[i].getSize(); j++){
                    //System.err.print(j);
                    //System.err.print("\n");
                    //System.err.print(tabLigne[i].getX(j).getContent() + " : " + j);
                    if (this.lines[i].getX(numColumn).getContent() == 0){
                        this.lines[i].setX(numColumn, numPlayer);
                        lineAdd = i;
                        //System.out.print(this.tabLigne[i].toString());//setX(numColumn, numPlayer);
                        verify = true;
                        if (verify){
                            //System.out.print(checkHorizontal(i));
                            //System.err.print(checkVertical(numColumn));
                            if (checkHorizontal(i, ALIGN) || checkVertical(numColumn, ALIGN)
                                    || checkDiagonal(i, numColumn, ALIGN) || checkReverseDiagonal(i, numColumn, ALIGN)){
                                return true;
                            }
                            else {
                                return noCheck();
                            }

                            /*checkHorizontal(i);
                            checkVertical(numColumn);
                            checkDiagonal(numColumn);*/
                            //checkReverseDiagonal(numColumn);
                        }

                        //checkVertical(numColumn);
                    }
                }
            }
            //System.err.print(this.tabLigne[i].toString());
        }
        return false;
    }

    /**
     *
     * @param line
     * @param MAX_ALIGN
     * @return
     */
    public boolean checkHorizontal(int line, int MAX_ALIGN){
        /*String str = this.tabLigne[line].toString();
        Ligne ligne = new Ligne(str.length());
        ligne.setLine(str);*/
        //System.out.println(this.lines[line].toString());
        return this.lines[line].win(MAX_ALIGN);
    }

    /**
     *
     * @param column
     * @param MAX_ALIGN
     * @return
     */
    public boolean checkVertical(int column, int MAX_ALIGN){
        String str = "";
        for (int i = 0; i < row; i++){
            //System.out.println(this.tabLigne[i].getX(column).getContent());
            str = str + this.lines[i].getX(column).getContent();
        }
        //System.out.print(str);
        Line line = new Line(str.length());
        line.setLine(str);
        //System.err.print(line.toString());
        return line.win(MAX_ALIGN);
    }

    /**
     *
     * @param lgn
     * @param col
     * @param MAX_ALIGN
     * @return
     */
    public boolean checkDiagonal(int lgn, int col, int MAX_ALIGN){
        if (lgn >= 0 && lgn < lines.length && col >= 0 && col < lines[0].getSize()) {
            StringBuilder stringBuilder = new StringBuilder();
            String str1 = "";
            String str2 = "";
            int tempCol = col - 1;

            for (int i = lgn; i < lines.length; i++) {
                if (col >= 0 && col < lines.length + 1 && col < lines[0].getSize() + 1) {
                    str1 += String.valueOf(this.lines[i].getX(col).getContent());
                    col++;
                }
            }
            for (int i = (lgn - 1); i >= 0; i--) {
                if (tempCol >= 0) {
                    stringBuilder.append(String.valueOf(this.lines[i].getX(tempCol).getContent()));
                    tempCol--;
                }
            }

            str2 = stringBuilder.reverse() + str1;
            //System.out.println(str2);
            if (str2.length() >= MAX_ALIGN) {
                Line line = new Line(str2.length());
                line.setLine(str2);
                return line.win(MAX_ALIGN);
            }
        }
        return false;
    }

    /**
     *
     * @param lgn
     * @param col
     * @param MAX_ALIGN
     * @return
     */
    public boolean checkReverseDiagonal(int lgn, int col, int MAX_ALIGN){
        if (lgn >= 0 && lgn < lines.length && col >= 0 && col < lines[0].getSize()) {
            StringBuilder stringBuilder = new StringBuilder();
            String str1 = "";
            String str2 = "";
            int tempCol = col + 1;

            for (int i = lgn; i < lines.length; i++) {
                if (col >= 0 && col < lines.length + 1 && col < lines[0].getSize() + 1) {
                    str1 += String.valueOf(this.lines[i].getX(col).getContent());
                    col--;
                }
            }
            for (int i = (lgn - 1); i >= 0; i--) {
                if (tempCol >= 0 && tempCol < lines[0].getSize()) {
                    stringBuilder.append(String.valueOf(this.lines[i].getX(tempCol).getContent()));
                    tempCol++;
                }
            }
            str2 = stringBuilder.reverse() + str1;
            //System.out.println(str2);
            if (str2.length() >= MAX_ALIGN) {
                Line line = new Line(str2.length());
                line.setLine(str2);
                return line.win(MAX_ALIGN);
            }
        }
        return false;
    }

    /**
     *
     * @return
     */
    public boolean noCheck(){
        for (int k = 0; k < row; k++){
            for (int l = 0; l < column; l++){
                //System.out.print(this.tabLigne[k].getX(l).getContent());
                if (this.lines[k].getX(l).getContent() == 0){
                    end = false;
                }
            }
        }
        //System.out.print(end);
        return end;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        String str = "|---------------|" + "\n";
        for (int i = 0; i < row; i++){
            str = str + "| " + this.lines[i].toString() + "|" + "\n";
        }
        str += "|---------------|";
        return str;//.replace('0','0');
    }

    /**
     *
     * @return
     */
    public String toStringIA() {
        String str = row + "x" + column + "-";
        for (int i = row; i > 0; i--){
            str = str + this.lines[i - 1].toString();
        }
        return str.replace(" ","");
    }

    public void win(){
        getLineColumn();
        Win win = new Win();
        win.BuildMatrix(this.lines, data);
        /*matrix = new int[line][column];
        for (int i = 0; i < line; i++){
            for (int j = 0; j < column; j++){
                matrix[i][j] = this.tabLigne[i].getX(j).getContent();
            }
        }

        for (int i = 0; i < line; i++){
            for (int j = 0; j < column; j++){
                System.err.print(matrix[i][j]);
            }
            System.err.println();
        }*/
    }

    /**
     *
     * @return
     */
    public int [] getLineColumn() {
        data = new int[2];
        data[0] = row;
        data[1] = column;
        return data;
    }
}
