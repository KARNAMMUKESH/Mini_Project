package online_ticket;

import java.sql.*;

public class JDBCConnection {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/it_support";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "mukesh@7";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    }
}
