public class Cat {
    private String name;
    private int appetit;
    private boolean satiety = false;
    public Cat (String name, int appetit){
        this.appetit = appetit;
        this.name = name;
    }
    public void eat (Plate p){

        if(p.getFood() > appetit){
            p.decreaseFood(appetit);
            satiety = true;
        }
    }
    public void isSatiety(){
        if (satiety) {
            System.out.println("Кот " + name + " сыт.");
        } else {
            System.out.println("Кот " + name + " голоден.");
        }
    }

    public boolean getSatiety (){
        return satiety;
    }
}
