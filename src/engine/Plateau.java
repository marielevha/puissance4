package engine;

import _interface.IPlateau;

import java.util.ArrayList;

public class Plateau implements IPlateau {
    private static final int ALIGN = 4, EMPTY = 0;
    private final Line[] lines;
    private static int row, column;
    private static int lineAdd;
    ArrayList<Integer> choices = new ArrayList<Integer>();

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
        String[] split0 = string.split("-");
        String[] split1 = split0[0].split("x");

        row = Integer.parseInt(split1[0]);
        column = Integer.parseInt(split1[1]);

        this.lines = new Line[row];
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
     * @return boolean true si colonne pleine, sinon false
     */
    public boolean fullColumn(int col) {
        boolean available = true;
        for (int i = 0; i < row; i++) {
            if (lines[i].getX(col).getContent() == EMPTY) {
                available = false;
            }
        }
        return available;
        //return lines[0].getX(col).getContent() != 0;
    }

    /**
     * AddPoint : ajoute un pion du joueur(numéro) dans la colonne indiquée
     * @param column
     * @param player
     * @return bool
     */
    public boolean addPoint(int column, int player) {
        for (int i = (row - 1); i >= 0; i--) {
            if (lines[i].getX(column).getContent() == EMPTY) {
                lines[i].getX(column).setContent(player);
                lineAdd = i;
                return verify(i, column);
            }
        }
        return false;
    }

    private boolean verify(int line, int column) {
        return checkHorizontal(line, ALIGN) || checkVertical(column, ALIGN)
                || checkDiagonal(line, column, ALIGN) || checkReverseDiagonal(line, column, ALIGN);
    }

    /**
     * CheckHorizontal : verifie s'il y'a un alignement horizontal
     * retourne true s'il y a un gagnant sinon false
     * @param line
     * @param MAX_ALIGN
     * @return boolean
     */
    public boolean checkHorizontal(int line, int MAX_ALIGN) {
        /*String str = this.tabLigne[line].toString();
        Ligne ligne = new Ligne(str.length());
        ligne.setLine(str);*/
        //System.out.println(this.lines[line].toString());
        return this.lines[line].win(MAX_ALIGN);
    }

    /**
     * CheckVertical : construis une verticale et verifie s'il y'a un alignement
     * retourne true s'il y a un gagnant, sinon false
     * @param column
     * @param MAX_ALIGN
     * @return boolean
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
     *  CheckDiagonal : construis une line à partir de la diagonale et verifie s'il y'a un alignement
     *  retourne true s'il y a un gagnant sinon false
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
     *   CheckReverseDiagonal : construis une line à partir de la diagonale inverse et verifie s'il y'a un alignement
     *   retourne true s'il y a un gagnant sinon false
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

    /**
     *  NoCheck : vérifie si le plateau est plein
     * @return boolean
     */
    @Override
    public boolean full() {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                //System.out.print(this.lines[i].getX(j).getContent());
                if (lines[i].getX(j).getContent() == 0) {
                    return false;
                }
            }
        }
        return true;
    }

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

    /**
     * AvailableColumn : retourne la liste des colonnes disponible
     * @return ArrayList choices
     */
    public ArrayList<Integer> availableColumn() {
        choices.clear();
        for (int i = 0; i < column; i++) {
            if (!fullColumn(i)) {
                choices.add(i);
            }
        }
        return choices;
    }

    /**
     * GetLineAdd : retourne la ligne ou le pion est ajouté
     * @return lineAdd
     */
    public int getLineAdd() {
        return lineAdd;
    }

    /**
     * TotalPoints : compte le nombre de pions dans le plateau
     * @return Integer count
     */
    public int totalPoints() {
        int count = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if (lines[i].getX(j).getContent() != EMPTY) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * CancelMove : annule un coup jouer en remplaçant le numéro du joueur par 0
     * @param column
     */
    public void cancelMove(int column) {
        int row = (Plateau.row - 1);
        while (lines[row].getX(column).getContent() == EMPTY) {
            row--;
        }
        lines[row].getX(column).setContent(EMPTY);
    }

    public Line test(int l) {
        return lines[l];
    }
}
