package churchaccountmanager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class TransactionAddEdit extends javax.swing.JFrame {
        
    static String transactionID;
    
    private static TransactionAddEdit transactionAddEdit = new TransactionAddEdit();

    private TransactionAddEdit() {
        initComponents();
    }
    
    public boolean isNumeric(String s) {
    return java.util.regex.Pattern.matches("\\d+", s);
}

    public static void execAddInstance() {
        Calendar calendar = Calendar.getInstance();
        transactionAddEdit.years.setSelectedItem(calendar.get(Calendar.YEAR));
        transactionAddEdit.months.setSelectedIndex(calendar.get(Calendar.MONTH));
        transactionAddEdit.days.setSelectedItem(calendar.get(Calendar.DATE));
        transactionAddEdit.toAccount.setModel(MyFunctions.populateAccounts());
        transactionAddEdit.fromAccount.setModel(MyFunctions.populateAccounts());
        transactionAddEdit.acceptButton.setText("Add");
        transactionAddEdit.setTitle("Add");
        transactionAddEdit.setVisible(true);
    }

    public static void execEditInstance(String target) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat fromSQL = new SimpleDateFormat("yyyy-MM-dd");
        String oldToAccount = "";
        String oldFromAccount = "";
        String oldAmount = "";
        String oldNote = "";
        Date sqlDate = new Date();

        try {
            TableModel targetTransaction = populateEditTransaction(target);
            
            if ( targetTransaction.getValueAt(0, 1) != null )
            sqlDate = fromSQL.parse(targetTransaction.getValueAt(0, 1).toString()); 
            
            if ( targetTransaction.getValueAt(0, 2) != null )
            oldToAccount = targetTransaction.getValueAt(0, 2).toString();
            
            if ( targetTransaction.getValueAt(0, 3) != null )
            oldFromAccount = targetTransaction.getValueAt(0, 3).toString();
            
            if ( targetTransaction.getValueAt(0, 4) != null )
            oldAmount = targetTransaction.getValueAt(0, 4).toString();
            
            if ( targetTransaction.getValueAt(0, 5) != null )
            oldNote = targetTransaction.getValueAt(0, 5).toString();
            
        } catch (Exception e) { System.out.println(e); }
        calendar.setTime(sqlDate);
        

        transactionID = target;
        transactionAddEdit.years.setSelectedItem(calendar.get(Calendar.YEAR));
        transactionAddEdit.months.setSelectedIndex(calendar.get(Calendar.MONTH));
        transactionAddEdit.days.setSelectedItem(calendar.get(Calendar.DATE));
        transactionAddEdit.toAccount.setSelectedItem(oldToAccount);
        transactionAddEdit.fromAccount.setSelectedItem(oldFromAccount);
        transactionAddEdit.amount.setText(oldAmount);
        transactionAddEdit.note.setText(oldNote);
        
        transactionAddEdit.acceptButton.setText("Save");
        transactionAddEdit.setTitle("Edit");
        transactionAddEdit.setVisible(true);
        
    }
    
    private static DefaultTableModel populateEditTransaction (String target) {
        try {
            return SQL.requestTableData("SELECT * FROM TRANSACTIONS WHERE TRANSACTIONS.transactionID = '" + target + "'");
        } catch (Exception e) { System.out.println("Population Error: " + e);}
        return null;
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToggleButton1 = new javax.swing.JToggleButton();
        jLabel1 = new javax.swing.JLabel();
        toAccount = new javax.swing.JComboBox<>();
        days = new javax.swing.JComboBox<>();
        months = new javax.swing.JComboBox<>();
        years = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        amount = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        fromAccount = new javax.swing.JComboBox<>();
        acceptButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        note = new javax.swing.JTextArea();
        jLabel4 = new javax.swing.JLabel();
        qp = new javax.swing.JButton();

        jToggleButton1.setText("jToggleButton1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("To: ");

        toAccount.setEditable(true);

        days.setModel(MyFunctions.getDaysModel());

        months.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" }));
        months.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                monthsActionPerformed(evt);
            }
        });

        years.setModel(MyFunctions.getYearsModel());
        years.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                yearsActionPerformed(evt);
            }
        });

        jLabel2.setText("Amount:");

        jLabel3.setText("From: ");

        fromAccount.setEditable(true);

        acceptButton.setText("Accept");
        acceptButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                acceptButtonActionPerformed(evt);
            }
        });

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        jLabel5.setText("Date: ");

        note.setColumns(20);
        note.setRows(5);
        jScrollPane1.setViewportView(note);

        jLabel4.setText("Note:");

        qp.setText("quickpop");
        qp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                qpActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(qp)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(acceptButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cancelButton))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(amount, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(toAccount, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(fromAccount, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(days, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(months, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(years, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(toAccount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(fromAccount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(days, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(months, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(years, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(amount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(acceptButton)
                    .addComponent(cancelButton)
                    .addComponent(qp))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    
    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void acceptButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_acceptButtonActionPerformed
        // TODO add your handling code here:
        String date = days.getSelectedItem() + String.valueOf(months.getSelectedIndex() + 1) + years.getSelectedItem();
        String toName = toAccount.getSelectedItem().toString();
        String fromName = fromAccount.getSelectedItem().toString();
        String amt = amount.getText();
        String nt = note.getText();
        
        if (date.length() < 8)
            date = "0" + date;

        if (isNumeric(amt)){
            if (acceptButton.getText().equals("Add"))
                MyFunctions.addTransaction(date, toName, fromName, amt, nt);
            else
                MyFunctions.editTransaction(transactionID, date, toName, fromName, amt, nt);
            Home.getInstance().updateTransactionsTable();
        } else { JOptionPane.showMessageDialog(null, "TRANSACTION AMOUNT REQUIRED", "MISSING FIELD", JOptionPane.ERROR_MESSAGE);}
    }//GEN-LAST:event_acceptButtonActionPerformed

    private void monthsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_monthsActionPerformed
        // TODO add your handling code here:
        int selectedYear = Integer.parseInt(years.getSelectedItem().toString());
        int selectedMonth = months.getSelectedIndex();       
        days.setModel(MyFunctions.getDaysModel( selectedYear, selectedMonth ));
    }//GEN-LAST:event_monthsActionPerformed

    private void yearsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_yearsActionPerformed
        // TODO add your handling code here:
        int selectedYear = Integer.parseInt(years.getSelectedItem().toString());
        int selectedMonth = months.getSelectedIndex();
        days.setModel(MyFunctions.getDaysModel( selectedYear, selectedMonth ));
    }//GEN-LAST:event_yearsActionPerformed

    private void qpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_qpActionPerformed
        // TODO add your handling code here:
        Random ran = new Random(); 

        
        for(int i = 0; i <=200; i ++) {
                    int n1 = ran.nextInt((81 - 79) + 1) + 79;
                    int n2 = ran.nextInt((60 - 10) + 1) + 10;
                    int n3 = ran.nextInt((5000 - 100) + 1) + 100;
            toAccount.setSelectedIndex(n1);
            fromAccount.setSelectedIndex(n2);
            amount.setText(String.valueOf(n3));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(TransactionAddEdit.class.getName()).log(Level.SEVERE, null, ex);
            }
            acceptButtonActionPerformed( evt );
        }
    }//GEN-LAST:event_qpActionPerformed

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
            java.util.logging.Logger.getLogger(TransactionAddEdit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TransactionAddEdit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TransactionAddEdit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TransactionAddEdit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
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
                transactionAddEdit.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton acceptButton;
    private javax.swing.JTextField amount;
    private javax.swing.JButton cancelButton;
    private javax.swing.JComboBox<String> days;
    private javax.swing.JComboBox<String> fromAccount;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JComboBox<String> months;
    private javax.swing.JTextArea note;
    private javax.swing.JButton qp;
    private javax.swing.JComboBox<String> toAccount;
    private javax.swing.JComboBox<String> years;
    // End of variables declaration//GEN-END:variables
}
