import java.util.Scanner;

public class Plate {


    private int food;
    public Plate (int food){
        this.food = food;
    }
    public void decreaseFood (int n) {
        food -= n;
    }
    public void info (){
        System.out.println("В миске: " + food + "шт.");
    }

    public int getFood (){
        return food;
    }

    public void addFood() {
        int addF = 0;
        Scanner scanner = new Scanner(System.in);
        System.out.print("Не хватило еды. Укажите сколько еды добавить в миску: ");
        if (scanner.hasNextInt()){
            addF = scanner.nextInt();
        }
        scanner.nextLine();
        food = food + addF;
    }

}
