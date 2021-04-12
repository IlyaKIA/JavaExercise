import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class BaseAuthService implements AuthService {

    List<Client> clients;
    private Connection connection;
    private Statement statement;

    public BaseAuthService(){
        clients = new LinkedList<>();
    }


    @Override
    public void start() {
        System.out.println("Authentication started");
    }

    @Override
    public String isAuthCorrect(String login, String password) {
        connectDB();
        readDB();
        disconnectDB();
        for (Client c : clients){
            if (c.getLogin().equals(login) && c.getPassword().equals(password)) return c.getNick();
        }
        return null;

    }

    private void readDB() {
        try {
            ResultSet rs = statement.executeQuery("select * from users;");
            while (rs.next()){
                clients.add(new Client(rs.getString("login"), rs.getString("password"), rs.getString("nick")));
            }
        } catch (SQLException throwables) {
            System.out.println("Read data base error");
        }
    }

    @Override
    public void stop() {

        System.out.println("Authentication stopped");
    }

    private void addNewUser(String login, String password, String nick){
        connectDB();
        try {
            statement.executeUpdate("insert into users (Login, password, nick) values ('" + login +"', '" + password +"', '" + nick + "');");
        } catch (SQLException throwables) {
            System.out.println("Error. New user is not added to data base");
        }
        disconnectDB();
    }

    private void connectDB() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:UserList.db");
            statement = connection.createStatement();
            statement.executeUpdate("create table if not exists users (id integer primary key autoincrement, login text, password text, nick text);");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Create data base error");
        }
    }

    private void disconnectDB() {
        try {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        } catch (SQLException throwables) {
            System.out.println("Disconnect data base error");
        }
    }
    public void changeNickInDB (String oldNick, String newNick){
        for (Client c : clients){
            if (c.getNick().equals(oldNick)) c.setNick(newNick);
        }
        connectDB();
        try {
            statement.executeUpdate("update users set nick = '" + newNick + "' where nick = '" + oldNick + "';");
        } catch (SQLException throwables) {
            System.out.println("Change nick Error");
        }
        disconnectDB();
    }
}

