package churchaccountmanager;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class Home extends javax.swing.JFrame {
    
    private static Home home;  
    
    private Home() {
        initComponents();
        updateAccountsTable();
        updateTransactionsTable();
    }
    public static Home getInstance() {
        return home;
		//test insert
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        searchTargetButton = new javax.swing.JButton();
        addButton = new javax.swing.JButton();
        editButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        searchTargetText = new javax.swing.JTextField();
        viewTables = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        accountsTable = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        transactionsTable = new javax.swing.JTable();

        jLabel1.setText("jLabel1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        searchTargetButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/churchaccountmanager/icons/ic_search_black_24dp_1x.png"))); // NOI18N

        addButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/churchaccountmanager/icons/ic_note_add_black_24dp_1x.png"))); // NOI18N
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });

        editButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/churchaccountmanager/icons/ic_mode_edit_black_24dp_1x.png"))); // NOI18N
        editButton.setEnabled(false);
        editButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editButtonActionPerformed(evt);
            }
        });

        deleteButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/churchaccountmanager/icons/ic_delete_black_24dp_1x.png"))); // NOI18N
        deleteButton.setEnabled(false);
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });

        viewTables.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);
        viewTables.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                viewTablesMouseClicked(evt);
            }
        });

        accountsTable.setAutoCreateRowSorter(true);
        accountsTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                accountsTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(accountsTable);

        viewTables.addTab("Accounts", jScrollPane1);

        transactionsTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                transactionsTableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(transactionsTable);

        viewTables.addTab("Transactions", jScrollPane2);

        viewTables.setSelectedIndex(1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(viewTables, javax.swing.GroupLayout.DEFAULT_SIZE, 630, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(addButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(editButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(deleteButton)
                        .addGap(18, 18, 18)
                        .addComponent(searchTargetText)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(searchTargetButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(editButton)
                    .addComponent(deleteButton)
                    .addComponent(searchTargetText, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchTargetButton)
                    .addComponent(addButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(viewTables, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                .addGap(29, 29, 29))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

            
    private String target = "";
    private int selectedTable = 1;
    
    public void updateAccountsTable() {
        TableModel accountsTableModel = new DefaultTableModel();
        try{            
            accountsTableModel = SQL.requestTableData("SELECT "
                + "ACCOUNTS.accountID, "
                + "ACCOUNTS.accountName AS NAME, "
                + "ACCOUNTS.address AS ADDRESS, "
                + "ACCOUNTS.contact AS CONTACT, "
                + "ACCOUNTS.email AS EMAIL, "
                + "SUM(TRANSACTIONS.amount) AS BALANCE "
                + "FROM ACCOUNTS LEFT JOIN TRANSACTIONS "
                + "ON ACCOUNTS.accountID = TRANSACTIONS.toID "
                + "GROUP BY ACCOUNTS.accountID, ACCOUNTS.accountName, ACCOUNTS.address, ACCOUNTS.contact, ACCOUNTS.email");
        } catch(Exception e){ System.out.println(e); }
        accountsTable.setModel(accountsTableModel);
        accountsTable.removeColumn(accountsTable.getColumnModel().getColumn(0));
    }
   
    public void updateTransactionsTable() { 
        TableModel transactionsTableModel = new DefaultTableModel();
        try {    
            transactionsTableModel = SQL.requestTableData("SELECT "
                + "TRANSACTIONS.transactionID, "
                + "TRANSACTIONS.transactionDate, "
                + "TRANSACTIONS.toID AS CASH_TO, "
                + "TRANSACTIONS.fromID AS CASH_FROM, "
                + "TRANSACTIONS.amount, "
                + "TRANSACTIONS.note "
                    
                + "FROM TRANSACTIONS "
                    + "LEFT JOIN ACCOUNTS ON TRANSACTIONS.toID = ACCOUNTS.accountID "
                    + "LEFT JOIN ACCOUNTS ON TRANSACTIONS.fromID = ACCOUNTS.accountID " 
            ) ;
        }catch(Exception e){ System.out.println(e); }
        transactionsTable.setModel(transactionsTableModel);
        transactionsTable.removeColumn(transactionsTable.getColumnModel().getColumn(0));
    }
    
    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        // TODO add your handling code here:
        switch(selectedTable){
            case 0: AccountAddEdit.execAddInstance(); break;
            case 1: TransactionAddEdit.execAddInstance(); break;
        }      
    }//GEN-LAST:event_addButtonActionPerformed

    private void editButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editButtonActionPerformed
        // TODO add your handling code here:
        if(!target.equals(""))
            switch(selectedTable){
                case 0: AccountAddEdit.execEditInstance(target); break;
                case 1: TransactionAddEdit.execEditInstance(target); break;
            }       
        editButton.setEnabled(false);
        deleteButton.setEnabled(false);
    }//GEN-LAST:event_editButtonActionPerformed

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        // TODO add your handling code here:
        switch (selectedTable) {
            case 0:
                MyFunctions.deleteAccount(target);
                updateAccountsTable();
                break;
            case 1:
                MyFunctions.deleteTransaction(target);
                updateTransactionsTable();
                break;
        }        
        editButton.setEnabled(false);
        deleteButton.setEnabled(false);
    }//GEN-LAST:event_deleteButtonActionPerformed
    
    private void viewTablesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_viewTablesMouseClicked
        // TODO add your handling code here:
        selectedTable = viewTables.getSelectedIndex();
        editButton.setEnabled(false);
        deleteButton.setEnabled(false);
    }//GEN-LAST:event_viewTablesMouseClicked

    private void transactionsTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_transactionsTableMouseClicked
        // TODO add your handling code here:
        selectedTable = 1;
        target = "";
        int selectedRow = transactionsTable.getSelectedRow();
        if (selectedRow >= 0) {
            target = transactionsTable.getModel().getValueAt(selectedRow, 0).toString();
            editButton.setEnabled(true);
            deleteButton.setEnabled(true);
        }
    }//GEN-LAST:event_transactionsTableMouseClicked

    private void accountsTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_accountsTableMouseClicked
        // TODO add your handling code here:
        selectedTable = 0;
        target = "";
        int selectedRow = accountsTable.getSelectedRow();
        if (selectedRow >= 0) {
            target = accountsTable.getModel().getValueAt(selectedRow, 0).toString();
            editButton.setEnabled(true);
            deleteButton.setEnabled(true);
        }
    }//GEN-LAST:event_accountsTableMouseClicked

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
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                home = new Home();
                home.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable accountsTable;
    private javax.swing.JButton addButton;
    private javax.swing.JButton deleteButton;
    private javax.swing.JButton editButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton searchTargetButton;
    private javax.swing.JTextField searchTargetText;
    private javax.swing.JTable transactionsTable;
    private javax.swing.JTabbedPane viewTables;
    // End of variables declaration//GEN-END:variables


}
