import java.sql.*;
import java.util.List;

public class BaseAuthService implements AuthService {

    private Connection connection;
    private Statement statement;
    private PreparedStatement prSt;

    public BaseAuthService(){
    }


    @Override
    public void start() {
        connectDB();
        System.out.println("Authentication started");
    }

    @Override
    public String isAuthCorrect(String login, String password) {
        try {
            ResultSet rs = statement.executeQuery("select * from users;");
            while (rs.next()) {
                if (login.equals(rs.getString("login")) && password.equals(rs.getString("password")))
                    return rs.getString("nick");
            }
        } catch (SQLException throwables) {
            System.out.println("Read data base error");
        }
        return null;
    }

    @Override
    public void stop() {
        disconnectDB();
        System.out.println("Authentication stopped");
    }

    private void addNewUser(String login, String password, String nick){
        try {
            statement.executeUpdate("insert into users (Login, password, nick) values ('" + login +"', '" + password +"', '" + nick + "');");
        } catch (SQLException throwables) {
            System.out.println("Error. New user is not added to data base");
        }
    }

    private void connectDB() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:UserList.db");
            statement = connection.createStatement ();
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
        try {
            prSt = connection.prepareStatement("update users set nick = ? where nick = ?;");
            prSt.setString(1, newNick);
            prSt.setString(2, oldNick);
            prSt.executeUpdate();
        } catch (SQLException throwables) {
            System.out.println("Change nick Error");
        }
    }
    public boolean isAccountFree(String newNick, String accountData){
        try {
            ResultSet rs = statement.executeQuery("select * from users;");
            while (rs.next()) {
                if (newNick.equals(rs.getString(accountData))) return false;
            }
        } catch (SQLException throwables) {
            System.out.println("Read data base error");
        }
        return true;
    }
}

