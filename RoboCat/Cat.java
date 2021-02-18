public class Cat implements Actions{
    private int maxRunLength;
    private int maxJumpHeight;
    private boolean tired;

    public Cat(int maxRunLength, int maxJumpHeight) {
        this.maxRunLength = maxRunLength;
        this.maxJumpHeight = maxJumpHeight;
    }

    @Override
    public void running(Track track) {
        System.out.println("Cat running");
        if (maxRunLength >= track.getLength()) {
            System.out.println("Cat run out");
        } else {
            System.out.println("Cat tired");
            tired = true;
        }
    }

    @Override
    public void jumping(Wall wall) {
        System.out.println("Cat jumping");
        if (maxJumpHeight >= wall.getLength()){
            System.out.println("Cat jumped the wall");
        } else {
            System.out.println("Cat fell");
            tired = true;
        }
    }

    @Override
    public boolean isTired() {
        return tired;
    }
}
