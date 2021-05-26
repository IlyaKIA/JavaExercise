public class Main {
    public static void main(String[] args) {
        PhoneBook book = new PhoneBook();
        book.add("Кусков", "8-925-255-55-10");
        System.out.println(book.get("Носков"));
    }
}
