import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler {
    private ChatServer server;
    private Socket socket;
    private DataInputStream input;
    private DataOutputStream output;
    private String nickName;

    public String getNickName() {
        return nickName;
    }

    public ClientHandler(Socket socket, ChatServer server){
        try {
            this.socket = socket;
            this.server = server;
            this.input = new DataInputStream(socket.getInputStream());
            this.output = new DataOutputStream(socket.getOutputStream());
            new Thread(() -> {
                try {
                    authentication();
                    readMessages();
                } catch (IOException e) {
                    System.out.println("Connection failed" + "\n");
                    e.printStackTrace();
                } finally {
                    closeConnection();
                }
            }).start();
        } catch (IOException e) {
            System.out.println("Socket connection error");
        }
    }

    private void readMessages() {
        try {
            while (true) {
                String msg = input.readUTF();
                MessageDTO dto = MessageDTO.convertFromJson(msg);
                dto.setFrom(nickName);
                String to = dto.getTo();

                switch (dto.getMessageType()) {
//                    case SEND_AUTH_MESSAGE -> authentication(dto);
                    case  PUBLIC_MESSAGE -> server.broadcastMessage(dto);
                    case PRIVATE_MESSAGE -> server.privetMessage(dto);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void authentication() throws IOException {
        while (true) {
            String auth = input.readUTF();
            MessageDTO dto = MessageDTO.convertFromJson(auth);
            boolean isAuthTrue = server.getAuthService().isAuthCorrect(dto.getLogin(), dto.getPassword());
            nickName = dto.getBody();
            MessageDTO answer = new MessageDTO();
            if (isAuthTrue == false){
                answer.setMessageType(MessageType.ERROR_MESSAGE);
                answer.setBody("Wrong login or pass!");
                System.out.println("Wrong auth");
            } else if (server.isUserBusy(nickName)) {
                answer.setMessageType(MessageType.ERROR_MESSAGE);
                answer.setBody("U're need to change nick!!!");
                System.out.println("Clone");
            } else {
                answer.setMessageType(MessageType.AUTH_CONFIRM);
                answer.setFrom(nickName);
                answer.setBody("Subscribed");
                server.addOnlineUser(this);
                System.out.println("Subscribed");
                sendMessage(answer);
                break;
            }
            sendMessage(answer);
            }
        }

    public void sendMessage(MessageDTO dto) {
        try {
            output.writeUTF(dto.convertToJson());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeConnection() {
        //TODO
        try {
            socket.close();
            server.removeUser(this);
        } catch (IOException e) {
            System.out.println("Socket closing error");
            e.printStackTrace();
        }
    }
}
