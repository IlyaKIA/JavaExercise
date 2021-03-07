package ChatServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ChatServer {
    private static final int PORT = 8085;
    private static boolean isEnd;
    private static DataInputStream input;
    private static DataOutputStream output;
    private static Socket socket;
    private static Scanner scanner;

    public static void main(String[] args) throws IOException {
        scanner = new Scanner(System.in);

        try {
            ServerSocket serv = new ServerSocket(PORT);
            System.out.println("Server started.");
            socket = serv.accept();
            System.out.println("Client connected.");
            input = new DataInputStream(socket.getInputStream());
            output = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            System.out.println("Error. Port is closed.");
        }

        //Read massage from net
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (!isEnd){
                        String msg = input.readUTF();
                        if (msg.toLowerCase().equals("/end")){
                            isEnd = true;
                            //scanner.close();
                            closeConnection();
                            break;
                        }
                        System.out.println("User: " + msg);
                    }
                } catch (IOException e) {
                    if (!isEnd) {
                        System.out.println("Error. Server stopped.");
                    } else {
                        System.out.println("Client is disconnected.");
                    }
                }

            }
        }).start();

        //Sending massage
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (!isEnd) {
                        String msg = scanner.nextLine();
                        output.writeUTF(msg);
                        if (!msg.trim().isEmpty()) {
                            if (msg.toLowerCase().equals("/end")){
                                isEnd = true;
                                closeConnection();
                                break;
                            }
                        }
                    }
                }catch (Exception e){
                    if (!isEnd) System.out.println("Error. Massage sending.");
                }
            }
        }).start();
    }

    private static void closeConnection() {
        try {
            input.close();
            output.close();
            socket.close();
            System.out.println("Connection is closed.");
            scanner.close();
        } catch (IOException e) {
            System.out.println("Error of connection ending.");
        }
    }
}
