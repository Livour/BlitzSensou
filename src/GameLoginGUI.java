import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.*;

public class GameLoginGUI implements ActionListener {
    JFrame frame = new JFrame();
    JButton loginButton = new JButton("Join");
    JButton resetButton = new JButton("Reset");
    JTextField userIDField = new JTextField();
    JLabel userIDLabel = new JLabel("Username:");
    JLabel messageLabel = new JLabel();

    GameLoginGUI() {
        userIDLabel.setBounds(50, 100, 75, 25);
        messageLabel.setBounds(125, 250, 250, 35);
        messageLabel.setFont(new Font(null, Font.ITALIC, 25));
        userIDField.setBounds(125, 100, 200, 25);
        loginButton.setBounds(125, 200, 100, 25);
        loginButton.setFocusable(false);
        loginButton.addActionListener(this);
        resetButton.setBounds(225, 200, 100, 25);
        resetButton.setFocusable(false);
        resetButton.addActionListener(this);
        frame.add(userIDLabel);
        frame.add(messageLabel);
        frame.add(userIDField);
        frame.add(loginButton);
        frame.add(resetButton);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(420, 420);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setResizable(false);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == resetButton) {
            userIDField.setText("");
            return;
        }

        if (e.getSource() == loginButton) {
            String userID = userIDField.getText();

            if (userID.matches("^[a-zA-Z0-9]{2,10}")) {
                messageLabel.setForeground(Color.green);
                messageLabel.setText("Login successful");
                frame.dispose();
                try {
                    GameGUI gui = new GameGUI(userID);
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (FontFormatException ex) {
                    ex.printStackTrace();
                }
            } else {
                messageLabel.setForeground(Color.red);
                messageLabel.setText("Invalid Format");
            }


        }
    }
}

