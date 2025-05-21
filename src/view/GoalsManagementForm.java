package view;

import controller.GoalController;
import controller.TransactionController;
import controller.UserController;
import java.math.BigDecimal;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Goal;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author USER
 */
public class GoalsManagementForm extends javax.swing.JFrame {

    private GoalController goalController;
    private TransactionController transactionController;
    private UserController userController;
    private int userId;

    public GoalsManagementForm(GoalController goalController, 
                         TransactionController transactionController,
                         UserController userController, 
                         int userId) {
    this.goalController = goalController;
    this.transactionController = transactionController;
    this.userController = userController;
    this.userId = userId;
    initComponents();
    loadGoals();
    setLocationRelativeTo(null);
}

// Keep this if NetBeans generated it
public GoalsManagementForm() {
    initComponents();
}

    GoalsManagementForm(int userId, UserController userController, TransactionController transactionController, GoalController goalController) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void loadGoals() {
        List<Goal> goals = goalController.getUserGoals(userId);
        DefaultTableModel model = (DefaultTableModel) goalsTable.getModel();
        model.setRowCount(0); // Clear existing data

        for (Goal goal : goals) {
            double progress = goalController.getGoalProgressPercentage(goal.getGoalId());
            model.addRow(new Object[]{
                goal.getGoalId(),
                goal.getGoalName(),
                String.format("$%.2f", goal.getTargetAmount()),
                String.format("$%.2f", goal.getCurrentAmount()),
                String.format("%.1f%%", progress)
            });
        }
    }

    private void createGoal() {
        try {
            String goalName = goalNameField.getText().trim();
            BigDecimal targetAmount = new BigDecimal(targetAmountField.getText().trim());

            if (goalName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a goal name", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (targetAmount.compareTo(BigDecimal.ZERO) <= 0) {
                JOptionPane.showMessageDialog(this, "Target amount must be positive", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (goalController.createGoal(userId, goalName, targetAmount)) {
                JOptionPane.showMessageDialog(this, "Goal created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                clearFields();
                loadGoals();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to create goal", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid amount", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateGoal() {
        int selectedRow = goalsTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a goal to update", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int goalId = (Integer) goalsTable.getValueAt(selectedRow, 0);
            String newName = goalNameField.getText().trim();
            BigDecimal newTarget = new BigDecimal(targetAmountField.getText().trim());

            if (goalController.updateGoal(goalId, newName, newTarget)) {
                JOptionPane.showMessageDialog(this, "Goal updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadGoals();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update goal", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid amount", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteGoal() {
        int selectedRow = goalsTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a goal to delete", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
            this, 
            "Are you sure you want to delete this goal?", 
            "Confirm Delete", 
            JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            int goalId = (Integer) goalsTable.getValueAt(selectedRow, 0);
            if (goalController.deleteGoal(goalId)) {
                JOptionPane.showMessageDialog(this, "Goal deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                clearFields();
                loadGoals();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete goal", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void populateFieldsFromSelectedRow() {
        int selectedRow = goalsTable.getSelectedRow();
        if (selectedRow >= 0) {
            goalNameField.setText(goalsTable.getValueAt(selectedRow, 1).toString());
            // Remove $ sign before displaying in edit field
            targetAmountField.setText(goalsTable.getValueAt(selectedRow, 2).toString().replace("$", ""));
        }
    }

    private void clearFields() {
        goalNameField.setText("");
        targetAmountField.setText("");
    }

    private void returnToDashboard() {
        DashboardForm dashboard = new DashboardForm(
                userId, 
                userController, 
                transactionController, 
                goalController
            );
            dashboard.setVisible(true);
            this.dispose();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        CreateGoalBtn = new javax.swing.JButton();
        goalNameField = new javax.swing.JTextField();
        BackToDashboardBtn = new javax.swing.JButton();
        targetAmountField = new javax.swing.JTextField();
        DeleteGoalBtn = new javax.swing.JButton();
        UpdateGoalBtn = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        goalsTable = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        BalanceLabel = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(44, 64, 93));

        jPanel2.setBackground(new java.awt.Color(139, 140, 137));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Create New Goal"));
        jPanel2.setName("New Transaction"); // NOI18N

        CreateGoalBtn.setBackground(new java.awt.Color(44, 64, 93));
        CreateGoalBtn.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        CreateGoalBtn.setForeground(new java.awt.Color(139, 140, 137));
        CreateGoalBtn.setText("Create a Goal");
        CreateGoalBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CreateGoalBtnActionPerformed(evt);
            }
        });

        goalNameField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                goalNameFieldActionPerformed(evt);
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

        targetAmountField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                targetAmountFieldActionPerformed(evt);
            }
        });

        DeleteGoalBtn.setBackground(new java.awt.Color(44, 64, 93));
        DeleteGoalBtn.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        DeleteGoalBtn.setForeground(new java.awt.Color(139, 140, 137));
        DeleteGoalBtn.setText("Delete Goal");
        DeleteGoalBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteGoalBtnActionPerformed(evt);
            }
        });

        UpdateGoalBtn.setBackground(new java.awt.Color(44, 64, 93));
        UpdateGoalBtn.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        UpdateGoalBtn.setForeground(new java.awt.Color(139, 140, 137));
        UpdateGoalBtn.setText("Update Goal");
        UpdateGoalBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UpdateGoalBtnActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(44, 64, 93));
        jLabel2.setText("Goal Names:");

