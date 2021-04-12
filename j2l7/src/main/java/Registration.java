import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Registration extends JFrame {

    public Registration() {
        setTitle("Profile");
        setBounds(300, 300, 250, 140);
        setResizable(false);
        JTextField jLogin = new JTextField();
        JPasswordField jPassword = new JPasswordField();
        JTextField jNick = new JTextField();
        JButton butOK = new JButton("OK");
        JButton butCancel = new JButton("Cancel");
        JLabel tLogin = new JLabel("Login");
        JLabel tPassword = new JLabel("Password");
        JLabel tNick = new JLabel("Nick");

        setLayout(new GridLayout(4,2));
        add(tLogin);
        add(jLogin);
        add(tPassword);
        add(jPassword);
        add(tNick);
        add(jNick);
        add(butOK);
        add(butCancel);

        butOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                setVisible(false);
            }
        });

        butCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
    }

}
