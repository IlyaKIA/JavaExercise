public class Track implements Obstruction {
    private int trackLength;

    public Track (int trackLength){
        this.trackLength = trackLength;
    }

    public int getLength() {
        return trackLength;
    }

}
