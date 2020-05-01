package engine;

public class Case {
    private int content;

    public Case() {
        this.content = 0;
    }

    public Case(int content) {
        if (content == 0 || content == 1 || content == 2){
            this.content = content;
        }
        else {
            System.err.println("Argument invalid !!");
            System.exit(0);
        }
    }

    public Case(Case aCase) {
        this.content = aCase.content;
    }

    public int getContent() {
        return this.content;
    }

    public void setContent(int content) {
        if (content == 0 || content == 1 || content == 2){
            this.content = content;
        }
        else {
            this.content = 0;
        }
    }

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
