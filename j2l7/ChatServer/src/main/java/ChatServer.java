
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatServer {
    private static final int PORT = 8085;
    private static boolean isEnd;
    private static DataInputStream input;
    private static DataOutputStream output;
    private static Socket socket;
    private static Scanner scanner;
    private static final Logger logger = LogManager.getLogger(ChatServer.class);
    private List<ClientHandler> onlineClients;
    private AuthService authService;
    private ExecutorService execService;


    public AuthService getAuthService() {
        return authService;
    }

    public ChatServer() {

        try (ServerSocket serv = new ServerSocket(PORT)) {
            onlineClients = new LinkedList<>();
            authService = new BaseAuthService();
            authService.start();
            execService = Executors.newCachedThreadPool();
            while (true){
                logger.info("Server started");
                socket = serv.accept();
                logger.info("Client connected");
                new ClientHandler(socket, this);
            }
        } catch (IOException e) {
            logger.error("Port is closed");
            logger.throwing(Level.DEBUG, e);
        } finally {
            if (authService != null) {
                authService.stop();
            }
            closeConnection();
            execService.shutdown();
        }
    }

    private static void closeConnection() {
        try {
            socket.close();
            logger.info("Connection is closed.");
            scanner.close();
        } catch (IOException e) {
            logger.error("Connection ending");
            logger.throwing(Level.DEBUG, e);
        }
    }

    public synchronized void broadcastOnlineClients() {
        MessageDTO dto = new MessageDTO();
        dto.setMessageType(MessageType.CLIENTS_LIST_MESSAGE);
        List<String> onlines = new ArrayList<>();
        for (ClientHandler o : onlineClients) {
            onlines.add(o.getNickName());
        }
        dto.setUsersOnline (onlines);
        broadcastMessage(dto);
    }

    public boolean isUserBusy(String nickName) {
        for (ClientHandler o : onlineClients){
            if (o.getNickName().equals(nickName)){
                return true;
            }
        }
        return false;
    }

    public void addOnlineUser(ClientHandler clientHandler) {
        onlineClients.add(clientHandler);
        broadcastOnlineClients();
    }

    public void broadcastMessage(MessageDTO dto) {
        for (ClientHandler c : onlineClients) {
            c.sendMessage(dto);
        }
    }

    public void removeUser(ClientHandler clientHandler) {
        onlineClients.remove(clientHandler);
        broadcastOnlineClients();
    }

    public void privetMessage(MessageDTO dto) {
        for (ClientHandler c : onlineClients) {
            if (c.getNickName().equals(dto.getTo()) || c.getNickName().equals(dto.getFrom())){
                c.sendMessage(dto);
            }
        }
    }

    public void changeNick(MessageDTO dto) {
                authService.changeNickInDB(dto.getFrom(), dto.getBody());
    }

    public ExecutorService getExecService() {
        return execService;
    }
}
