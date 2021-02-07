import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Window extends JFrame {
    public Window() {
        setTitle("MyChat");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(300,300,300,400);
        JTextField textInput = new JTextField();
        JTextArea historyText = new JTextArea();
        JButton insertTextBtn = new JButton();
        historyText.setFocusable(false);
        insertTextBtn.setText(">");
        Container insertTextCont = new Container();
        insertTextCont.setLayout( new BorderLayout());
        insertTextCont.add(textInput, BorderLayout.CENTER);
        insertTextCont.add(insertTextBtn, BorderLayout.EAST);

        setLayout(new BorderLayout());
        add(insertTextCont, BorderLayout.PAGE_END);
        add(historyText, BorderLayout.CENTER);

        textInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insertTextMetod(historyText, textInput);
            }
        });
        insertTextBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insertTextMetod(historyText, textInput);
            }
        });

        setVisible(true);
    }

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
