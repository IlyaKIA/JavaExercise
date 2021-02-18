public interface Actions {
    void running(Track track);
    void jumping(Wall wall);
    boolean isTired();
}
