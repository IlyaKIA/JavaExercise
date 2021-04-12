import java.lang.reflect.Array;

public class Main {
    public static boolean err;

    public static void main(String[] args) {
        String[][] arr = new String[][]{{"5", "4", "3", "4"}, {"2", "6", "8", "6"}, {"2", "-5", "8", "5"}, {"0", "6", "8" , "5"}};
        int sum = sumArr(arr);
        if (!err) System.out.println( "Сумма массива = " + sumArr(arr));
    }

    public static int sumArr(String[][] arr) {
        int sum = 0;
        int i = 0;
        int j = 0;
        try {
            for (String[] ar : arr) {
                if (ar.length != 4 || arr.length != 4) {
                    throw new MyArraySizeException();
                }
            }
            for (i = 0; i < arr.length; i++) {
                for (j = 0; j < arr[i].length; j++) {
                    sum += Integer.parseInt(arr[i][j]);
                }
            }

        } catch(MyArraySizeException e){
            System.out.println("Проверь размер массива.");
            err = true;
        } catch (NumberFormatException e){
            try {
                throw new MyArrayDataException(i, j);
            }catch (MyArrayDataException e1){
                System.out.println("\nСимвол '" + arr[i][j] + "' не соответствует числу.");
                err = true;
            }
        }
        return sum;
    }

    public static class MyArraySizeException extends NegativeArraySizeException{}

    public static class MyArrayDataException extends NumberFormatException{
        int i;
        int j;
        public MyArrayDataException(int i, int j) {
            this.i = i;
            this.j = j;
            System.out.printf("Ошибка!\nВ массиве по адресу [%d][%d] находитс не корректное значение.; ", i + 1, j + 1);
        }
    }
}
