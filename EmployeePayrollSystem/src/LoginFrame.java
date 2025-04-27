import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class LoginFrame extends JFrame implements ActionListener {
    JTextField userField;
    JPasswordField passField;
    JButton loginButton;

    public LoginFrame() {
        setTitle("Login");
        setSize(700, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());

        // --------------------
        // Left Image Panel
        // --------------------
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setPreferredSize(new Dimension(300, 0));
        imagePanel.setBackground(Color.WHITE);

        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.CENTER);

        // Proper Image path (make sure to include extension like .png or .jpg)
        ImageIcon icon = new ImageIcon("C:\\Users\\siyac\\OneDrive\\Pictures\\Screenshots\\Screenshot 2025-04-27 163908.png");
        Image img = icon.getImage().getScaledInstance(400, 450, Image.SCALE_SMOOTH);
        imageLabel.setIcon(new ImageIcon(img));
        imagePanel.add(imageLabel, BorderLayout.CENTER);

        // --------------------
        // Right Login Form Panel
        // --------------------
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(240, 248, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font labelFont = new Font("Segoe UI", Font.BOLD, 16);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 14);

        // Username Label
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(labelFont);
        formPanel.add(userLabel, gbc);

        // Username Field
        gbc.gridy = 1;
        userField = new JTextField(18);
        userField.setFont(fieldFont);
        formPanel.add(userField, gbc);

        // Password Label
        gbc.gridy = 2;
        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(labelFont);
        formPanel.add(passLabel, gbc);

        // Password Field
        gbc.gridy = 3;
        passField = new JPasswordField(18);
        passField.setFont(fieldFont);
        formPanel.add(passField, gbc);

        // Login Button
        gbc.gridy = 4;
        loginButton = new JButton("Login");
        loginButton.setBackground(new Color(76, 175, 80));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        loginButton.setFocusPainted(false);
        loginButton.setPreferredSize(new Dimension(120, 35));
        loginButton.addActionListener(this);
        formPanel.add(loginButton, gbc);

        mainPanel.add(imagePanel, BorderLayout.WEST);
        mainPanel.add(formPanel, BorderLayout.CENTER);

        add(mainPanel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String username = userField.getText();
        String password = new String(passField.getPassword());

        try {
            Connection conn = DBConnection.getConnection();
            String query = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Login Successful!");
                dispose();
                new Dashboard();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Credentials!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
