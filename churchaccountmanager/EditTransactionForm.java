package churchaccountmanager;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;
import javax.swing.DefaultComboBoxModel;
import java.util.Calendar;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class EditTransactionForm extends javax.swing.JFrame {
    

    SQL sr = new SQL();
    private final Calendar cal = Calendar.getInstance();
    private final Calendar loCal = Calendar.getInstance();
    private final DefaultComboBoxModel cbxYears = new DefaultComboBoxModel();
    private final DefaultComboBoxModel cbxMonths = new DefaultComboBoxModel();
    private final DefaultComboBoxModel cbxDays = new DefaultComboBoxModel();
    private DefaultTableModel tempTable = new DefaultTableModel();
    String transID;
    
    public EditTransactionForm() {
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowActivated(java.awt.event.WindowEvent evt) {
                onLoad(evt);
            }
            
        });
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        initComponents();
    }
    
        @SuppressWarnings("unchecked")
    private void initComponents() {
        jLabel1 = new javax.swing.JLabel();
        accountCBX = new javax.swing.JComboBox<>();
        dayCBX = new javax.swing.JComboBox<>();
        monthCBX = new javax.swing.JComboBox<>();
        yearCBX = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        amountTxt = new javax.swing.JTextField();
        purposeCBX = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        description = new javax.swing.JTextArea();
        addTransactionBTN = new javax.swing.JButton();
        cancel = new javax.swing.JButton();
        isRestricted = new javax.swing.JCheckBox();
        jToggleButton1 = new javax.swing.JToggleButton();
        jLabel5 = new javax.swing.JLabel();

        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        populateYears();
        populateMonths();
        populateDays();

        jLabel1.setText("Account Name:");

        accountCBX.setEditable(true);
        
        jLabel2.setText("Amount: ");

        amountTxt.setText("100");

        purposeCBX.setEditable(true);
        
        jLabel3.setText("Purpose: ");

        jLabel4.setText("Notes:");
        
        description.setColumns(20);
        description.setRows(5);
        description.setText("None");
        jScrollPane1.setViewportView(description);
 
        yearCBX.addActionListener(this::refreshDayCBX);
        monthCBX.addActionListener(this::refreshDayCBX);
        
        addTransactionBTN.setText("Okay");
        addTransactionBTN.addActionListener(this::editTransaction);

        cancel.setText("Cancel");
        cancel.addActionListener(this::cancel);

        isRestricted.setText("Restricted");

        jToggleButton1.setText("Receivable");
        jToggleButton1.addActionListener(this::toggleTransactionType);

        jLabel5.setText("Transaction Type:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(isRestricted)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                        .addComponent(addTransactionBTN)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cancel))
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(accountCBX, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(purposeCBX, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(amountTxt)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jToggleButton1))
                            .addComponent(jLabel4)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(dayCBX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(monthCBX, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(yearCBX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(accountCBX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dayCBX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(monthCBX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(yearCBX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(amountTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(purposeCBX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jToggleButton1)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(isRestricted)
                    .addComponent(addTransactionBTN)
                    .addComponent(cancel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }   
    
    private void refreshDayCBX(java.awt.event.ActionEvent evt){
        populateDays();
    }
    

    
    private void populateYears(){
        int startYear = -5;
        int endYear = 5;
        for ( int cbxStart = startYear ; cbxStart < endYear ; cbxStart++){
            cbxYears.addElement(cal.get(Calendar.YEAR) + cbxStart);
        }
        yearCBX.setModel(cbxYears);
    }
    
    private void populateMonths(){
        String[] dfs = new DateFormatSymbols().getMonths();
        for ( int cbxStart = 0 ; cbxStart < 12 ; cbxStart++){   
            cbxMonths.addElement( dfs[cbxStart] );
        }
        monthCBX.setModel(cbxMonths);
    }
    
    private void populateDays(){
        int selectedYear = Integer.parseInt(yearCBX.getSelectedItem().toString());
        int selectedMonth = monthCBX.getSelectedIndex()+1;
        loCal.set(Calendar.YEAR, selectedYear);
        loCal.set(Calendar.MONTH, selectedMonth);
        int daysOfMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        cbxDays.removeAllElements();
        for ( int cbxStart = 0 ; cbxStart < daysOfMonth ; cbxStart++){   
            cbxDays.addElement( cbxStart + 1 );
        }
        dayCBX.setModel(cbxDays);
    }   
    
    private void editTransaction(java.awt.event.ActionEvent evt) {
        String transactionDate = dayCBX.getSelectedItem().toString() + monthCBX.getSelectedItem().toString() + yearCBX.getSelectedItem().toString();
        String transactionType = jToggleButton1.getText();
        String accountID = "Anonymous";
        String accountName = accountCBX.getSelectedItem().toString();
        String amount = amountTxt.getText();
        String purpose = purposeCBX.getSelectedItem().toString();
        String restricted;
        String desc = description.getText();

        //Check if Account Exists, otherwise assign to Anonymous
        try {
            DefaultTableModel accountCheck = sr.requestTableData("SELECT ACCOUNTS.accountID FROM ACCOUNTS WHERE ACCOUNTS.accountName ='" +  accountName + "'");
            if ( !(accountCheck.getRowCount() < 1 ) ) {
                accountID = accountCheck.getValueAt(0, 0).toString(); }
        } catch (Exception e){ System.out.println(e);}

        try {
            DefaultTableModel purposeCheck = sr.requestTableData("SELECT PURPOSES.purposeName FROM PURPOSES WHERE PURPOSES.purposeName ='" + purpose + "'");
            if ( !(purposeCheck.getRowCount() > 0) )
                createPurpose(purpose); 
        } catch (Exception e) { System.out.println(e);}

        //Check Restricted
        if (isRestricted.isSelected())
            restricted = "True";
        else
            restricted = "False";
        
        if( JOptionPane.showConfirmDialog(null, "All Information is Correct", "Confirm Changes", JOptionPane.OK_CANCEL_OPTION)==0 )
        try {    
            sr.runSQL("UPDATE TRANSACTIONS SET "
                    + "TRANSACTIONS.transactionDate = TO_DATE ('" + transactionDate + "', 'dd/mm/yyyy'), "
                    + "TRANSACTIONS.transactionType = '" + transactionType + "', "
                    + "TRANSACTIONS.accountID = '" + accountID + "', "
                    + "TRANSACTIONS.accountName = '" + accountName + "', "
                    + "TRANSACTIONS.amount = '" + amount + "', "
                    + "TRANSACTIONS.purpose = '" + purpose + "', "
                    + "TRANSACTIONS.description = '" + desc + "', "
                    + "TRANSACTIONS.restricted = '" + restricted +"' "
                    + "WHERE TRANSACTIONS.transactionID = '" + transID + "'");
            
            JOptionPane.showMessageDialog(null, "Transaction Updated!", "Transaction Updated", JOptionPane.PLAIN_MESSAGE);
            transID = ""; 
            this.dispose();
        } catch (SQLException ex) {
            Logger.getLogger(EditTransactionForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void cancel(java.awt.event.ActionEvent evt) {
        this.dispose();
    }

    private void toggleTransactionType(java.awt.event.ActionEvent evt) {
    }

    private void onLoad(java.awt.event.WindowEvent evt) {
        try{
            tempTable = sr.requestTableData("SELECT * FROM TRANSACTIONS WHERE TRANSACTIONS.transactionID = '" + transID + "'");
            String accountName = tempTable.getValueAt(0, 4).toString();
            String transactionType = tempTable.getValueAt(0, 2).toString();
            String amount = tempTable.getValueAt(0, 5).toString();
            String purpose = tempTable.getValueAt(0, 6).toString();
            String desc = tempTable.getValueAt(0, 7).toString();
            String restricted = tempTable.getValueAt(0, 8).toString();

            //setDate
            SimpleDateFormat fromSQL = new SimpleDateFormat("dd-MMM-yy");
            Date sqlDate = fromSQL.parse(tempTable.getValueAt(0, 1).toString());
            
            cal.setTime(sqlDate);

            yearCBX.setSelectedItem(cal.get(Calendar.YEAR));
            monthCBX.setSelectedIndex(cal.get(Calendar.MONTH));
            dayCBX.setSelectedItem(cal.get(Calendar.DATE));
            
            //setTransactionType
            if(transactionType.equals("Receivable")) {
                jToggleButton1.setSelected(false);
                jToggleButton1.setText("Receivable");
            }
            else {
                jToggleButton1.setSelected(true);
                jToggleButton1.setText("Payable");
            }
            
            accountCBX.setSelectedItem(accountName);

            amountTxt.setText(amount);
            
            purposeCBX.setSelectedItem(purpose);
            
            description.setText(desc);
            
            if (restricted.equals("True"))
                isRestricted.setSelected(true);
            else
                isRestricted.setSelected(false);  
        } catch (Exception e) {System.out.println(e);} 
    }
    
    private void createPurpose (String purpose){
        try {
            sr.runSQL( "INSERT INTO PURPOSES "
                + "VALUES('" + purpose + "')");
            populatePurpose();
            System.out.print("Purpose Added!\n");
        } catch (Exception e){ System.out.println(e);}
    }
    public void setTransactionID(String transactionIDFromMain) {
        transID = transactionIDFromMain;
    }
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
            java.util.logging.Logger.getLogger(EditTransactionForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EditTransactionForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EditTransactionForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EditTransactionForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EditTransactionForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> accountCBX;
    private javax.swing.JButton addTransactionBTN;
    private javax.swing.JTextField amountTxt;
    private javax.swing.JButton cancel;
    private javax.swing.JComboBox<String> dayCBX;
    private javax.swing.JTextArea description;
    private javax.swing.JCheckBox isRestricted;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JComboBox<String> monthCBX;
    private javax.swing.JComboBox<String> purposeCBX;
    private javax.swing.JComboBox<String> yearCBX;
    // End of variables declaration//GEN-END:variables
}
