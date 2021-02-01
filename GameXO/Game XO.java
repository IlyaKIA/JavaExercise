import java.util.Random;
import java.util.Scanner;

public class Main {
    public static final int SIZE = 5;
    public static char map [][] = new char[SIZE][SIZE];
    public static final int DOTS_TO_WIN = 4;
    public static final char DOT_EMPTY = '•';
    public static final char DOTS_X = 'X';
    public static final char DOTS_O = 'O';
    public static Scanner sc = new Scanner(System.in);
    public static Random random = new Random();
    public static boolean firstStep = true;
    public static int x = -1;
    public static int y = -1;

    public static void main(String[] args) {
        fillTheMap();
        printTheMap();
        boolean turn = true;
        char dot;
        String winText;
        do {
            if (turn == true) {
                turn = false;
                playerTurn();
                dot = DOTS_X;
                winText =  "Вы выиграли!";
            } else {
                turn = true;
                compTurn();
                dot = DOTS_O;
                winText =  "Выиграл компьютер!";

            }
            printTheMap();
            System.out.println();
        } while (winCheck(dot, winText));
        sc.close();
    }

    private static boolean winCheck(char dot, String winText) {
        int winScore[] = {0, 0};
        int friendlyWin = 0;
        //Проверка горизонталей и вертикалей:
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (map[i][j] == dot) {
                    winScore[0]++;
                } else if (winScore[0] < DOTS_TO_WIN) {
                    winScore[0] = 0;
                }
                if (map[j][i] == dot) {
                    winScore[1]++;
                } else if (winScore[1] < DOTS_TO_WIN) {
                    winScore[1] = 0;
                }
            }

            if (winScore[0] >= DOTS_TO_WIN || winScore[1] >= DOTS_TO_WIN) {
                System.out.print(winText);
                return false;
            } else {
                winScore[0] = 0;
                winScore[1] = 0;
            }
        }
        //Проверка диаганалям:
        for (int k = 3; k < 7; k++) {
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    if (i + j == k && map[i][j] == dot) {
                        winScore[0]++;
                    }
                    if (i == j + k - 4 && map[i][j] == dot) {
                        winScore[1]++;
                    }
                }
            }
            if (winScore[0] >= DOTS_TO_WIN || winScore[1] >= DOTS_TO_WIN) {
                System.out.print(winText);
                return false;
            } else {
                winScore[0] = 0;
                winScore[1] = 0;
            }
        }
        //Проверка ничьей:
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (map[i][j] == DOT_EMPTY){
                    friendlyWin++;
                }
            }
        }
        if(friendlyWin == 0) {
            System.out.print("Ничья!");
            return false;
        }
        return true;
    }
    private static void compTurn() {
        int shiftX = 0;
        int shiftY = 0;
        if (firstStep == true) {
            do {
                shiftX = random.nextInt(3) - 1;
                shiftY = random.nextInt(3) - 1;
            } while (checkCorrect(x + shiftX, y + shiftY));
            map[x + shiftX][y + shiftY] = DOTS_O;
            firstStep = false;
            return;
        }
        if (x > 0 && x < map.length - 1 && y > 0 && y < map.length - 1) {
            if (map[x][y - 1] == DOTS_X && map[x][y + 1] == DOT_EMPTY) {
                map[x][y + 1] = DOTS_O;
            } else if (map[x][y + 1] == DOTS_X && map[x][y - 1] == DOT_EMPTY) {
                map[x][y - 1] = DOTS_O;
            } else if (map[x - 1][y] == DOTS_X && map[x + 1][y] == DOT_EMPTY) {
                map[x + 1][y] = DOTS_O;
            } else if (map[x + 1][y] == DOTS_X && map[x - 1][y] == DOT_EMPTY) {
                map[x - 1][y] = DOTS_O;
            } else if (map[x - 1][y - 1] == DOTS_X && map[x + 1][y + 1] == DOT_EMPTY) {
                map[x + 1][y + 1] = DOTS_O;
            } else if (map[x + 1][y + 1] == DOTS_X && map[x - 1][y - 1] == DOT_EMPTY) {
                map[x - 1][y - 1] = DOTS_O;
            } else if (map[x + 1][y - 1] == DOTS_X && map[x - 1][y + 1] == DOT_EMPTY) {
                map[x - 1][y + 1] = DOTS_O;
            } else if (map[x - 1][y + 1] == DOTS_X && map[x + 1][y - 1] == DOT_EMPTY) {
                map[x + 1][y - 1] = DOTS_O;
            } else {
                do {
                    shiftX = random.nextInt(3) - 1;
                    shiftY = random.nextInt(3) - 1;
                } while (checkCorrect(x + shiftX, y + shiftY));
                map[x + shiftX][y + shiftY] = DOTS_O;
            }
        } else {
            for (int i = 0; i < 16; i++) {
                do {
                    shiftX = random.nextInt(3) - 1;
                    shiftY = random.nextInt(3) - 1;
                } while (checkCorrect(x + shiftX, y + shiftY));
                map[x + shiftX][y + shiftY] = DOTS_O;
                return;
            }
            do {
                shiftX = random.nextInt(SIZE);
                shiftY = random.nextInt(SIZE);
            } while (checkCorrect(x + shiftX, y + shiftY));
            map[shiftX][shiftY] = DOTS_O;
        }
    }

    private static void playerTurn() {
        x = -1;
        y = -1;
        do {
        System.out.print("Введи координаты хода типа X Y:");
        boolean correctCoordinate = false;

            if (sc.hasNextInt()) {
                x = sc.nextInt() - 1;
            }
            if (sc.hasNextInt()) {
                y = sc.nextInt() - 1;
            }
            sc.nextLine();
        } while (checkCorrect(x, y));
        map[x][y] = DOTS_X;
    }

    private static boolean checkCorrect(int x, int y){
        if (x >= 0 && y >= 0 && x < SIZE && y < SIZE && map[x][y] == DOT_EMPTY){
            return false;
        }
        return true;
    }

    private static void printTheMap() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.print(map[i][j] + "  ");
            }
            System.out.println();
        }
    }

    private static void fillTheMap() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                map [i][j] = DOT_EMPTY;
            }
        }
    }
}