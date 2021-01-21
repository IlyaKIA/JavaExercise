import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Random random = new Random();
        Scanner scanner = new Scanner(System.in);
        int number = random.nextInt(10);
        int userNumber = -1;
        for (int i = 0; i < 3; i++) {
            do {
                System.out.print("Попытка " + (i + 1) + " из 3. Угадайте число от 0 до 9: ");

                if (scanner.hasNextInt()) {
                    userNumber = scanner.nextInt();
                } else  {
                    System.out.println("Введите корректное число.");
                }
                scanner.nextLine();
            } while (userNumber < 0 || userNumber > 9);
            if (userNumber == number) {
                System.out.println("Поздравляю, Вы угадали!");
                break;
            } else if (userNumber < number) {
                System.out.println("Ваше число меньше загаданного.");
            } else {
                System.out.println("Ваше число больше загаданноываго.");
            }
            if (i == 2) {
                System.out.println("Вы проиграли.");
            }
        }
        scanner.close();
    }
}
