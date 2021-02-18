public class Wall implements Obstruction{
    private int heightWall;

    public Wall (int heightWall){
        this.heightWall = heightWall;
    }

    public int getLength (){
        return heightWall;
    }
}
