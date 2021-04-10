import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class BaseAuthService implements AuthService {

    private Connection connection;
    private Statement statement;
    private PreparedStatement prSt;
    private static final Logger logger = LogManager.getLogger(BaseAuthService.class);

    public BaseAuthService(){
    }


    @Override
    public void start() {
        connectDB();
        logger.info("Authentication started");
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
            logger.error("Read data base error");
            logger.throwing(Level.DEBUG, throwables);
        }
        return null;
    }

    @Override
    public void stop() {
        disconnectDB();
        logger.info("Authentication stopped");
    }

    private void addNewUser(String login, String password, String nick){
        try {
            statement.executeUpdate("insert into users (Login, password, nick) values ('" + login +"', '" + password +"', '" + nick + "');");
        } catch (SQLException throwables) {
            logger.error("New user is not added to data base");
            logger.throwing(Level.DEBUG, throwables);
        }
    }

    private void connectDB() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:UserList.db");
            statement = connection.createStatement ();
            statement.executeUpdate("create table if not exists users (id integer primary key autoincrement, login text, password text, nick text);");
        } catch (ClassNotFoundException | SQLException e) {
            logger.error("Create data base error");
            logger.throwing(Level.DEBUG, e);
        }
    }

    private void disconnectDB() {
        try {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        } catch (SQLException throwables) {
            logger.error("Disconnect data base error");
            logger.throwing(Level.DEBUG, throwables);
        }
    }
    public void changeNickInDB (String oldNick, String newNick){
        try {
            prSt = connection.prepareStatement("update users set nick = ? where nick = ?;");
            prSt.setString(1, newNick);
            prSt.setString(2, oldNick);
            prSt.executeUpdate();
        } catch (SQLException throwables) {
            logger.error("Change nick Error");
            logger.throwing(Level.DEBUG, throwables);
        }
    }
    public boolean isAccountFree(String newNick, String accountData){
        try {
            ResultSet rs = statement.executeQuery("select * from users;");
            while (rs.next()) {
                String s = rs.getString(accountData);
                if (newNick.equals(s)) return false;
            }
        } catch (SQLException throwables) {
            logger.error("Read data base error");
            logger.throwing(Level.DEBUG, throwables);
        }
        return true;
    }

    public void addUserToDB(String login, String password, String nick){
        try {
            prSt = connection.prepareStatement("insert into users (login, password, nick) values (?, ?, ?);");
            prSt.setString(1, login);
            prSt.setString(2, password);
            prSt.setString(3, nick);
            prSt.executeUpdate();
        } catch (SQLException throwables) {
            logger.error("Registration new user error");
            logger.throwing(Level.DEBUG, throwables);
        }
    }
}

