import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Window extends JFrame {

    public Window() {
        setTitle("MyChat");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(300,300,300,400);

        JTextArea historyText = new JTextArea();
        historyText.setFocusable(false);

        //Инициализация поля ввода текста и кнопки
        JTextField textInput = new JTextField();
        JList<String> userList = new JList<>();
        JScrollPane scrollUsers = new JScrollPane(userList);
        String[] users = {"user1", "user2", "user3", "user4", "user5", "user6",
                "user_with_an_exceptionally_long_nickname"};
        userList.setListData(users);
        scrollUsers.setPreferredSize(new Dimension(100, 0));

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
        JMenuItem menuAbout = new JMenuItem("About");
        JMenuItem menuSettings = new JMenuItem("Settings");
        JMenuItem menuExit = new JMenuItem("Exit");
        menuFile.add(menuSettings);
        menuFile.add(menuAbout);
        menuFile.add(menuExit);
        menuBar.add(menuFile);
        setJMenuBar(menuBar);

        //Компановка элементов окна
        setLayout(new BorderLayout());
        add(insertTextCont, BorderLayout.PAGE_END);
        add(historyText, BorderLayout.CENTER);
        add(scrollUsers, BorderLayout.EAST);
        setLocationRelativeTo(null);

        //Отслеживание Enter при вводе текста
        textInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insertTextMetod(historyText, textInput);
            }
        });

        //Отслеживание нажатия кнопки "отправки"
        insertTextBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insertTextMetod(historyText, textInput);
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
                Settings settings = new Settings();
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

    //Метод ввода текста
    private void insertTextMetod(JTextArea historyText, JTextField textInput) {
        String errText = "Введите текст";
        if (textInput.getText().equals("")) {
            textInput.setText(errText);
            textInput.select(0,errText.length());
        } else {
            historyText.setText(historyText.getText() + textInput.getText() + "\n");
            textInput.setText("");
        }
    }

}
