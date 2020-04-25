package engine;

public class Case {
    private int content;

    /**
     *
     */
    public Case() {
        this.content = 0;
    }

    /**
     *
     * @param content
     */
    public Case(int content) {
        if (content == 0 || content == 1 || content == 2){
            this.content = content;
        }
        else {
            System.err.println("Argument invalid !!");
            System.exit(0);
        }
    }

    /**
     *
     * @param aCase
     */
    public Case(Case aCase) {
        this.content = aCase.content;
    }

    /**
     *
     * @return
     */
    public int getContent() {
        return this.content;
    }

    /**
     *
     * @param content
     */
    public void setContent(int content) {
        if (content == 0 || content == 1 || content == 2){
            this.content = content;
        }
        else {
            this.content = 0;
        }
    }

    /**
     *
     * @return
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
