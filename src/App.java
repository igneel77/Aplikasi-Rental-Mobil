import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



public class App {
    public static void main(String[] args) throws Exception {
        login();
    }


    public static void login() {
        JFrame loginFrame = new JFrame("Login");
        JLabel usernameLabel, passwordLabel;
        usernameLabel = new JLabel("Username");
        usernameLabel.setBounds(30, 15, 100, 30);

        passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(30, 50, 100, 30);

        JTextField usernameField = new JTextField();
        usernameField.setBounds(110, 15, 200, 30);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(110, 50, 200, 30);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(130, 90, 80, 25);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (username.equals("")) {
                    JOptionPane.showMessageDialog(null, "Please enter username");
                } else if (password.equals("")) {
                    JOptionPane.showMessageDialog(null, "Please enter password");
                } else if (username.equals("admin") && password.equals("admin")) {
                    loginFrame.dispose();
                    new mainMenu();
                } else {
                    Connection connection = Koneksi.connect();
                    if (connection != null) {
                        try {
                            Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                                    ResultSet.CONCUR_READ_ONLY);
                            String query = "SELECT * FROM users WHERE username='" + username + "' AND password='"
                                    + password + "'";
                            ResultSet rs = stmt.executeQuery(query);
                            if (!rs.next()) {
                                JOptionPane.showMessageDialog(null, "Wrong Username/Password!");
                            } else {
                                loginFrame.dispose();
                                new mainMenu();
                            }
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
                        }
                    }
                }
            }
        });

        loginFrame.add(usernameLabel);
        loginFrame.add(usernameField);
        loginFrame.add(passwordLabel);
        loginFrame.add(passwordField);
        loginFrame.add(loginButton);

        loginFrame.setSize(400, 180);
        loginFrame.setLayout(null);
        loginFrame.setVisible(true);
        loginFrame.setLocationRelativeTo(null);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}