import javax.swing.*;
import java.awt.*;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ChangeNick extends JFrame {
    String nick;

    public ChangeNick(String nick) {
        this.nick = nick;
        setTitle("Change Nick");
        setBounds(300, 300, 250, 90);
        setResizable(false);
        JTextField jNick = new JTextField(nick);
        JButton butOK = new JButton("OK");
        JButton butCancel = new JButton("Cancel");
        JLabel tNick = new JLabel("Nick");

        setLayout(new GridLayout(2,2));
        add(tNick);
        add(jNick);
        add(butOK);
        add(butCancel);

        butOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Main.getWindow().getConServ().setChangeNick(nick, jNick.getText());
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                dispose();
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
