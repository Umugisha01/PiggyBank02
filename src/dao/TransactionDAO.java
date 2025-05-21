package dao;

import model.Transaction;
import model.Transaction.TransactionType;
import java.sql.*;
import java.time.LocalDate;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {
    private Connection connection;

    public TransactionDAO() {
        try {
            this.connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Create a new transaction
    public boolean addTransaction(Transaction transaction) {
        if (!transaction.isValidAmount() || !transaction.isValidDate()) {
            return false;
        }

        String sql = "INSERT INTO Transactions (user_id, amount, transaction_type, date, goal_id) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, transaction.getUserId());
            pstmt.setBigDecimal(2, transaction.getAmount());
            pstmt.setString(3, transaction.getTransactionType().name());
            pstmt.setDate(4, Date.valueOf(transaction.getDate()));
            
            if (transaction.getGoalId() != null && transaction.getGoalId() > 0) {
                pstmt.setInt(5, transaction.getGoalId());
            } else {
                pstmt.setNull(5, Types.INTEGER);
            }

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        transaction.setTransactionId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Get transactions for a specific user
    public List<Transaction> getTransactionsByUserId(int userId) {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT t.*, g.goal_name FROM Transactions t LEFT JOIN Goals g ON t.goal_id = g.goal_id WHERE t.user_id = ? ORDER BY t.date DESC";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, userId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Transaction transaction = new Transaction(
                        rs.getInt("user_id"),
                        rs.getBigDecimal("amount"),
                        TransactionType.valueOf(rs.getString("transaction_type").toUpperCase()),
                        rs.getDate("date").toLocalDate()
                    );
                    transaction.setTransactionId(rs.getInt("transaction_id"));
                    if (rs.getObject("goal_id") != null) {
                        transaction.setGoalId(rs.getInt("goal_id"));
                        transaction.setGoalName(rs.getString("goal_name"));
                    }
                    transactions.add(transaction);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    // Calculate total balance for a user
    public BigDecimal calculateUserBalance(int userId) {
        String sql = "SELECT SUM(CASE WHEN transaction_type = 'DEPOSIT' THEN amount ELSE -amount END) AS balance " +
                     "FROM Transactions WHERE user_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, userId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    BigDecimal balance = rs.getBigDecimal("balance");
                    return balance != null ? balance : BigDecimal.ZERO;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return BigDecimal.ZERO;
    }

    // Get transactions within a date range
    public List<Transaction> getTransactionsByDateRange(int userId, LocalDate startDate, LocalDate endDate) {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT t.*, g.goal_name FROM Transactions t LEFT JOIN Goals g ON t.goal_id = g.goal_id " +
                     "WHERE t.user_id = ? AND t.date BETWEEN ? AND ? ORDER BY t.date DESC";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setDate(2, Date.valueOf(startDate));
            pstmt.setDate(3, Date.valueOf(endDate));

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Transaction transaction = new Transaction(
                        rs.getInt("user_id"),
                        rs.getBigDecimal("amount"),
                        TransactionType.valueOf(rs.getString("transaction_type").toUpperCase()),
                        rs.getDate("date").toLocalDate()
                    );
                    transaction.setTransactionId(rs.getInt("transaction_id"));
                    if (rs.getObject("goal_id") != null) {
                        transaction.setGoalId(rs.getInt("goal_id"));
                        transaction.setGoalName(rs.getString("goal_name"));
                    }
                    transactions.add(transaction);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    // Update an existing transaction
    public boolean updateTransaction(Transaction transaction) {
        if (!transaction.isValidAmount() || !transaction.isValidDate()) {
            return false;
        }

        String sql = "UPDATE Transactions SET amount = ?, transaction_type = ?, date = ?, goal_id = ? WHERE transaction_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setBigDecimal(1, transaction.getAmount());
            pstmt.setString(2, transaction.getTransactionType().name());
            pstmt.setDate(3, Date.valueOf(transaction.getDate()));
            
            if (transaction.getGoalId() != null && transaction.getGoalId() > 0) {
                pstmt.setInt(4, transaction.getGoalId());
            } else {
                pstmt.setNull(4, Types.INTEGER);
            }
            
            pstmt.setInt(5, transaction.getTransactionId());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Delete a transaction
    public boolean deleteTransaction(int transactionId) {
        String sql = "DELETE FROM Transactions WHERE transaction_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, transactionId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Close the database connection
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}