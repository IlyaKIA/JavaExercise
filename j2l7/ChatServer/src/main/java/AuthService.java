public interface AuthService {
    void start();
    boolean isAuthCorrect(String login, String password);
    void stop();
}
