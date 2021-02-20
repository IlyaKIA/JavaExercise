public class Robot implements Actions{
    private int maxRunLength;
    private int maxJumpHeight;
    private boolean tired;

    public Robot(int maxRunLength, int maxJumpHeight) {
        this.maxRunLength = maxRunLength;
        this.maxJumpHeight = maxJumpHeight;
    }

    @Override
    public void running(Track track) {
        System.out.println("Robot running");
        if (maxRunLength >= track.getLength()) {
            System.out.println("Robot run out");
        } else {
            System.out.println("Robot tired");
            tired = true;
        }
    }

    @Override
    public void jumping(Wall wall) {
        System.out.println("Robot jumping");
        if (maxJumpHeight >= wall.getLength()){
            System.out.println("Robot jumped the wall");
        } else {
            System.out.println("Robot fell");
            tired = true;
        }
    }

    @Override
    public boolean isTired() {
        return tired;
    }
}
