package controller;

import model.Transaction;
import model.Transaction.TransactionType;
import dao.TransactionDAO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class TransactionController {
    private TransactionDAO transactionDAO;

    public TransactionController() {
        this.transactionDAO = new TransactionDAO();
    }

    public boolean deposit(int userId, BigDecimal amount, Integer goalId) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }

        Transaction depositTransaction = new Transaction(
            userId,
            amount,
            TransactionType.DEPOSIT,
            LocalDate.now()
        );
        
        if (goalId != null && goalId > 0) {
            depositTransaction.setGoalId(goalId);
        }

        return transactionDAO.addTransaction(depositTransaction);
    }

    public boolean withdraw(int userId, BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }

        BigDecimal currentBalance = transactionDAO.calculateUserBalance(userId);
        if (amount.compareTo(currentBalance) > 0) {
            return false;
        }

        Transaction withdrawalTransaction = new Transaction(
            userId,
            amount,
            TransactionType.WITHDRAWAL,
            LocalDate.now()
        );

        return transactionDAO.addTransaction(withdrawalTransaction);
    }

    public List<Transaction> getTransactionHistory(int userId) {
        return transactionDAO.getTransactionsByUserId(userId);
    }

    public BigDecimal getCurrentBalance(int userId) {
        return transactionDAO.calculateUserBalance(userId);
    }

    public List<Transaction> getTransactionsByDateRange(int userId, LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null || startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Invalid date range");
        }
        return transactionDAO.getTransactionsByDateRange(userId, startDate, endDate);
    }

    public boolean updateTransaction(Transaction transaction) {
        if (transaction == null || transaction.getTransactionId() <= 0) {
            return false;
        }
        return transactionDAO.updateTransaction(transaction);
    }

    public boolean deleteTransaction(int transactionId) {
        if (transactionId <= 0) {
            return false;
        }
        return transactionDAO.deleteTransaction(transactionId);
    }

    public void closeConnection() {
        transactionDAO.closeConnection();
    }

    public BigDecimal calculateUserBalance(int userId) {
    return transactionDAO.calculateUserBalance(userId);
    }
}