import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

// Custom JPanel to draw the background image
class ImagePanel extends JPanel {
    private Image backgroundImage;

    public ImagePanel(String imagePath) {
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

public class AddDeductionFrame extends JFrame implements ActionListener {
    JTextField empIdField, taxField, insuranceField, otherField;
    JButton saveBtn;

    public AddDeductionFrame() {
        setTitle("Add Deduction Details");
        setSize(700, 400); // Adjusted size to accommodate the image
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create the image panel with the path to your image
        ImagePanel imagePanel = new ImagePanel("C:\\Users\\siyac\\OneDrive\\Pictures\\Screenshots\\Screenshot 2025-04-27 171901.png");
        imagePanel.setPreferredSize(new Dimension(300, 400)); // Adjust the width of the image panel

        // Form Panel
        JPanel formPanel = new JPanel(new BorderLayout()); // Use BorderLayout for better control
        formPanel.setOpaque(true); // So the background color is visible
        formPanel.setBackground(new Color(224, 255, 255)); // Lighter blue background (#E0FFFF)

        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 10, 10)); // Sub-panel for form fields
        inputPanel.setOpaque(true); // To make background color visible
        inputPanel.setBackground(new Color(224, 255, 255)); // Lighter blue background (#E0FFFF)

        Font labelFont = new Font("Arial", Font.BOLD, 18); // Increased font size for labels
        Font fieldFont = new Font("Arial", Font.PLAIN, 16); // Larger font for text fields

        inputPanel.add(new JLabel("Employee ID:"));
        empIdField = new JTextField();
        empIdField.setFont(fieldFont);
        inputPanel.add(empIdField);

        inputPanel.add(new JLabel("Tax:"));
        taxField = new JTextField();
        taxField.setFont(fieldFont);
        inputPanel.add(taxField);

        inputPanel.add(new JLabel("Insurance:"));
        insuranceField = new JTextField();
        insuranceField.setFont(fieldFont);
        inputPanel.add(insuranceField);

        inputPanel.add(new JLabel("Other Deductions:"));
        otherField = new JTextField();
        otherField.setFont(fieldFont);
        inputPanel.add(otherField);

        formPanel.add(inputPanel, BorderLayout.CENTER);

        // Save Button Panel at the bottom-right
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // Align right
        buttonPanel.setOpaque(false); // Transparent background
        saveBtn = new JButton("Save");
        saveBtn.setFont(new Font("Arial", Font.BOLD, 16)); // Larger font for button
        saveBtn.addActionListener(this);
        buttonPanel.add(saveBtn);
        formPanel.add(buttonPanel, BorderLayout.SOUTH); // Place the button at the bottom

        // Create a split pane to separate the image and form
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, imagePanel, formPanel);
        splitPane.setDividerLocation(300); // Adjust this to resize the image/form proportion
        splitPane.setResizeWeight(0.3); // This keeps both sides resizable

        // Add the split pane to the frame
        add(splitPane, BorderLayout.CENTER);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            int empId = Integer.parseInt(empIdField.getText());
            double tax = Double.parseDouble(taxField.getText());
            double insurance = Double.parseDouble(insuranceField.getText());
            double other = Double.parseDouble(otherField.getText());

            Connection conn = DBConnection.getConnection();
            String query = "INSERT INTO deduction (emp_id, tax, insurance, other_deductions) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, empId);
            stmt.setDouble(2, tax);
            stmt.setDouble(3, insurance);
            stmt.setDouble(4, other);

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Deduction Details Added Successfully!");
            clearFields();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void clearFields() {
        empIdField.setText("");
        taxField.setText("");
        insuranceField.setText("");
        otherField.setText("");
    }

    public static void main(String[] args) {
        new AddDeductionFrame();
    }
}
