package engine;

public class Line {
    private Case [] cases;
    private static boolean state_string = true;

    public Line() {
        this.cases = new Case[1];
        this.cases[0] = new Case();
    }

    public Line(int size) {
        this.cases = new Case[size];
        for (int i = 0; i < size; i++){
            this.cases[i] = new Case();
        }
        /*for (Case aCase : tabCases){
            System.err.println(tabCases[0].getContent());
        }*/
        //this.tabCases = tabCases;
    }

    public Line(Line ligne){
        this.cases = ligne.cases;
    }

    public Case getX(int i){
        return cases[i];
    }
    public void setX(int i, int value){
        if (value == 0 || value == 1 || value == 2){
            cases[i].setContent(value);
        }
        else {
            System.err.println("Argument invalid !!");
            System.exit(0);
        }
    }
    public void setLine(String string){
        //System.err.println(this.tabCases.length);
        //System.err.println(string.length());
        if (string.length() == this.cases.length){
            for (int i = 0; i < string.length(); i++){
                //System.out.println(string.charAt(i));
                if (string.charAt(i) != '0' && string.charAt(i) != '1' && string.charAt(i) != '2'){
                    state_string = false;
                    System.err.println("Success false!");
                }
            }

            if (state_string){
                for (int i = 0; i < this.cases.length; i++){
                    //this.tabCases[i].setContent(string.charAt(i));
                    //System.out.println();
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
        /*if (string.equals('0') || string.equals('1') ||string.equals('2')){
            System.out.println("ValInit : " + string);
        }
        else {
            System.err.println("Error ValInit : " + string);
        }*/
    }
    public int getSize(){
        return this.cases.length;
    }
    public boolean win(int align){
        switch (align) {
            case 3 : {
                for (int i = (cases.length - 1); i >= 2; i--){
                    if (cases[i].getContent() != 0){
                        if (cases[i].getContent() == cases[i - 1].getContent()
                                && cases[i].getContent() == cases[i - 2].getContent()
                        ){
                            if ((cases[i - 1].getContent()
                                    + cases[i].getContent() + cases[i - 2].getContent()) == 6){
                                return true;
                            }
                        }
                    }
                }
            }
            case 4 : for (int i = (cases.length - 1); i >= 3; i--){
            /*System.out.println(tabCases[i].getContent() + " " + tabCases[i - 1].getContent() + " " + tabCases[i - 2].getContent()
                    + " " + tabCases[i - 3].getContent()
            );*/
                if (cases[i].getContent() != 0){
                    if (cases[i].getContent() == cases[i - 1].getContent()
                            && cases[i].getContent() == cases[i - 2].getContent()
                            && cases[i].getContent() == cases[i - 3].getContent()
                    ){
                        return true;
                    }
                }
            }
            default : return false;
        }
        /*if (align == 4) {

        }
        else if (align == 3) */
        //return false;
    }

    @Override
    public String toString() {
        String str = "";
        for (int i = 0; i < this.cases.length; i++){
            //System.out.print(this.tabCases[i].getContent());
            str += this.cases[i].getContent() + " ";
        }
        return str;
        /*return "Ligne{" +
                "tabCases=" + Arrays.toString(tabCases) +
                '}';*/
    }
}
