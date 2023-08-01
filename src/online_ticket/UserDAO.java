package online_ticket;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO extends DAO{
    public List<User> getAll() {
        List<User> users = new ArrayList<>();

        try (Connection conn = JDBCConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM users")) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String username = rs.getString("username");
                String role = rs.getString("role");
                String password = rs.getString("password");

                User user = new User(id, username, role, password);
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    public User getUserByUsername(String username) {
        try (Connection conn = JDBCConnection.getConnection()) {
            String query = "SELECT * FROM users WHERE username = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, username);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                String role = rs.getString("role");
                String password = rs.getString("password");
                return new User(id, username, role, password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // User not found
    }

    public void create(String username, String role, String password) {
        try (Connection conn = JDBCConnection.getConnection()) {
            String insertQuery = "INSERT INTO users (username, role, password) VALUES (?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(insertQuery);
            pstmt.setString(1, username);
            pstmt.setString(2, role);
            pstmt.setString(3, password);
            pstmt.executeUpdate();

            System.out.println("User created successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
class TicketDAO extends UserDAO {
    public List<Ticket> getAllTickets() {
        List<Ticket> tickets = new ArrayList<>();

        try (Connection conn = JDBCConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM tickets")) {

            while (rs.next()) {
                int id = rs.getInt("id");
                int userId = rs.getInt("user_id");
                String issueDescription = rs.getString("issue_description");
                String status = rs.getString("status");
                String createdAt = rs.getString("created_at");
                String resolvedAt = rs.getString("resolved_at");

                Ticket ticket = new Ticket(id, userId, issueDescription, status, createdAt, resolvedAt);
                tickets.add(ticket);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tickets;
    }

    public void create(int userId, String issueDescription) {
        try (Connection conn = JDBCConnection.getConnection()) {
            String insertQuery = "INSERT INTO tickets (user_id, issue_description, status) VALUES (?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(insertQuery);
            pstmt.setInt(1, userId);
            pstmt.setString(2, issueDescription);
            pstmt.setString(3, "OPEN");
            pstmt.executeUpdate();

            System.out.println("Ticket raised successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void resolveTicket(int ticketId) {
        try (Connection conn = JDBCConnection.getConnection()) {
            String updateQuery = "UPDATE tickets SET status = 'RESOLVED', resolved_at = CURRENT_TIMESTAMP WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(updateQuery);
            pstmt.setInt(1, ticketId);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Ticket resolved successfully!");
            } else {
                System.out.println("Ticket not found or already resolved.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

