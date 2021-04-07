import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class MainClass {
    private static boolean winner;
    public static final int CARS_COUNT = 4;
    public static CyclicBarrier cyclicBarrier = new CyclicBarrier(CARS_COUNT + 1);

    public static void main(String[] args) throws BrokenBarrierException, InterruptedException {
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        Race race = new Race(new Road(60), new Tunnel(), new Road(40));
        Car[] cars = new Car[CARS_COUNT];
        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, 20 + (int) (Math.random() * 10));
        }
        for (int i = 0; i < cars.length; i++) {
            new Thread(cars[i]).start();
        }
        while (cyclicBarrier.getNumberWaiting() != CARS_COUNT) Thread.sleep(100);
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
        cyclicBarrier.await();
        while (cyclicBarrier.getNumberWaiting() != CARS_COUNT) Thread.sleep(100);
        cyclicBarrier.await();
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
    }

    public static boolean isWinner() {
        return winner;
    }

    public static void setWinner(boolean isWinner) {
        MainClass.winner = isWinner;
    }
}
