package to_delete;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {
    private static final int PORT = 8085;
    private static final String ADDRESS = "localhost";
    private static Socket socket;
    private static DataInputStream input;
    private static DataOutputStream output;
    private static boolean isEnd;
    private static String inMsg;
    private static String msg;
    private static Scanner scanner;

    public static void main(String[] args) throws IOException {
        scanner = new Scanner(System.in);
        socket = new Socket(ADDRESS, PORT);
        System.out.println("Client connected.");
        input = new DataInputStream(socket.getInputStream());
        output = new DataOutputStream(socket.getOutputStream());
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (!isEnd){
                        inMsg = input.readUTF();
                        if (inMsg.toLowerCase().equals("/end")){
                            isEnd = true;
                            closeConnection();
                            break;
                        }
                        System.out.println("Server: " + inMsg);
                    }
                }catch (IOException e){
                    if (!isEnd) System.out.println("Error. Connection is failed.");
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (!isEnd) {
                        msg = scanner.nextLine();
                        if (!msg.trim().isEmpty()) {
                            output.writeUTF(msg);
                            if (msg.toLowerCase().equals("/end")) {
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