        jLabel3.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(44, 64, 93));
        jLabel3.setText("Target amount:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(63, 63, 63)
                        .addComponent(targetAmountField)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(UpdateGoalBtn)
                            .addComponent(DeleteGoalBtn))
                        .addGap(39, 39, 39))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(CreateGoalBtn)
                                .addGap(37, 37, 37)
                                .addComponent(BackToDashboardBtn)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addComponent(jLabel2)
                                .addGap(63, 63, 63)
                                .addComponent(goalNameField, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE)
                                .addGap(156, 156, 156))))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(UpdateGoalBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2)
                    .addComponent(goalNameField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(jLabel3)
                        .addGap(12, 12, 12))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(DeleteGoalBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(targetAmountField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CreateGoalBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BackToDashboardBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(59, 59, 59))
        );

        jPanel3.setBackground(new java.awt.Color(139, 140, 137));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Your Savings Goals"));

        goalsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Goal", "Target Amount", "Curent Amount", "Progress"
            }
        ));
        goalsTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                goalsTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(goalsTable);

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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        jLabel1.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(139, 140, 137));
        jLabel1.setText("Current Ballance");

        BalanceLabel.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        BalanceLabel.setForeground(new java.awt.Color(139, 140, 137));
        BalanceLabel.setText("$0");

        jLabel4.setIcon(new javax.swing.ImageIcon("C:\\Users\\USER\\Downloads\\logo-2-square.png")); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(152, 152, 152)
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(BalanceLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(113, 113, 113)
                        .addComponent(jLabel4)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(BalanceLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
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
    }// </editor-fold>//GEN-END:initComponents

    private void goalNameFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_goalNameFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_goalNameFieldActionPerformed

    private void targetAmountFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_targetAmountFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_targetAmountFieldActionPerformed

    private void goalsTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_goalsTableMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_goalsTableMouseClicked

    private void CreateGoalBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CreateGoalBtnActionPerformed
        // TODO add your handling code here:
        createGoal();
    }//GEN-LAST:event_CreateGoalBtnActionPerformed

    private void BackToDashboardBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BackToDashboardBtnActionPerformed
        // TODO add your handling code here:
        returnToDashboard();
    }//GEN-LAST:event_BackToDashboardBtnActionPerformed

    private void UpdateGoalBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UpdateGoalBtnActionPerformed
        // TODO add your handling code here:
        updateGoal();
    }//GEN-LAST:event_UpdateGoalBtnActionPerformed

    private void DeleteGoalBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteGoalBtnActionPerformed
        // TODO add your handling code here:
        deleteGoal();
    }//GEN-LAST:event_DeleteGoalBtnActionPerformed

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
            java.util.logging.Logger.getLogger(GoalsManagementForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GoalsManagementForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GoalsManagementForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GoalsManagementForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GoalsManagementForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BackToDashboardBtn;
    private javax.swing.JLabel BalanceLabel;
    private javax.swing.JButton CreateGoalBtn;
    private javax.swing.JButton DeleteGoalBtn;
    private javax.swing.JButton UpdateGoalBtn;
    private javax.swing.JTextField goalNameField;
    private javax.swing.JTable goalsTable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField targetAmountField;
    // End of variables declaration//GEN-END:variables
}
