import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AddEmployeeFrame extends JFrame implements ActionListener {
    JTextField empIdField, nameField, deptField, desigField;
    JButton saveBtn, clearBtn;

    public AddEmployeeFrame() {
        setTitle("Add Employee");
        setSize(1000, 600); // Adjust the size based on your preference
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // -------------------- Left Image Panel --------------------
        JPanel leftImagePanel = new JPanel(new BorderLayout());
        leftImagePanel.setPreferredSize(new Dimension(400, 0)); // Image panel for the left side
        leftImagePanel.setBackground(Color.WHITE);

        JLabel leftImageLabel = new JLabel();
        leftImageLabel.setHorizontalAlignment(JLabel.CENTER);

        ImageIcon leftIcon = new ImageIcon("C:\\Users\\siyac\\OneDrive\\Pictures\\Screenshots\\Screenshot 2025-04-27 175251.png"); // Left image path
        Image leftImg = leftIcon.getImage().getScaledInstance(350, 400, Image.SCALE_SMOOTH); // resize for left image
        leftImageLabel.setIcon(new ImageIcon(leftImg));
        leftImagePanel.add(leftImageLabel, BorderLayout.CENTER);

        // -------------------- Form Panel --------------------
        JPanel formOuterPanel = new JPanel(new BorderLayout());
        formOuterPanel.setBorder(BorderFactory.createEmptyBorder(40, 30, 40, 30)); // extra breathing space
        formOuterPanel.setBackground(new Color(240, 248, 255));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 15, 20, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font labelFont = new Font("Segoe UI", Font.BOLD, 16);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 16);

        // Row 1
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel idLabel = new JLabel("Employee ID:");
        idLabel.setFont(labelFont);
        formPanel.add(idLabel, gbc);

        gbc.gridx = 1;
        empIdField = new JTextField(25);
        empIdField.setFont(fieldFont);
        formPanel.add(empIdField, gbc);

        // Row 2
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(labelFont);
        formPanel.add(nameLabel, gbc);

        gbc.gridx = 1;
        nameField = new JTextField(25);
        nameField.setFont(fieldFont);
        formPanel.add(nameField, gbc);

        // Row 3
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel deptLabel = new JLabel("Department:");
        deptLabel.setFont(labelFont);
        formPanel.add(deptLabel, gbc);

        gbc.gridx = 1;
        deptField = new JTextField(25);
        deptField.setFont(fieldFont);
        formPanel.add(deptField, gbc);

        // Row 4
        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel desigLabel = new JLabel("Designation:");
        desigLabel.setFont(labelFont);
        formPanel.add(desigLabel, gbc);

        gbc.gridx = 1;
        desigField = new JTextField(25);
        desigField.setFont(fieldFont);
        formPanel.add(desigField, gbc);

        // -------------------- Buttons Panel --------------------
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 20));
        buttonPanel.setOpaque(false);

        saveBtn = new JButton("Save");
        saveBtn.setBackground(new Color(76, 175, 80));
        saveBtn.setForeground(Color.WHITE);
        saveBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        saveBtn.setPreferredSize(new Dimension(120, 45));
        saveBtn.addActionListener(this);

        clearBtn = new JButton("Clear");
        clearBtn.setBackground(new Color(244, 67, 54));
        clearBtn.setForeground(Color.WHITE);
        clearBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        clearBtn.setPreferredSize(new Dimension(120, 45));
        clearBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(
                        AddEmployeeFrame.this,
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

        buttonPanel.add(saveBtn);
        buttonPanel.add(clearBtn);

        formOuterPanel.add(formPanel, BorderLayout.CENTER);
        formOuterPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add the form and image panels
        mainPanel.add(leftImagePanel, BorderLayout.WEST); // Image will be on the left side
        mainPanel.add(formOuterPanel, BorderLayout.CENTER); // Form is in the center

        add(mainPanel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String empId = empIdField.getText();
        String name = nameField.getText();
        String dept = deptField.getText();
        String desig = desigField.getText();

        try {
            Connection conn = DBConnection.getConnection();
            String query = "INSERT INTO employee (emp_id, name, department, designation) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, empId);
            stmt.setString(2, name);
            stmt.setString(3, dept);
            stmt.setString(4, desig);

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Employee Added Successfully!");
            clearFields();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        empIdField.setText("");
        nameField.setText("");
        deptField.setText("");
        desigField.setText("");
    }
}
