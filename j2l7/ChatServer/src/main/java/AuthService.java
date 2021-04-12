public interface AuthService {
    void start();
    String isAuthCorrect(String login, String password);
    void stop();

    void changeNickInDB(String from, String body);

    boolean isAccountFree(String newNick, String accountData);

    void addUserToDB(String login, String password, String nick);
}
