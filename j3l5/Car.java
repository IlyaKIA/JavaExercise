import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Car implements Runnable {
    private static int CARS_COUNT;
    private Race race;
    private int speed;
    private String name;
    public String getName() {
        return name;
    }
    public int getSpeed() {
        return speed;
    }
    public Car(Race race, int speed) {
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
    }
    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int)(Math.random() * 800));
            System.out.println(this.name + " готов");
            MainClass.cyclicBarrier.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < race.getStages().size(); i++) {
            race.getStages().get(i).go(this);
        }
        if (!MainClass.isWinner() && !rwl.isWriteLocked()){
            try {
                rwl.writeLock().lock();
                MainClass.setWinner(true);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                rwl.writeLock().unlock();
                System.out.println(this.name + " WIN!!!");
            }
        }
        try {
            MainClass.cyclicBarrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}
