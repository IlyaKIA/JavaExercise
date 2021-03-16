import javax.swing.*;
import java.awt.Window;
import java.awt.event.*;

public class Settings extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;

    private JTextField addressTextField;

    private JTextField portTextField;
    private JTextField loginTextField;
    private JPasswordField passwordField;
    private JCheckBox alwaysOnTopCheckBox;

    public String getNickTextField() {
        return nickTextField.getText();
    }

    private JTextField nickTextField;

    public Settings() {

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setResizable(false);
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);

        alwaysOnTopCheckBox.setSelected(Main.checkAlwaysOnTop);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        // add your code here
        Main.checkAlwaysOnTop = alwaysOnTopCheckBox.isSelected();
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public String getAddressTextField() {
        return addressTextField.getText();
    }

    public int getPortTextField() {
        return Integer.parseInt(portTextField.getText());
    }

    public String getLoginTextField() {
        return loginTextField.getText();
    }

    public String getPasswordField() {
        return passwordField.getText();
    }



}
