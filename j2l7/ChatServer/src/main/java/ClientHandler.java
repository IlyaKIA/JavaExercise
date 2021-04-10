import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
    private static final Logger logger = LogManager.getLogger(ClientHandler.class);

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
                        logger.info("Connection failed");
                        logger.throwing(Level.DEBUG, e);
                    } finally {
                        closeConnection();
                    }
                }
            });
        } catch (IOException e) {
            logger.error("Socket connection error");
            logger.throwing(Level.DEBUG, e);
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
            logger.info("Client " + nickName + " disconnected");
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
            logger.info("Nick or login already busy!");
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
            logger.info("Wrong auth");
        } else if (server.isUserBusy(nickName)) {
            answer.setMessageType(MessageType.ERROR_MESSAGE);
            answer.setBody("U're need to change nick!!!");
            logger.info("Clone");
        } else {
            answer.setMessageType(MessageType.AUTH_CONFIRM);
            answer.setFrom(nickName);
            answer.setBody("Subscribed");
            server.addOnlineUser(this);
            logger.info("Subscribed");
            sendMessage(answer);
            isAuth = true;
        }
        sendMessage(answer);
    }

    public void sendMessage(MessageDTO dto) {
        try {
            output.writeUTF(dto.convertToJson());
        } catch (IOException e) {
            logger.error("Massage sending error");
            logger.throwing(Level.DEBUG, e);
        }
    }

    private void closeConnection() {
        try {
            socket.close();
            server.removeUser(this);
        } catch (IOException e) {
            logger.error("Socket closing error");
            logger.throwing(Level.DEBUG, e);
        }
    }

    private class timerCloseConnection extends TimerTask
    {
        @Override
        public void run() {
            if (nickName == null){
                closeConnection();
                logger.info("The client was disconnected by timeout");
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
