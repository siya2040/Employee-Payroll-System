import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    public static Connection getConnection() throws Exception {
        String url = "jdbc:mysql://localhost:3306/payroll_system";
        String username = "root";  // Your MySQL username
        String password = "Siya@1234";  // Your MySQL password
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(url, username, password);
    }
}
