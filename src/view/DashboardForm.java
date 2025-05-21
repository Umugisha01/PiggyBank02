package view;

import controller.UserController;
import controller.TransactionController;
import controller.GoalController;
import model.Transaction;
import model.Goal;
import javax.swing.table.DefaultTableModel;
import java.math.BigDecimal;
import java.util.List;
import javax.swing.JOptionPane;
/**
 *
 * @author USER
 */
public class DashboardForm extends javax.swing.JFrame {

    private int userId;
    private UserController userController;
    private TransactionController transactionController;
    private GoalController goalController;

    public DashboardForm(int userId, UserController userController,
                        TransactionController transactionController,
                        GoalController goalController) {
        this.userId = userId;
        this.userController = userController;
        this.transactionController = transactionController;
        this.goalController = goalController;
        initComponents();
        jLabel1.setText("Current Balance:");
        refreshDashboardData();
    }

   public DashboardForm() {
    // Initialize your dashboard components here
    initComponents();
    // Other initialization code
}

    

    private void refreshDashboardData() {
        try {
            // Update balance
            BigDecimal balance = transactionController.getCurrentBalance(userId);
            BalanceLabel.setText(String.format("$%.2f", balance));

            // Update transactions
            List<Transaction> transactions = transactionController.getTransactionHistory(userId);
            updateTransactionTable(transactions);

            // Update goals
            List<Goal> goals = goalController.getUserGoals(userId);
            updateGoalTable(goals);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

private void updateTransactionTable(List<Transaction> transactions) {
        String[] columns = {"ID", "Amount", "Type", "Date"};
        Object[][] data = new Object[transactions.size()][4];
        for (int i = 0; i < transactions.size(); i++) {
            Transaction t = transactions.get(i);
            data[i] = new Object[]{
                t.getTransactionId(),
                String.format("$%.2f", t.getAmount()),
                t.getTransactionType(),
                t.getDate()
            };
        }
        recentTransactionsTable.setModel(new DefaultTableModel(data, columns));
    }

    private void updateGoalTable(List<Goal> goals) {
        String[] columns = {"Goal", "Target", "Current", "Progress"};
        Object[][] data = new Object[goals.size()][4];
        for (int i = 0; i < goals.size(); i++) {
            Goal g = goals.get(i);
            double progress = goalController.getGoalProgressPercentage(g.getGoalId());
            data[i] = new Object[]{
                g.getGoalName(),
                String.format("$%.2f", g.getTargetAmount()),
                String.format("$%.2f", g.getCurrentAmount()),
                String.format("%.1f%%", progress)
            };
        }
        goalsTable.setModel(new DefaultTableModel(data, columns));
    }
    
   
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        ManageTransactionsBtn = new javax.swing.JButton();
        ManageGoalsBtn = new javax.swing.JButton();
        LogOutBtn = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        BalanceLabel = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        recentTransactionsTable = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        goalsTable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(44, 64, 93));

        ManageTransactionsBtn.setBackground(new java.awt.Color(139, 140, 137));
        ManageTransactionsBtn.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        ManageTransactionsBtn.setForeground(new java.awt.Color(44, 64, 93));
        ManageTransactionsBtn.setText("Manage Transactions");
        ManageTransactionsBtn.setFocusPainted(false);
        ManageTransactionsBtn.setFocusable(false);
        ManageTransactionsBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ManageTransactionsBtnActionPerformed(evt);
            }
        });

        ManageGoalsBtn.setBackground(new java.awt.Color(139, 140, 137));
        ManageGoalsBtn.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        ManageGoalsBtn.setForeground(new java.awt.Color(44, 64, 93));
        ManageGoalsBtn.setText("Manage Goals");
        ManageGoalsBtn.setDefaultCapable(false);
        ManageGoalsBtn.setFocusPainted(false);
        ManageGoalsBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ManageGoalsBtnActionPerformed(evt);
            }
        });

        LogOutBtn.setBackground(new java.awt.Color(139, 140, 137));
        LogOutBtn.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        LogOutBtn.setForeground(new java.awt.Color(44, 64, 93));
        LogOutBtn.setText("LogOut");
        LogOutBtn.setBorderPainted(false);
        LogOutBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        LogOutBtn.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        LogOutBtn.setDefaultCapable(false);
        LogOutBtn.setFocusPainted(false);
        LogOutBtn.setFocusable(false);
        LogOutBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LogOutBtnActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(139, 140, 137));
        jLabel1.setText("Current Ballance:");

        BalanceLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        BalanceLabel.setForeground(new java.awt.Color(139, 140, 137));
        BalanceLabel.setText("$0");

        jLabel3.setIcon(new javax.swing.ImageIcon("C:\\Users\\USER\\Downloads\\logo-2-square.png")); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(172, 172, 172)
                                .addComponent(jLabel1))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(90, 90, 90)
                                .addComponent(ManageTransactionsBtn)))
                        .addGap(16, 16, 16)
                        .addComponent(ManageGoalsBtn)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(291, 291, 291)
                                .addComponent(BalanceLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel3)
                                .addGap(45, 45, 45)))
                        .addComponent(LogOutBtn)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(BalanceLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(LogOutBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ManageTransactionsBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ManageGoalsBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(139, 140, 137));

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Recent Transactions"));
        jPanel4.setForeground(new java.awt.Color(44, 64, 93));

        recentTransactionsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "Amount", "Type", "Date"
            }
        ));
        jScrollPane1.setViewportView(recentTransactionsTable);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 435, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 8, Short.MAX_VALUE))
        );

        jScrollPane2.setBorder(javax.swing.BorderFactory.createTitledBorder("Savings Goals"));

        goalsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Goal", "Target", "Current ", "Progeress"
            }
        ));
        jScrollPane3.setViewportView(goalsTable);

        jScrollPane2.setViewportView(jScrollPane3);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 467, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
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
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void ManageTransactionsBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ManageTransactionsBtnActionPerformed
        // TODO add your handling code here:
        TransactionForm transactionForm = new TransactionForm(userId, userController, transactionController, goalController);
        transactionForm.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_ManageTransactionsBtnActionPerformed

    private void ManageGoalsBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ManageGoalsBtnActionPerformed
        // TODO add your handling code here:
        GoalsManagementForm goalsForm = new GoalsManagementForm(
        goalController, 
        transactionController, 
        userController, 
        userId
    );
    goalsForm.setVisible(true);
    this.dispose();
    }//GEN-LAST:event_ManageGoalsBtnActionPerformed

    private void LogOutBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LogOutBtnActionPerformed
        // TODO add your handling code here:
        LoginForm loginForm = new LoginForm(userController, transactionController, goalController);
        loginForm.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_LogOutBtnActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DashboardForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DashboardForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DashboardForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DashboardForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DashboardForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel BalanceLabel;
    private javax.swing.JButton LogOutBtn;
    private javax.swing.JButton ManageGoalsBtn;
    private javax.swing.JButton ManageTransactionsBtn;
    private javax.swing.JTable goalsTable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable recentTransactionsTable;
    // End of variables declaration//GEN-END:variables
}
