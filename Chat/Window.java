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
        setLayout(new BorderLayout());
        add(textInput, BorderLayout.PAGE_END);
        add(historyText, BorderLayout.CENTER);

        textInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                historyText.setText(historyText.getText() + textInput.getText() + "\n");
                textInput.setText("");
            }
        });
        setVisible(true);
    }
}
