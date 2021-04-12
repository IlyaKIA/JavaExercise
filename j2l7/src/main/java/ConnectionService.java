import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ConnectionService {
    private String address;
    private int port;
    private String nick;
    private String login;
    private String password;
    private DataInputStream input;
    private DataOutputStream output;
    private String inMSG;
    private Socket socket;
    private DefaultListModel<String> usersOnline = new DefaultListModel<>();


    public ConnectionService(String address, int port, String nick, String login, String password) {
        this.address = address;
        this.port = port;
        this.nick = nick;
        this.login = login;
        this.password = password;
    }

    //Подключение потока передачи данных

    public void connect () throws IOException {
            socket = new Socket(address, port);
            input = new DataInputStream(socket.getInputStream());
            output = new DataOutputStream(socket.getOutputStream());
    }


    //Метода приема информации с сервера

    public synchronized String getInMSG () {
        try {
            MessageDTO dto = MessageDTO.convertFromJson(input.readUTF());
            switch (dto.getMessageType()) {
                case PUBLIC_MESSAGE, PRIVATE_MESSAGE -> inMSG = showMessage(dto);
                case CLIENTS_LIST_MESSAGE -> {
                    List<String> inputUserList = new ArrayList<>(dto.getUsersOnline());
                    usersOnline.removeAllElements();
                    inputUserList.add(0, "Send all users");
                    for (String u : inputUserList){
                        usersOnline.addElement(u);
                    }
                    inMSG = "*Refresh user list";
                }
//
                case AUTH_CONFIRM -> {
                    inMSG = "New user added\n";
                }
//
//                case ERROR_MESSAGE -> showError(dto);
            }
            return inMSG;

        }catch (IOException e){
            inMSG = "Error. Connection is failed.";
        }
        return "Server is disconnected";
    }
    private String showMessage(MessageDTO dto) {
        String msg = String.format("[%s] [%s] -> %s\n", dto.getMessageType(), dto.getFrom(), dto.getBody());
        return msg;
    }

    //Передача текста на сервер

    public void setOutMSG(String msg) throws IOException {
        MessageDTO dto = new MessageDTO();
        dto.setMessageType(MessageType.PUBLIC_MESSAGE);
        dto.setBody(msg);
        output.writeUTF(dto.convertToJson());
    }
    public void disconnection() throws IOException {
        socket.close();
    }

    public DefaultListModel<String> getUsersOnline() {
        return usersOnline;
    }

    public void authentication() throws IOException {
        MessageDTO dto = new MessageDTO();
        dto.setLogin(login);
        dto.setPassword(password);
        dto.setBody(nick);
        dto.setMessageType(MessageType.SEND_AUTH_MESSAGE);
        output.writeUTF(dto.convertToJson());
    }

    public void setOutPrivetMSG(String nick, String msg) throws IOException {
        MessageDTO dto = new MessageDTO();
        dto.setMessageType(MessageType.PRIVATE_MESSAGE);
        dto.setTo(nick);
        dto.setBody(msg);
        output.writeUTF(dto.convertToJson());
    }
}
