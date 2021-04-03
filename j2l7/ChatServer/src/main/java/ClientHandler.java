import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

public class ClientHandler {
    private ChatServer server;
    private Socket socket;
    private DataInputStream input;
    private DataOutputStream output;
    private String nickName;
    private boolean isAuth;

    public String getNickName() {
        return nickName;
    }

    public ClientHandler(Socket socket, ChatServer server){
        try {
            this.socket = socket;
            this.server = server;
            this.input = new DataInputStream(socket.getInputStream());
            this.output = new DataOutputStream(socket.getOutputStream());
            server.getExecService().execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Timer timer = new Timer();
                        timer.schedule(new timerCloseConnection(), 120_000);
                        authOrRegistr();
                        readMessages();
                    } catch (IOException e) {
                        System.out.println("Connection failed" + "\n");
                    } finally {
                        closeConnection();
                    }
                }
            });
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
                    case CHANGE_NICK -> nickChanging(dto);
                }
            }
        } catch (IOException e) {
            System.out.println("Client " + nickName + " disconnected" );
        }
    }

    private void addNewUser(MessageDTO dto) throws IOException {
        if (server.getAuthService().isAccountFree(dto.getLogin(), "login") && server.getAuthService().isAccountFree(dto.getBody(), "nick")) {
            server.getAuthService().addUserToDB(dto.getLogin(), dto.getPassword(), dto.getBody());
            authentication(dto);
        } else{
            MessageDTO answer = new MessageDTO();
            answer.setMessageType(MessageType.ERROR_MESSAGE);
            answer.setBody("Nick or login already busy!");
            System.out.println("Nick or login already busy!");
            sendMessage(answer);
        }
    }

    private void nickChanging(MessageDTO dto) {
        if (server.getAuthService().isAccountFree(dto.getBody(), "nick")) {
            server.changeNick(dto);
            nickName = dto.getBody();
            server.broadcastOnlineClients();
            MessageDTO answer = new MessageDTO();
            answer.setMessageType(MessageType.CHANGE_NICK);
            answer.setFrom(nickName);
            answer.setBody(dto.getFrom() + " change nick to " + dto.getBody());
            server.privetMessage(answer);
            answer.setFrom(dto.getFrom());
            answer.setMessageType(MessageType.PUBLIC_MESSAGE);
            server.broadcastMessage(answer);
        } else {
            MessageDTO answer = new MessageDTO();
            answer.setMessageType(MessageType.PRIVATE_MESSAGE);
            answer.setFrom(dto.getFrom());
            answer.setTo(dto.getFrom());
            answer.setBody("Error nick is busy");
            server.privetMessage(answer);
        }
    }

    private void authentication(MessageDTO dto) {
        nickName = server.getAuthService().isAuthCorrect(dto.getLogin(), dto.getPassword());
        MessageDTO answer = new MessageDTO();
        if (nickName == null){
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
            isAuth = true;
        }
        sendMessage(answer);
    }

    public void sendMessage(MessageDTO dto) {
        try {
            output.writeUTF(dto.convertToJson());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeConnection() {
        try {
            socket.close();
            server.removeUser(this);
        } catch (IOException e) {
            System.out.println("Socket closing error");
            e.printStackTrace();
        }
    }

    private class timerCloseConnection extends TimerTask
    {
        @Override
        public void run() {
            if (nickName == null){
                closeConnection();
                System.out.println("The client was disconnected by timeout");
            }
        }
    }

    private void authOrRegistr() throws IOException {
        while (!isAuth) {
            String auth = input.readUTF();
            MessageDTO dto = MessageDTO.convertFromJson(auth);
            switch (dto.getMessageType()) {
                case SEND_AUTH_MESSAGE -> authentication(dto);
                case REGISTRATION -> addNewUser(dto);
            }
        }
    }
}
