package to_delete;

import java.util.concurrent.TimeUnit;

public class ABCClass {
    private static final Object mon = new Object();
    private static char printLetter;

    public static void main(String[] args) {
        printLetter = 'A';
        new Thread(new PrintA()).start();
        new Thread(new PrintB()).start();
        new Thread(new PrintC()).start();
    }

    private static class PrintA implements Runnable {
        @Override
        public void run() {
            try {
                synchronized (mon) {
                    for (int i = 0; i < 5; i++) {
                        while (printLetter != 'A') {
                            mon.wait();
                        }
                        System.out.print(printLetter);
                        Thread.sleep(500);
                        printLetter = 'B';
                        mon.notifyAll();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static class PrintB implements Runnable {
        @Override
        public void run() {
            try {
                synchronized (mon) {
                    for (int i = 0; i < 5; i++) {
                        while (printLetter != 'B') {
                            mon.wait();
                        }
                        System.out.print(printLetter);
                        Thread.sleep(500);
                        printLetter = 'C';
                        mon.notifyAll();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static class PrintC implements Runnable {
        @Override
        public void run() {
            try {
                synchronized (mon) {
                    for (int i = 0; i < 5; i++) {
                        while (printLetter != 'C') {
                            mon.wait();
                        }
                        System.out.print(printLetter);
                        Thread.sleep(500);
                        printLetter = 'A';
                        mon.notifyAll();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
