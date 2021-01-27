public class Worker {
    private String nameFIO;
    private String post;
    private String email;
    private String phoneNum;
    private int salary;
    private int age;

    protected Worker(String nameFIO, String post, String email, String phoneNum, int salary, int age){
        this.nameFIO = nameFIO;
        this.post = post;
        this.email = email;
        this.phoneNum = phoneNum;
        this.salary = salary;
        this.age = age;
    }
    protected void printWorker (){
        System.out.println(nameFIO + " - " + post + " Email: " + email + " Зарплата: " + salary + "р. Тел." + phoneNum  + " Возраст: " + age + " лет.");
    }

    public int getAge() {
        return age;
    }
}
