public class Human implements Actions {
    private int maxRunLength;
    private int maxJumpHeight;
    private boolean tired;

    public Human(int maxRunLength, int maxJumpHeight) {
        this.maxRunLength = maxRunLength;
        this.maxJumpHeight = maxJumpHeight;
    }

    @Override
    public void running(Track track) {
        System.out.println("Human running");
        if (maxRunLength >= track.getLength()) {
            System.out.println("Human run out");
        } else {
            System.out.println("Human tired");
            tired = true;
        }
    }

    @Override
    public void jumping(Wall wall) {
        System.out.println("Human jumping");
        if (maxJumpHeight >= wall.getLength()){
            System.out.println("Human jumped the wall");
        } else {
            System.out.println("Human fell");
            tired = true;
        }
    }

    @Override
    public boolean isTired() {
        return tired;
    }
}
