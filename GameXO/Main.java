import java.util.Arrays;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Random random = new Random();
        Scanner scanner = new Scanner(System.in);
        String[] words = {"apple", "orange", "lemon", "banana", "apricot",
                "avocado", "broccoli", "carrot", "cherry", "garlic",
                "grape", "melon", "leak", "kiwi", "mango", "mushroom",
                "nut", "olive", "pea", "peanut", "pear", "pepper",
                "pineapple", "pumpkin", "potato"};
        String secretWord = words[random.nextInt(25)];
        System.out.println("Игра 'Угадай слово'. При вводе неверного слова будут отображены совпадающие буквы.");
        System.out.println("Для выхода введи 'exit'.");
        System.out.println();
        System.out.println("Я загадал одно из этих слов: ");
        for (int i = 0; i < words.length; i++) {
            System.out.print(words[i] + " ");
            if ((i + 1) % 9 == 0){
                System.out.println();
            }
        }
        System.out.println();

        do {
            System.out.print("Угадай какое? И введи в консоль: ");
            String userWord = scanner.next();
            userWord = userWord.toLowerCase(Locale.ROOT);
            if (userWord.equals("exit")) {
                break;
            }
            if (secretWord.equals(userWord)){
                System.out.println("Поздравляю Вы выиграли!");
                break;
            } else {
                System.out.println(symbolOverlap(secretWord, userWord));
            }

        } while (true);
        scanner.close();
    }

    public static String symbolOverlap (String word1, String word2) {
        String longWord = word1;
        String shortWord = word2;
        String hint = "";
        if (longWord.length() < shortWord.length()) {
            longWord = word2;
            shortWord = word1;
        }
        for (int i = 0; i < shortWord.length(); i++) {
            if (longWord.charAt(i) == shortWord.charAt(i)) {
                hint += longWord.charAt(i);
            } else {
                hint += "#";
            }
        }
        for (int i = shortWord.length(); i < 15; i++) {
            hint += "#";
        }
        return hint;
    }
}
