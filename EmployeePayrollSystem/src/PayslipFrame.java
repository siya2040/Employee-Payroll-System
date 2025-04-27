import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class PayslipFrame extends JFrame implements ActionListener {
    JTextField empIdField;
    JTextArea payslipArea;
    JButton generateBtn;

    public PayslipFrame() {
        setTitle("Generate Payslip");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set background color of the frame to a pastel green
        getContentPane().setBackground(new Color(204, 255, 204)); // Pastel green background

        // Top panel with employee ID field and button
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(204, 255, 204)); // Same pastel green for consistency
        topPanel.add(new JLabel("Employee ID:"));
        empIdField = new JTextField(10);
        topPanel.add(empIdField);

        generateBtn = new JButton("Generate Payslip");
        generateBtn.addActionListener(this);
        topPanel.add(generateBtn);

        // Create the payslip area with a soft white background and some padding
        payslipArea = new JTextArea();
        payslipArea.setEditable(false);
        payslipArea.setBackground(new Color(255, 255, 255)); // White background for the text area
        payslipArea.setFont(new Font("Arial", Font.PLAIN, 14)); // Set font size for better readability

        // Add a scroll pane around the payslip area to handle overflow text
        JScrollPane scrollPane = new JScrollPane(payslipArea);
        scrollPane.setBackground(new Color(255, 255, 255)); // Match the background of the text area

        // Set the layout for the frame
        setLayout(new BorderLayout());
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int empId = Integer.parseInt(empIdField.getText());

        try {
            Connection conn = DBConnection.getConnection();

            // Query to get employee details
            String empQuery = "SELECT * FROM employee WHERE emp_id = ?";
            PreparedStatement empStmt = conn.prepareStatement(empQuery);
            empStmt.setInt(1, empId);
            ResultSet empRs = empStmt.executeQuery();

            if (!empRs.next()) {
                JOptionPane.showMessageDialog(this, "Employee not found!");
                return;
            }

            String name = empRs.getString("name");
            String dept = empRs.getString("department");
            String desig = empRs.getString("designation");

            // Query to get salary details
            String salaryQuery = "SELECT * FROM salary WHERE emp_id = ?";
            PreparedStatement salaryStmt = conn.prepareStatement(salaryQuery);
            salaryStmt.setInt(1, empId);
            ResultSet salaryRs = salaryStmt.executeQuery();

            // Query to get deduction details
            String deductionQuery = "SELECT * FROM deduction WHERE emp_id = ?";
            PreparedStatement deducStmt = conn.prepareStatement(deductionQuery);
            deducStmt.setInt(1, empId);
            ResultSet deducRs = deducStmt.executeQuery();

            if (!salaryRs.next() || !deducRs.next()) {
                JOptionPane.showMessageDialog(this, "Salary or Deduction details missing!");
                return;
            }

            // Extract salary and deduction details
            double basic = salaryRs.getDouble("basic_salary");
            double bonus = salaryRs.getDouble("bonus");
            double allowance = salaryRs.getDouble("allowances");

            double tax = deducRs.getDouble("tax");
            double insurance = deducRs.getDouble("insurance");
            double other = deducRs.getDouble("other_deductions");

            // Calculate gross salary and deductions
            double grossSalary = basic + bonus + allowance;
            double totalDeductions = tax + insurance + other;
            double netSalary = grossSalary - totalDeductions;

            // Construct the payslip string
            StringBuilder slip = new StringBuilder();
            slip.append("----- PAYSLIP -----\n");
            slip.append("Name: " + name + "\n");
            slip.append("Department: " + dept + "\n");
            slip.append("Designation: " + desig + "\n\n");
            slip.append("Basic Salary: " + basic + "\n");
            slip.append("Bonus: " + bonus + "\n");
            slip.append("Allowances: " + allowance + "\n");
            slip.append("Gross Salary: " + grossSalary + "\n\n");
            slip.append("Tax: " + tax + "\n");
            slip.append("Insurance: " + insurance + "\n");
            slip.append("Other Deductions: " + other + "\n");
            slip.append("Total Deductions: " + totalDeductions + "\n\n");
            slip.append("NET SALARY: " + netSalary + "\n");

            // Set the payslip text to the generated string
            payslipArea.setText(slip.toString());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new PayslipFrame();
    }
}
