import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        System.out.println("Task 1:");
        Integer [] arr1 = new Integer [] {1, 2, 3, 4, 5};
        String[] arr2 = new String[] {"One", "Two", "Three"};

        for(Integer i : arr1) {
            System.out.print(i + ", ");
        }
        System.out.println();
        swapArrElement(arr1, 0, 4);

        for(Integer i : arr1) {
            System.out.print(i + ", ");
        }
        System.out.println();

        for(String i : arr2) {
            System.out.print(i + ", ");
        }
        System.out.println();
        swapArrElement(arr2, 0, 2);

        for(String i : arr2) {
            System.out.print(i + ", ");
        }
        System.out.println();
        System.out.println();
        System.out.println("Task 2:");
        System.out.println(transformToArrList(arr1));
        System.out.println(transformToArrList(arr2));
        System.out.println();

        //Задание 3:
        System.out.println("Task 3:");
        Box<Orange> orangeBox = new Box<>(new ArrayList <Orange>() {
            {
                add(new Orange());
                add(new Orange());
                add(new Orange());
                add(new Orange());
            }
        });

        Box<Apple> appleBox = new Box<>();
        appleBox.addFruit(new Apple());
        appleBox.addFruit(new Apple());
        appleBox.addFruit(new Apple());
        appleBox.addFruit(new Apple());

        System.out.println("Apple box weight: " + appleBox.getWeight());
        System.out.println("Orange box weight: " + orangeBox.getWeight());
        System.out.println("Is Orange weight = Apple weight: " + orangeBox.weightCompare(appleBox));

        Box<Apple> appleBox2 = new Box<>();
        appleBox2.addFruit(new Apple());
        appleBox2.addFruit(new Apple());
        appleBox2.addFruit(new Apple());

        System.out.println("Apple box weight: " + appleBox.getWeight());
        System.out.println("Apple box2 weight: " + appleBox2.getWeight());
        appleBox.fillIn(appleBox2);
        System.out.println("Apple box weight: " + appleBox.getWeight());
        System.out.println("Apple box2 weight: " + appleBox2.getWeight());
    }

//Задание 1:
    public static <E> void swapArrElement ( E[] arr, int a, int b){
        try{
            E temp = arr[a];
            arr[a] = arr[b];
            arr[b] = temp;
        } catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Argument 'a' or 'b' out of array index");
        }
    }
//Задание 2:
    public static <E> ArrayList transformToArrList (E[] arr){
        ArrayList <E> list = new ArrayList<>();
        for (E temp : arr){
            list.add(temp);
        }
        return list;
    }
}
