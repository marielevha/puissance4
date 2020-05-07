package engine;

import _interface.IPlateau;

public class Plateau implements IPlateau {
    private static final int ALIGN = 4;
    private Line[] lines;
    private static int row, column;
    private static int [] data;
    private static int [][] matrix;
    private boolean end = true;
    private static int lineAdd;

    /**
     * GetLineAdd : retourne la ligne ou le pion est ajouté
     * @return lineAdd
     */
    public int getLineAdd() {
        return lineAdd;
    }

    /**
     * Constructor : construit un plateau d'une case vide
     */
    public Plateau() {
        this.lines = new Line[1];
        this.lines[0] = new Line();
    }

    /**
     * Constructor : construit un plateau vide selon une des dimensions définies
     * Plateau initialisé avec des cases vides
     * @param line
     * @param column
     */
    public Plateau(int line, int column) {
        Plateau.row = line;
        Plateau.column = column;
        lines = new Line[line];
        for (int i = 0; i < line; i++){
            lines[i] = new Line(column);
        }
    }

    /**
     * Constructor Copy : clonne un objet plateau
     * @param plateau
     */
    public Plateau(Plateau plateau) {
        this.lines = plateau.lines;
    }

    /**
     * Constructor : construit un plateau à partir d'un string
     * @param direction : détermine le sens de construction du plateau
     * @param string : format "6x7-211222112211201112210121212000200000000000"
     */
    public Plateau(String string, int direction) {
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
        //System.err.println("################");
        int cmp = 0;
        if (direction == 0){
            for (int i = split0[1].length(); i > 0; i -= column){
                //System.err.println(split0[1].substring(i - column, i));
                //System.err.println(i + " " + (i - column));
                this.lines[cmp] = new Line(column);
                this.lines[cmp].setLine(split0[1].substring(i - column, i));
                //System.err.println(this.tabLigne[cmp].toString());
                cmp++;
            }
        }
        else {
            for (int i = 0; i < split0[1].length(); i += column){
                this.lines[cmp] = new Line(column);
                this.lines[cmp].setLine(split0[1].substring(i, i + column));
                //System.out.println(this.lines[cmp].toString());
                cmp++;
            }
        }




        //String data = "65165189516151351891512189";
        //System.err.println(split0[1].substring(data.length() - 7, 7));

    }

    /**
     * GetXY : retourne la case se trouvant aux coordonnées X-Y
     * @param x
     * @param y
     * @return Case
     */
    public Case getXY(int x, int y){
        return this.lines[y].getX(x);
    }

    /**
     * FullColumn : vérifie diponibilité de la colonne
     * @param col
     * @return true si colonne pleine, sinon false
     */
    public boolean fullColumn(int col) {
        if (lines[0].getX(col).getContent() != 0) {
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
     * AddPoint : ajoute un pion du joueur(numéro) dans la colonne indiquée
     * @param numColumn
     * @param numPlayer
     * @return bool
     */
    public boolean addPoint(int numColumn, int numPlayer) {
        boolean verify = false;
        for (int i =  this.lines.length - 1; i >= 0; i--) {
            if (!verify) {
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
                for (int j = 0; j < this.lines[i].getSize(); j++) {
                    //System.err.print(j);
                    //System.err.print("\n");
                    //System.err.print(tabLigne[i].getX(j).getContent() + " : " + j);
                    if (this.lines[i].getX(numColumn).getContent() == 0) {
                        this.lines[i].setX(numColumn, numPlayer);
                        lineAdd = i;
                        //System.out.print(this.tabLigne[i].toString());//setX(numColumn, numPlayer);
                        verify = true;
                        if (verify) {
                            //System.out.print(checkHorizontal(i));
                            //System.err.print(checkVertical(numColumn));
                            if (checkHorizontal(i, ALIGN) || checkVertical(numColumn, ALIGN)
                                    || checkDiagonal(i, numColumn, ALIGN) || checkReverseDiagonal(i, numColumn, ALIGN)) {
                                return true;
                            }
                            else {
                                return full();
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
     * CheckHorizontal :
     * @param line
     * @param MAX_ALIGN
     * @return bool
     */
    public boolean checkHorizontal(int line, int MAX_ALIGN) {
        /*String str = this.tabLigne[line].toString();
        Ligne ligne = new Ligne(str.length());
        ligne.setLine(str);*/
        //System.out.println(this.lines[line].toString());
        return this.lines[line].win(MAX_ALIGN);
    }

    /**
     * CheckVertical :
     * @param column
     * @param MAX_ALIGN
     * @return bool
     */
    public boolean checkVertical(int column, int MAX_ALIGN) {
        String str = "";
        for (int i = 0; i < row; i++) {
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
     *  CheckDiagonal :
     * @param lgn
     * @param col
     * @param MAX_ALIGN
     * @return bool
     */
    public boolean checkDiagonal(int lgn, int col, int MAX_ALIGN) {
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
     *   CheckReverseDiagonal :
     * @param lgn
     * @param col
     * @param MAX_ALIGN
     * @return bool
     */
    public boolean checkReverseDiagonal(int lgn, int col, int MAX_ALIGN) {
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


    /*public boolean full() {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                //System.out.print(this.tabLigne[i].getX(j).getContent());
                if (lines[i].getX(j).getContent() == 0) {
                    return false;
                }
            }
        }
        return true;
    }*/
    /**
     *  NoCheck : vérifie si le plateau est plein
     * @return boolean
     */
    @Override
    public boolean full() {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                //System.out.print(this.tabLigne[i].getX(j).getContent());
                if (lines[i].getX(j).getContent() == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    /*public void empty() {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                lines[i].getX(j).setContent(0);
            }
        }
    }*/

    /**
     * Empty : vider plateau
     */
    @Override
    public void empty() {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                lines[i].getX(j).setContent(0);
            }
        }
    }

    /**
     * ToString : retourne une description formatée de l'objet
     * @return string
     */
    @Override
    public String toString() {
        String str = "|---------------|" + "\n";
        for (int i = 0; i < row; i++) {
            str = str + "| " + lines[i].toString() + "|" + "\n";
        }
        str += "|---------------|";
        return str;//.replace('0','0');
    }

    /**
     * ToStringIA : retourne une description formatée de l'objet
     * Format : 6x7-211222112211201112210121212000200000000000
     * @return string
     */
    public String toStringIA() {
        String str = row + "x" + column + "-";
        for (int i = row; i > 0; i--) {
            str = str + lines[i - 1].toString();
        }
        return str.replace(" ","");
    }

    public void win() {
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
     * GetLineColumn : retourne le nombre de lignes et de colonnes
     * @return [] int
     */
    public int [] getLineColumn() {
        data = new int[2];
        data[0] = row;
        data[1] = column;
        return data;
    }

    /**
     * GetLine : retourne nombre de lignes
     * @return row : Integer
     */
    public int getLine() {
        return row;
    }

    /**
     * GetColumn : retourne nombre de colonnes
     * @return column : Integer
     */
    public int getColumn() {
        return column;
    }
}
