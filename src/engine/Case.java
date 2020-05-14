package engine;

public class Case {
    private int content;

    /**
     * Constructor : construit une case vide
     */
    public Case() {
        this.content = 0;
    }

    /**
     * Constructor : construit une case contenant une valeur entré en paramètre
     * @param content
     */
    public Case(int content) {
        if (content == 0 || content == 1 || content == 2){
            this.content = content;
        }
        else {
            /*System.err.println("Argument invalid !!");
            System.exit(0);*/
            this.content = 0;
        }
    }

    /**
     * Constructor Copy : construit une case à partir d'une case existante un objet ligne
     * @param aCase
     */
    public Case(Case aCase) {
        this.content = aCase.content;
    }

    /**
     * GetContent : retourne le contenu d'une case
     * @return Integer content
     */
    public int getContent() {
        return this.content;
    }

    /**
     * SetContent : met à jour le contenu d'une case
     * @param content
     */
    public void setContent(int content) {
        if (content == 0 || content == 1 || content == 2){
            this.content = content;
        }
        else {
            /*System.err.println("Argument invalid !!");
            System.exit(0);*/
            this.content = 0;
        }
    }

    /**
     * ToString : retourne une description formmatée de l'objet
     * @return String
     */
    @Override
    public String toString() {
        return String.valueOf(this.getContent());
        /*if (content == 0){
            return "" + 0;
        }
        else {
            return ""+content;
        }*/
    }
}
