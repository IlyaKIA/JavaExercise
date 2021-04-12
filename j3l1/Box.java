import java.util.ArrayList;
import java.util.Collection;

public class Box <F extends Fruit> {
    ArrayList<F> fruit = new ArrayList<>();

    public Box() {
    }

    public Box(ArrayList<F> fruit) {
        this.fruit = fruit;
    }

    public void addFruit (F f){
        fruit.add(f);
    }

    public float getWeight(){
        float weight = 0f;
        for (int i = 0; i < fruit.size(); i++) {
            weight += fruit.get(i).getWeight();
        }
        return weight;
    }

    public boolean weightCompare (Box<?> box) {
        if (Math.abs(getWeight() - box.getWeight()) < 0.0001) return true;
        return false;
    }

    public int getCount(){
        return fruit.size();
    }

    public void fillIn (Box<F> box){
        for (int i = 0; i < fruit.size(); i++) {
            box.addFruit(fruit.get(i));
        }
        fruit.clear();
    }

}
