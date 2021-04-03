import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Window extends JFrame {
    private ConnectionService conServ;
    private JTextArea historyText;
    private final Settings settings;
    private ChangeNick changeNick;
    public static JList<String> userList;
    private Registration registration;
    private final JMenuItem menuChangeNick;
    private final JMenuItem menuRegistration;
    private History historyToFile;

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

        JButton insertTextBtn = new JButton(">");
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
        menuRegistration = new JMenuItem("Registration");
        menuChangeNick = new JMenuItem("Change nick");
        menuChangeNick.setEnabled(false);
        JMenuItem menuAbout = new JMenuItem("About");
        JMenuItem menuSettings = new JMenuItem("Settings");
        JMenuItem menuExit = new JMenuItem("Exit");
        menuFile.add(menuSettings);
        menuConnection.add(menuRegistration);
        menuFile.add(menuChangeNick);
        menuFile.add(menuAbout);
        menuFile.add(menuExit);
        menuBar.add(menuFile);
        menuConnection.add(menuConnect);
        menuConnection.add(menuGetIn);
        menuConnection.add(menuDisconnect);
        menuConnection.add(menuRegistration);
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
        historyToFile = new History();


//        profile = new Profile(settings.getLoginTextField(), settings.getPasswordField(), settings.getNickTextField());
//        profile.setVisible(true);

        //Отслеживание меню регистарции
        menuRegistration.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Registration registrWin = new Registration();
                registrWin.setVisible(true);
            }
        });

        //Соединение
        menuConnect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                connection();
            }
        });

        //Аутентификация
        menuGetIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (settings.getNickTextField().equals("") || settings.getLoginTextField().equals("") ||
                        settings.getPasswordField().equals("")) {
                    appendHistoryText("Login, Password and Nick must to be filled");
                    return;
                }
                try {
                    conServ.authentication();
                    insertMSG();
                    appendHistoryText("Authentication success\n");
                } catch (IOException ioException) {
                    appendHistoryText("Authentication failed\n");
                }
            }
        });

        menuDisconnect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    conServ.disconnection();
                    historyToFile.writeToFile(conServ.getNick(), historyText.getText());
                    historyText.setText("Disconnected\n");
                    userList.setListData(new String[] {""});
                    menuRegistration.setEnabled(true);
                } catch (IOException ioException) {
                    appendHistoryText("Disconnection failed\n");
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
                settings.getAlwaysOnTopCheckBox().setSelected(Main.getWindow().isAlwaysOnTop());
                settings.setVisible(true);
            }
        });
        //Отслеживанеи кнопки выхода
        menuExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        //Вклчение меню смены ника
        menuChangeNick.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeNick = new ChangeNick(conServ.getNick());
                changeNick.setVisible(true);
            }
        });

        setVisible(true);
    }

    public void connection() {
        conServ = new ConnectionService(settings.getAddressTextField(), settings.getPortTextField(), settings.getLoginTextField(), settings.getPasswordField());
        try {
            conServ.connect();
            appendHistoryText("Connection success\n");
        } catch (IOException ioException) {
            appendHistoryText("Connection failed\n");
        }
    }


    void insertMSG() {
        //Запуск потока чтения
        new Thread(new Runnable() {
            @Override
            public void run() {
                //TODO Вывод сообщений с ошибками.
                while (true) {
                    String msg = conServ.getInMSG();
                    if (msg != null) {
                        userList.setModel(conServ.getUsersOnline());
                        userList.setSelectedIndex(0);
                        break;
                    }
                }
                while (true) {
                    String msg = conServ.getInMSG();
                    if (msg != null) {
                        if (msg.equals("*Refresh user list")) {
                            userList.setSelectedIndex(0);
                        } else {
                            appendHistoryText(msg);
                        }
                    } else {
                        appendHistoryText("Server is disconnected");
                        break;
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
                appendHistoryText("Sending error\n");
            }
            textInput.setText("");
        } else {
            try {
                conServ.setOutMSG(textInput.getText());
            } catch (IOException e) {
                appendHistoryText("Sending error\n");
            }
            textInput.setText("");
        }
        historyText.setCaretPosition(historyText.getDocument().getLength());
    }

    public JMenuItem getMenuChangeNick() {
        return menuChangeNick;
    }

    public ConnectionService getConServ() {
        return conServ;
    }

    public void fillHistoryText (){
        List<String> historyFromFile = new ArrayList<>(historyToFile.readFromFile(conServ.getNick()));
        for (String s : historyFromFile){
            appendHistoryText(s + "\n");
        }
    }

    public void saveHistory(String msg) {
        historyToFile.addToLastSessionHistory(msg);
    }

    private void appendHistoryText (String msg) {
        historyText.append(msg);
        historyText.setCaretPosition(historyText.getDocument().getLength());
    }

    public JMenuItem getMenuRegistration() {
        return menuRegistration;
    }
}
