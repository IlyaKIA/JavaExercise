//package ChatServer;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class ChatServer {
    private static final int PORT = 8085;
    private static boolean isEnd;
    private static DataInputStream input;
    private static DataOutputStream output;
    private static Socket socket;
    private static Scanner scanner;
    private List<ClientHandler> onlineClients;
    private AuthService authService;

    public AuthService getAuthService() {
        return authService;
    }

    public ChatServer() {

        try (ServerSocket serv = new ServerSocket(PORT)) {
            onlineClients = new LinkedList<>();
            authService = new BaseAuthService();
            authService.start();
            while (true){
                System.out.println("Server started");
                socket = serv.accept();
                System.out.println("Client connected.");
                new ClientHandler(socket, this);
            }
        } catch (IOException e) {
            System.out.println("Error. Port is closed.");
        } finally {
            if (authService != null) {
                authService.stop();
            }
            closeConnection();
        }
    }

    private static void closeConnection() {
        try {
            socket.close();
            System.out.println("Connection is closed.");
            scanner.close();
        } catch (IOException e) {
            System.out.println("Error of connection ending.");
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
}
