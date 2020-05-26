package engine.player;

import old.TypePlayer;

public class Player {
    public TypePlayer type;
    public int number, level, depth;
    private String typePlayer;

    public Player() {
        depth = 8;
    }

    public Player(int number) {
        this.number = number;
        this.typePlayer = "Real Player !";
        //If IA level <= 6
        //if (number == 2) {
        //}
    }
    public int getNumber() {
        return number;
    }
    public void setNumber(int number) {
        this.number = number;
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

    public int getDepth() {
        return depth;
    }
    public void setDepth(int depth) {
        this.depth = depth;
    }

    public String getTypePlayer() {
        return typePlayer;
    }
    public void setTypePlayer(String typePlayer) {
        this.typePlayer = typePlayer;
    }

    public String toString(){
        return ("Player "+ number +" ");
    }
}
