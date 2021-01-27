public class Main {
    public static void main(String[] args) {
        Worker [] workerArr = new Worker [5];
        workerArr [0] = new Worker("Васлсий Васильев", "Инженер", "vasvas@vasya.com", "8 (955)555-55-55", 50000, 25);
        workerArr [1] = new Worker("Иван Иванович", "Программист", "iviv@vasya.com", "8 (955)555-55-56", 100000, 30);
        workerArr [2] = new Worker("Семен Семеныч", "Инженер-программист", "ss@vasya.com", "8 (955)555-55-54", 150000, 35);
        workerArr [3] = new Worker("Андрей Андреевич", "Дворник", "andry@vasya.com", "8 (955)555-55-53", 30000, 70);
        workerArr [4] = new Worker("Дмитрий Александрович", "Директор", "Dmitich@vasya.com", "8 (955)555-55-51", 200000, 50);

        System.out.println("Сотрудники старше 40 лет:");
        for (int i = 0; i < workerArr.length; i++) {
            if (workerArr[i].getAge() > 40){
                workerArr[i].printWorker();
            }
        }
    }
}
