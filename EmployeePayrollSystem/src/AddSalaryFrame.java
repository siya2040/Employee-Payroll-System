import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

// Custom JPanel to draw the background image
class BackgroundPanel extends JPanel {
    private Image backgroundImage;

    public BackgroundPanel(String imagePath) {
        backgroundImage = new ImageIcon(imagePath).getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            // Fit the image to the size of the panel
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}

public class AddSalaryFrame extends JFrame implements ActionListener {
    JTextField empIdField, basicField, bonusField, allowanceField;
    JButton saveBtn, clearBtn;

    public AddSalaryFrame() {
        setTitle("Add Salary Details");
        setSize(700, 400); // Adjusted to fit the content
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set the background color to light blue (#ADD8E6)
        getContentPane().setBackground(new Color(173, 216, 230));  // Light blue color

        // Create the image panel
        BackgroundPanel imagePanel = new BackgroundPanel("C:\\Users\\siyac\\OneDrive\\Pictures\\Screenshots\\Screenshot 2025-04-27 170451.png");
        imagePanel.setPreferredSize(new Dimension(300, 400)); // Adjust the width of the image panel

        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false); // Important! So the background image shows through
        formPanel.setBackground(new Color(173, 216, 230)); // Set background color of the form panel to light blue
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font labelFont = new Font("Arial", Font.BOLD, 14);
        Font fieldFont = new Font("Arial", Font.PLAIN, 14);

        // Row 1: Employee ID
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel idLabel = new JLabel("Employee ID:");
        idLabel.setFont(labelFont);
        formPanel.add(idLabel, gbc);

        gbc.gridx = 1;
        empIdField = new JTextField(20);
        empIdField.setFont(fieldFont);
        formPanel.add(empIdField, gbc);

        // Row 2: Basic Salary
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel basicLabel = new JLabel("Basic Salary:");
        basicLabel.setFont(labelFont);
        formPanel.add(basicLabel, gbc);

        gbc.gridx = 1;
        basicField = new JTextField(20);
        basicField.setFont(fieldFont);
        formPanel.add(basicField, gbc);

        // Row 3: Bonus
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel bonusLabel = new JLabel("Bonus:");
        bonusLabel.setFont(labelFont);
        formPanel.add(bonusLabel, gbc);

        gbc.gridx = 1;
        bonusField = new JTextField(20);
        bonusField.setFont(fieldFont);
        formPanel.add(bonusField, gbc);

        // Row 4: Allowances
        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel allowanceLabel = new JLabel("Allowances:");
        allowanceLabel.setFont(labelFont);
        formPanel.add(allowanceLabel, gbc);

        gbc.gridx = 1;
        allowanceField = new JTextField(20);
        allowanceField.setFont(fieldFont);
        formPanel.add(allowanceField, gbc);

        // Button Panel at the bottom center
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setOpaque(false); // Also make this transparent
        saveBtn = new JButton("Save");
        saveBtn.setBackground(new Color(76, 175, 80));
        saveBtn.setForeground(Color.WHITE);
        saveBtn.setFocusPainted(false);
        saveBtn.setFont(new Font("Arial", Font.BOLD, 14));
        saveBtn.setPreferredSize(new Dimension(100, 40));
        saveBtn.addActionListener(this);
        buttonPanel.add(saveBtn);

        clearBtn = new JButton("Clear");
        clearBtn.setBackground(new Color(244, 67, 54));
        clearBtn.setForeground(Color.WHITE);
        clearBtn.setFocusPainted(false);
        clearBtn.setFont(new Font("Arial", Font.BOLD, 14));
        clearBtn.setPreferredSize(new Dimension(100, 40));
        clearBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(
                        AddSalaryFrame.this,
                        "Are you sure you want to clear all fields?",
                        "Confirm Clear",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );
                if (result == JOptionPane.YES_OPTION) {
                    clearFields();
                }
            }
        });
        buttonPanel.add(clearBtn);

        // Create a split pane to separate the image and form
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, imagePanel, formPanel);
        splitPane.setDividerLocation(300); // You can adjust this to resize the image/form proportion
        splitPane.setResizeWeight(0.5); // This keeps both sides resizable

        // Add the split pane to the frame
        add(splitPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH); // Place button panel at the bottom center

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            int empId = Integer.parseInt(empIdField.getText());
            double basic = Double.parseDouble(basicField.getText());
            double bonus = Double.parseDouble(bonusField.getText());
            double allowance = Double.parseDouble(allowanceField.getText());

            Connection conn = DBConnection.getConnection();
            String query = "INSERT INTO salary (emp_id, basic_salary, bonus, allowances) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, empId);
            stmt.setDouble(2, basic);
            stmt.setDouble(3, bonus);
            stmt.setDouble(4, allowance);

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Salary Details Added Successfully!");
            clearFields();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        empIdField.setText("");
        basicField.setText("");
        bonusField.setText("");
        allowanceField.setText("");
    }

    public static void main(String[] args) {
        new AddSalaryFrame();
    }
}
