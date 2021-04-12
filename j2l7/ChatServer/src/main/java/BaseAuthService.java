import java.util.LinkedList;
import java.util.List;

public class BaseAuthService implements AuthService {
    List<Client> clients;

    public BaseAuthService(){
        clients = new LinkedList<>();
        clients.add(new Client("log1", "pass1"));
        clients.add(new Client("log2", "pass2"));
        clients.add(new Client("log3", "pass3"));
    }


    @Override
    public void start() {
        System.out.println("Authentication started");
    }

    @Override
    public boolean isAuthCorrect(String login, String password) {
        for (Client c : clients){
            if (c.getLogin().equals(login) && c.getPassword().equals(password)) return true;
        }
        return false;
    }

    @Override
    public void stop() {
        System.out.println("Authentication stopped");
    }
}
