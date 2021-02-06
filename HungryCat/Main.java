import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        int addFood = 0;
        Cat cat []= {new Cat("Вася", 10),
            new Cat("Кузьма", 15),
            new Cat("Sheba", 7)};
        Plate plate = new Plate(30);
        plate.info();
        cat[0].eat(plate);
        cat[1].eat(plate);
        cat[2].eat(plate);

        for (int i = 0; i < 3; i++) {
            if (cat[i].getSatiety()) {
                cat[i].isSatiety();
            } else {
                cat[i].isSatiety();
                plate.addFood();
                cat[i].eat(plate);
                cat[i].isSatiety();
            }
        }
        plate.info();

    }
}
