import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Window extends JFrame {
    private ConnectionService conServ;
    private JTextArea historyText;
    private Settings settings;
    public static JList<String> userList;

    public Window() {
        setTitle("MyChat");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(300,300,450,600);

        historyText = new JTextArea();
        historyText.setEditable(false);
        JScrollPane scrollText = new JScrollPane(historyText);

        //Инициализация поля ввода текста и кнопки
        JTextField textInput = new JTextField();
        userList = new JList<>();
        JScrollPane scrollUsers = new JScrollPane(userList);
        scrollUsers.setPreferredSize(new Dimension(120, 0));

        JButton insertTextBtn = new JButton();
        insertTextBtn.setText(">");
        //Установка расположения поля ввода текста и кнопки в контейнере
        Container insertTextCont = new Container();
        insertTextCont.setLayout( new BorderLayout());
        insertTextCont.add(textInput, BorderLayout.CENTER);
        insertTextCont.add(insertTextBtn, BorderLayout.EAST);


        //Создание структуры меню
        JMenuBar menuBar = new JMenuBar();
        JMenu menuFile = new JMenu("File");
        JMenu menuConnection = new JMenu("Connection");
        JMenuItem menuConnect = new JMenuItem("Connect");
        JMenuItem menuGetIn = new JMenuItem("Get in");
        JMenuItem menuDisconnect = new JMenuItem("Disconnect");
        JMenuItem menuAbout = new JMenuItem("About");
        JMenuItem menuSettings = new JMenuItem("Settings");
        JMenuItem menuExit = new JMenuItem("Exit");
        menuFile.add(menuSettings);
        menuFile.add(menuAbout);
        menuFile.add(menuExit);
        menuBar.add(menuFile);
        menuConnection.add(menuConnect);
        menuConnection.add(menuGetIn);
        menuConnection.add(menuDisconnect);
        menuBar.add(menuConnection);
        setJMenuBar(menuBar);

        //Компановка элементов окна
        setLayout(new BorderLayout());
        add(insertTextCont, BorderLayout.PAGE_END);
        add(scrollText, BorderLayout.CENTER);
        add(scrollUsers, BorderLayout.EAST);
        setLocationRelativeTo(null);

        //Загрузка Настроек
        settings = new Settings();


        //Соединение
        menuConnect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                conServ = new ConnectionService(settings.getAddressTextField(), settings.getPortTextField(),settings.getNickTextField(), settings.getLoginTextField(), settings.getPasswordField());
                try {
                    conServ.connect();
                    historyText.append("Connection success\n");
                } catch (IOException ioException) {
                    historyText.append("Connection failed\n");
                }
            }
        });

        //Аутентификация
        menuGetIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (settings.getNickTextField().equals("") || settings.getLoginTextField().equals("") ||
                        settings.getPasswordField().equals("")) {
                    historyText.append("Login, Password and Nick must to be filled");
                    return;
                }
                try {
                    conServ.authentication();
                    insertMSG();
                    historyText.append("Authentication success\n");
                } catch (IOException ioException) {
                    historyText.append("Authentication failed\n");
                }
            }
        });



        menuDisconnect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    conServ.disconnection();
                    historyText.append("Disconnected\n");
                } catch (IOException ioException) {
                    historyText.append("Disconnection failed\n");
                }
            }
        });

        //Отслеживание Enter при вводе текста
        textInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportTextMetod(historyText, textInput);
            }
        });

        //Отслеживание нажатия кнопки "отправки"
        insertTextBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportTextMetod(historyText, textInput);
            }
        });
        //Включение меню About
        menuAbout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                About about = new About();
                        about.pack();
                        about.setVisible(true);
            }
        });
        //Включение меню настроек
        menuSettings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                settings.pack();
                settings.setVisible(true);
                setAlwaysOnTop(Main.checkAlwaysOnTop);
            }
        });
        //Отслеживанеи кнопки выхода
        menuExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        setVisible(true);
    }
    void insertMSG() {
        //Запуск потока чтения
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    String msg = conServ.getInMSG();
                    if (msg != null) {
                        setTitle(settings.getNickTextField());
                        userList.setModel(conServ.getUsersOnline());
                        userList.setSelectedIndex(0);
                        break;
                    }
                }
                while (true) {
                    String msg = conServ.getInMSG();
                    if (msg.equals("*Refresh user list")){
                        userList.setSelectedIndex(0);
                    } else {
                        historyText.append(msg);
                    }
                }
            }
        }).start();
    }

    //Метод отправки текста

    private void exportTextMetod(JTextArea historyText, JTextField textInput) {
        String errText = "Введите текст";

        if (textInput.getText().equals("")) {
            textInput.setText(errText);
            textInput.select(0,errText.length());
        } else if (textInput.getText().equals(errText)){
            return;
        } else if (userList.getSelectedIndex() != 0) {
            try {
                conServ.setOutPrivetMSG(userList.getSelectedValue(), textInput.getText());
            } catch (IOException e) {
                historyText.append("Sending error\n");
            }
            textInput.setText("");
        } else {
            try {
                conServ.setOutMSG(textInput.getText());
            } catch (IOException e) {
                historyText.append("Sending error\n");
            }
            textInput.setText("");
        }
    }

}
