public interface AuthService {
    void start();
    String isAuthCorrect(String login, String password);
    void stop();

    void changeNickInDB(String from, String body);
}
