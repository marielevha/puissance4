package engine;

import ia.IAMariel;

public class Line {
    private Case [] cases;
    private static boolean state_string = true;

    /**
     * Constructor : construit une ligne d'une case vide
     */
    public Line() {
        this.cases = new Case[1];
        this.cases[0] = new Case();
    }

    /**
     * Constructor : construit une ligne vide selon une taille définie
     * Ligne initialisée avec des cases vides
     * @param size
     */
    public Line(int size) {
        this.cases = new Case[size];
        for (int i = 0; i < size; i++){
            this.cases[i] = new Case();
        }
    }

    /**
     * Constructor Copy : clonne un objet ligne
     * @param ligne
     */
    public Line(Line ligne){
        this.cases = ligne.cases;
    }

    /**
     * GetX : retourne une case d'une position donnée
     * @param i : position
     * @return Case
     */
    public Case getX(int i){
        return cases[i];
    }

    /**
     * SetX : modifie le contenu d'une case sur une position donnée
     * @param i : position
     * @param value : valeur
     */
    public void setX(int i, int value) {
        if (value == 0 || value == 1 || value == 2) {
            cases[i].setContent(value);
        }
        else {
            /*System.err.println("Argument invalid !!");
            System.exit(0);*/
            cases[i].setContent(0);
        }
    }

    /**
     * SetLine : modifie le contenu d'une line à partir d'une chaine
     * @param string
     */
    public void setLine(String string) {
        if (string.length() == this.cases.length) {
            for (int i = 0; i < string.length(); i++) {
                if (string.charAt(i) != '0' && string.charAt(i) != '1' && string.charAt(i) != '2') {
                    state_string = false;
                    System.err.println("Success false!");
                }
            }

            if (state_string) {
                for (int i = 0; i < this.cases.length; i++) {
                    this.cases[i].setContent(Character.getNumericValue(string.charAt(i)));
                }
            }
            else {
                System.out.println("Error !");
                System.exit(0);
            }
        }
        else {
            System.out.println("Error !");
            System.exit(0);
        }
    }

    /**
     * GetSize : retourne la taille de la ligne
     * @return size
     */
    public int getSize(){
        return this.cases.length;
    }

    /**
     * Win : vérifie s'il y a un alignement de case de meme valeur
     * @param align : limite d'alignement
     * @return true s'il y a alignement
     */
    public boolean win(int align) {
        if (align == 3) {
            for (int i = (cases.length - 1); i >= 2; i--) {
                if (cases[i].getContent() != 0) {
                    if (cases[i].getContent() == cases[i - 1].getContent()
                            && cases[i].getContent() == cases[i - 2].getContent()
                    ){
                        int test = (IAMariel.getSsdlv() == 1 ? 3 : 6);
                        //System.out.println(test);
                        if ((cases[i - 1].getContent()
                                + cases[i].getContent() + cases[i - 2].getContent()) == test){
                            //System.err.println(cases[i].getContent() + cases[i - 1].getContent() + cases[i - 2].getContent());
                            return true;
                        }
                    }
                }
            }
        }
        else {
            for (int i = (cases.length - 1); i >= 3; i--){
                if (cases[i].getContent() != 0){
                    if (cases[i].getContent() == cases[i - 1].getContent()
                            && cases[i].getContent() == cases[i - 2].getContent()
                            && cases[i].getContent() == cases[i - 3].getContent()
                    ){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * ToString : retourne une description formmatée de l'objet
     * @return string
     */
    @Override
    public String toString() {
        String str = "";
        for (int i = 0; i < this.cases.length; i++){
            str += this.cases[i].getContent() + " ";
        }
        return str;
    }
}