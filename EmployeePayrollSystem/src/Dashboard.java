import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Dashboard extends JFrame {
    JButton addEmployeeBtn, addSalaryBtn, addDeductionBtn, viewPayslipBtn;

    public Dashboard() {
        setTitle("Dashboard");

        // Increase the window size to accommodate the image
        setSize(800, 600);  // Set a larger size for the window
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set the layout for the frame and panel
        setLayout(new BorderLayout());
        JPanel panel = new JPanel();

        // Load the image
        ImageIcon imageIcon = new ImageIcon("C:\\Users\\siyac\\OneDrive\\Pictures\\Screenshots\\Screenshot 2025-04-27 174129.png");  // Replace with your image path

        // Scale the image to fit the window's width (800px)
        Image image = imageIcon.getImage();
        Image scaledImage = image.getScaledInstance(800, 300, Image.SCALE_SMOOTH);  // Resize image to fit 800px width

        // Create a JLabel with the scaled image
        JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));

        // Add image at the top
        add(imageLabel, BorderLayout.NORTH);

        // Panel with FlowLayout for buttons and added vertical spacing
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 20));  // 20px vertical space between buttons

        // Add a spacer at the top to shift the buttons downward
        JPanel spacer = new JPanel();
        spacer.setPreferredSize(new Dimension(800, 50));  // Spacer of 50px height
        panel.add(spacer);

        // Increase button size and font
        addEmployeeBtn = new JButton("Add Employee");
        addEmployeeBtn.setFont(new Font("Arial", Font.PLAIN, 20)); // Set larger font
        addEmployeeBtn.setPreferredSize(new Dimension(250, 50)); // Set larger size
        addEmployeeBtn.addActionListener(e -> new AddEmployeeFrame());
        panel.add(addEmployeeBtn);

        addSalaryBtn = new JButton("Add Salary");
        addSalaryBtn.setFont(new Font("Arial", Font.PLAIN, 20)); // Set larger font
        addSalaryBtn.setPreferredSize(new Dimension(250, 50)); // Set larger size
        addSalaryBtn.addActionListener(e -> new AddSalaryFrame());
        panel.add(addSalaryBtn);

        addDeductionBtn = new JButton("Add Deduction");
        addDeductionBtn.setFont(new Font("Arial", Font.PLAIN, 20)); // Set larger font
        addDeductionBtn.setPreferredSize(new Dimension(250, 50)); // Set larger size
        addDeductionBtn.addActionListener(e -> new AddDeductionFrame());
        panel.add(addDeductionBtn);

        viewPayslipBtn = new JButton("View Payslip");
        viewPayslipBtn.setFont(new Font("Arial", Font.PLAIN, 20)); // Set larger font
        viewPayslipBtn.setPreferredSize(new Dimension(250, 50)); // Set larger size
        viewPayslipBtn.addActionListener(e -> new PayslipFrame());
        panel.add(viewPayslipBtn);

        add(panel, BorderLayout.CENTER); // Add the panel with buttons in the center

        setVisible(true);
    }

    public static void main(String[] args) {
        new Dashboard();
    }
}
