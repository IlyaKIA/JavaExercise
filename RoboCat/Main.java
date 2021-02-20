public class Main {
    public static void main(String[] args) {
        Actions [] athletics = {
                new Cat(100, 5),
                new Human(1000, 10),
                new Robot(50, 100)
        };
        Obstruction [] obstructions = {
                new Track(300),
                new Wall(10)
        };
        for (int i = 0; i < athletics.length; i++) {
            athletics[i].running((Track) obstructions[0]);
            if (!athletics[i].isTired()) {
                athletics[i].jumping((Wall) obstructions[1]);
            }
        }
    }
}
