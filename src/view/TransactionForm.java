package view;

import controller.UserController;
import controller.TransactionController;
import controller.GoalController;
import model.Transaction;
import model.Goal;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author USER
 */
public class TransactionForm extends javax.swing.JFrame {

     private int userId;
    private UserController userController;
    private TransactionController transactionController;
    private GoalController goalController;

    public TransactionForm(int userId, UserController userController,
                   TransactionController transactionController,
                   GoalController goalController) {
    this.userId = userId;
    this.userController = userController;
    this.transactionController = transactionController;
    this.goalController = goalController;
    initComponents();
    
    // Initialize with empty model first
    goalComboBox.setModel(new DefaultComboBoxModel<>());
    goalComboBox.addItem(new Goal(0, "-- No Goal --", BigDecimal.ZERO));
    
    refreshTransactionData();
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
}


    private TransactionForm() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void refreshTransactionData() {
        try {
            BigDecimal balance = transactionController.getCurrentBalance(userId);
            balanceLabel.setText(String.format("$%.2f", balance));

            List<Transaction> transactions = transactionController.getTransactionHistory(userId);
            String[] columns = {"ID", "Amount", "Type", "Date", "Goal"};
            Object[][] data = new Object[transactions.size()][5];

            for (int i = 0; i < transactions.size(); i++) {
                Transaction t = transactions.get(i);
                data[i] = new Object[]{
                    t.getTransactionId(),
                    String.format("$%.2f", t.getAmount()),
                    t.getTransactionType(),
                    t.getDate(),
                    t.getGoalName() != null ? t.getGoalName() : "--"
                };
            }

            transactionsTable.setModel(new DefaultTableModel(data, columns));
            loadGoalsToComboBox();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error loading transaction data: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadGoalsToComboBox() {
    try {
        DefaultComboBoxModel<Goal> model = new DefaultComboBoxModel<>();
        model.addElement(new Goal(0, "-- No Goal --", BigDecimal.ZERO));

        List<Goal> goals = goalController.getUserGoals(userId);
        for (Goal goal : goals) {
            model.addElement(goal);
        }
        goalComboBox.setModel(model);
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this,
            "Error loading goals: " + e.getMessage(),
            "Error", JOptionPane.ERROR_MESSAGE);
    }
}
    
    private void processTransaction() {
        try {
            BigDecimal amount = new BigDecimal(amountField.getText());
            if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("Amount must be positive");
            }

            boolean isDeposit = "Deposit".equals(transactionTypeComboBox.getSelectedItem());
            Goal selectedGoal = (Goal) goalComboBox.getSelectedItem();
            Integer goalId = (selectedGoal != null && selectedGoal.getGoalId() != 0) ? selectedGoal.getGoalId() : null;

            boolean transactionSuccess;
            if (isDeposit) {
                transactionSuccess = transactionController.deposit(userId, amount, goalId);
                if (transactionSuccess && goalId != null) {
                    goalController.addFundsToGoal(goalId, amount);
                }
            } else {
                BigDecimal currentBalance = transactionController.getCurrentBalance(userId);
                if (amount.compareTo(currentBalance) > 0) {
                    throw new IllegalArgumentException("Insufficient funds for withdrawal");
                }
                transactionSuccess = transactionController.withdraw(userId, amount);
            }

            if (transactionSuccess) {
                JOptionPane.showMessageDialog(this, "Transaction Successful!", 
                    "Success", JOptionPane.INFORMATION_MESSAGE);
                amountField.setText("");
                refreshTransactionData();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Transaction Failed", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid amount format", 
                "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error processing transaction: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        SubmitTransactionBtn = new javax.swing.JButton();
        transactionTypeComboBox = new javax.swing.JComboBox<>();
        goalComboBox = new javax.swing.JComboBox<>();
        amountField = new javax.swing.JTextField();
        BackToDashboardBtn = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        transactionsTable = new javax.swing.JTable();
        balanceLabel = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(44, 64, 93));

        jPanel2.setBackground(new java.awt.Color(139, 140, 137));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("New Transaction"));
        jPanel2.setName("New Transaction"); // NOI18N

        SubmitTransactionBtn.setBackground(new java.awt.Color(44, 64, 93));
        SubmitTransactionBtn.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        SubmitTransactionBtn.setForeground(new java.awt.Color(139, 140, 137));
        SubmitTransactionBtn.setText("Submit Transaction");
        SubmitTransactionBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SubmitTransactionBtnActionPerformed(evt);
            }
        });

        transactionTypeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Deposit", "Withdraw" }));
        transactionTypeComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                transactionTypeComboBoxActionPerformed(evt);
            }
        });

        goalComboBox.setModel(goalComboBox.getModel());
        goalComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                goalComboBoxActionPerformed(evt);
            }
        });

        amountField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                amountFieldActionPerformed(evt);
            }
        });

        BackToDashboardBtn.setBackground(new java.awt.Color(44, 64, 93));
        BackToDashboardBtn.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        BackToDashboardBtn.setForeground(new java.awt.Color(139, 140, 137));
        BackToDashboardBtn.setText("Back to Dashboard");
        BackToDashboardBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BackToDashboardBtnActionPerformed(evt);
            }
        });

        jLabel2.setBackground(new java.awt.Color(44, 64, 93));
        jLabel2.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(44, 64, 93));
        jLabel2.setText("Ammount:");

        jLabel3.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(44, 64, 93));
        jLabel3.setText("Transaction Type:");

        jLabel4.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(44, 64, 93));
        jLabel4.setText("Allocate to Goal: ");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3))
                        .addGap(53, 53, 53)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(transactionTypeComboBox, 0, 91, Short.MAX_VALUE)
                            .addComponent(goalComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(amountField))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(SubmitTransactionBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 117, Short.MAX_VALUE)
                        .addComponent(BackToDashboardBtn)
                        .addGap(18, 18, 18))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2))
                    .addComponent(amountField, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(transactionTypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(goalComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 18, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(SubmitTransactionBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(BackToDashboardBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(6, 6, 6))
        );

        jPanel3.setBackground(new java.awt.Color(139, 140, 137));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Transaction History"));
        jPanel3.setForeground(new java.awt.Color(44, 64, 93));

        transactionsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Amount", "Type", "Date", "Goal"
            }
        ));
        jScrollPane1.setViewportView(transactionsTable);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(86, Short.MAX_VALUE))
        );

        balanceLabel.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        balanceLabel.setForeground(new java.awt.Color(139, 140, 137));
        balanceLabel.setText("$0");

        jLabel1.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(139, 140, 137));
        jLabel1.setText("Current Ballance");

        jLabel5.setIcon(new javax.swing.ImageIcon("C:\\Users\\USER\\Downloads\\logo-2-square.png")); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(133, 133, 133)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(162, 162, 162)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(balanceLabel))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(balanceLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void amountFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_amountFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_amountFieldActionPerformed

    private void transactionTypeComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_transactionTypeComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_transactionTypeComboBoxActionPerformed

    private void goalComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_goalComboBoxActionPerformed
        // TODO add your handling code here:
    DefaultComboBoxModel<Goal> model = new DefaultComboBoxModel<>();
    model.addElement(new Goal(0, "-- No Goal --", BigDecimal.ZERO));
    goalComboBox.setModel(model);
    }//GEN-LAST:event_goalComboBoxActionPerformed

    private void SubmitTransactionBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SubmitTransactionBtnActionPerformed
        // TODO add your handling code here:
        processTransaction();
    }//GEN-LAST:event_SubmitTransactionBtnActionPerformed

    private void BackToDashboardBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BackToDashboardBtnActionPerformed
        // TODO add your handling code here:
        DashboardForm dashboard = new DashboardForm(
            userId, 
            userController, 
            transactionController, 
            goalController
        );
        dashboard.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_BackToDashboardBtnActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BackToDashboardBtn;
    private javax.swing.JButton SubmitTransactionBtn;
    private javax.swing.JTextField amountField;
    private javax.swing.JLabel balanceLabel;
    private javax.swing.JComboBox<Goal> goalComboBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox<String> transactionTypeComboBox;
    private javax.swing.JTable transactionsTable;
    // End of variables declaration//GEN-END:variables
}
