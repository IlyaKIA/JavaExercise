import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import static java.awt.Color.RED;

public class Registration extends JFrame {

    public Registration() {
        setTitle("Profile");
        setBounds(300, 300, 250, 140);
        setResizable(false);
        JTextField jLogin = new JTextField();
        JPasswordField jPassword = new JPasswordField();
        JPasswordField jPassword2 = new JPasswordField();
        JTextField jNick = new JTextField();
        JButton butOK = new JButton("OK");
        JButton butCancel = new JButton("Cancel");
        JLabel tLogin = new JLabel("Login");
        JLabel tPassword = new JLabel("Password");
        JLabel tPassword2 = new JLabel("Password check");
        JLabel tNick = new JLabel("Nick");

        setLayout(new GridLayout(5,2));
        add(tLogin);
        add(jLogin);
        add(tPassword);
        add(jPassword);
        add(tPassword2);
        add(jPassword2);
        add(tNick);
        add(jNick);
        add(butOK);
        add(butCancel);

        if (Main.getWindow().getConServ() == null) {
            Main.getWindow().connection();
        }

        butOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!jPassword.getText().equals(jPassword2.getText())){
                    jPassword.setBackground(RED);
                    jPassword2.setBackground(RED);
                } else {
                    try {
                        Main.getWindow().getConServ().sendRegistration(jLogin.getText(), jPassword.getText(), jNick.getText());
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    dispose();
                }
            }
        });

        butCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

}
