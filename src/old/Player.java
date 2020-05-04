package old;

public class Player {
    public TypePlayer type;
    public int number;
    public int level;

    public Player(int number) {
        this.number = number;
        //If IA level <= 6
        if (number == 2) {
            setLevel(6);
        }
    }
    public int getNumber() {
        return number;
    }
    public TypePlayer getType(){
        return type;
    }
    public void setType(TypePlayer type){
        this.type = type;
    }
    public void setLevel(int level){
        this.level = level;
    }
    public int getLevel(){
        return level;
    }
    public String toString(){
        return ("Joueur "+ number +" ");
    }
}