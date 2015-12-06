package churchaccountmanager;

import java.awt.HeadlessException;
import java.sql.SQLException;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;


public class MyFunctions {
    private static final MyFunctions myFunctions = new MyFunctions();
    
    private MyFunctions() {}
    
    public static MyFunctions getInstance() {
        return myFunctions;
    }

    private static boolean checkAccount(String accountName) {
        try {
            return ( 0 == SQL.requestTableData("SELECT "
                    + "ACCOUNTS.accountName FROM ACCOUNTS "
                    + "WHERE "
                    + "ACCOUNTS.accountName = '" + accountName + "' ").getRowCount() );
        } catch (Exception e) { System.out.println ("Account Checking Error:" + e); }
        return false;
    }
        
    private static boolean checkAccount(String accountID, String accountName) {
        try {
            return ( 0 == SQL.requestTableData("SELECT "
                    + "ACCOUNTS.accountName FROM ACCOUNTS "
                    + "WHERE "
                    + "ACCOUNTS.accountName = '" + accountName + "' "
                    + "AND "
                    + "ACCOUNTS.accountID <> '" + accountID + "'").getRowCount() );
        } catch (Exception e) { System.out.println ("Account Checking Error:" + e); }
        return false;
    }

    public static void addAccount(String name, String address, String contact, String email){
        SimpleDateFormat sdfa = new SimpleDateFormat("mmss");

        if (name.length() > 3) {
            if ( checkAccount(name) ){
                try {
                    Calendar.getInstance().setTimeInMillis(System.currentTimeMillis());
                    String accountID = name.substring(0,3) + sdfa.format(Calendar.getInstance().getTime());
                    SQL.runSQL("INSERT INTO ACCOUNTS VALUES('" + accountID + "', '" + name + "', '" + address + "', '" + contact + "', '" + email + "')");

                    try {
                        SQL.runSQL("UPDATE TRANSACTIONS SET "
                                + "TRANSACTIONS.fromID = '" + accountID + "', "
                                + "Transactions.anonName = '' "
                                + "WHERE TRANSACTIONS.anonName = '" + name + "'");
                    } catch (SQLException | HeadlessException e) {System.out.println("1" + e);}

                    try {
                        SQL.runSQL("UPDATE TRANSACTIONS SET "
                                + "TRANSACTIONS.toID = '" + accountID + "', "
                                + "Transactions.anonName = '' "
                                + "WHERE TRANSACTIONS.anonName = '" + name + "'");
                    } catch (SQLException | HeadlessException e) {System.out.println("2" + e);}  

                    JOptionPane.showMessageDialog(null, "Account Added", "Account Added", JOptionPane.PLAIN_MESSAGE);
                } catch (SQLException | HeadlessException  e){ System.out.println(e); }
            } else { JOptionPane.showMessageDialog(null, "Accout Exists", "Account Name Already Exists", JOptionPane.ERROR_MESSAGE); }
        } else { JOptionPane.showMessageDialog(null, "Name too short", "Name Too Short", JOptionPane.ERROR_MESSAGE); }
    }

    public static void editAccount(String oldName, String accountID, String newName, String address, String contact, String email) {
        if( checkAccount (accountID, newName) ) {
            try{ 
                SQL.runSQL("UPDATE ACCOUNTS SET "
                          + "ACCOUNTS.accountName = '" + newName + "', "
                          + "ACCOUNTS.address = '" + address + "', "
                          + "ACCOUNTS.contact = '" + contact + "',  "
                          + "ACCOUNTS.email = '" + email + "'  "
                          + "WHERE ACCOUNTS.accountID = '" + accountID + "'");
            } catch (SQLException | HeadlessException e) {System.out.println(e);} 

            JOptionPane.showMessageDialog(null, "Account Updated", "Account has been Updated!", JOptionPane.PLAIN_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "NAME TAKEN", newName + " is Taken!", JOptionPane.PLAIN_MESSAGE);
        }
    }
    
    public static void addTransaction(String transactionDate, String toAccount, String fromAccount, String amount, String note){
        SimpleDateFormat sdft = new SimpleDateFormat("YYMMDDHHmmss");
        Calendar.getInstance().setTimeInMillis(System.currentTimeMillis());
        String toID = "";
        String fromID = "";
        String anonName = "";
        String tmp;
        try {            
            tmp = SQL.requestTableData("SELECT ACCOUNTS.accountID FROM ACCOUNTS WHERE ACCOUNTS.accountName = '" + toAccount + "'").getValueAt(0,0).toString();
            if (tmp.length() > 0)
                toID = tmp;
            else
                anonName = toAccount;
        } catch (Exception  e){ System.out.println("FAILED TO GET ACCOUNTID" + e); }   
        
        try {
            tmp =  SQL.requestTableData("SELECT ACCOUNTS.accountID FROM ACCOUNTS WHERE ACCOUNTS.accountName = '" + fromAccount + "'").getValueAt(0,0).toString();
            if (tmp.length() > 0)
                fromID = tmp;
            else
                anonName = fromAccount;
        } catch (Exception  e){ System.out.println("FAILED TO GET ACCOUNTID" + e); }
        
        try {
            SQL.runSQL("INSERT INTO TRANSACTIONS VALUES('" 
                    + sdft.format(Calendar.getInstance().getTime()) + "', " 
                    + "TO_DATE ('" + transactionDate + "', 'DDMMYYYY'), '" 
                    + toID + "', '" 
                    + fromID + "', '" 
                    + amount + "', '" 
                    + note + "', '"
                    + anonName + "')");
            JOptionPane.showMessageDialog(null, "Transaction Added", "Transaction Added", JOptionPane.PLAIN_MESSAGE);
        } catch (Exception  e){ System.out.println(e); }
    }
        
    public static void editTransaction(String target, String transactionDate,String toAccount, String fromAccount, String amount, String note){
        try {

        } catch (Exception  e){ System.out.println(e); }
    }

    public void deleteTarget( int table, String target ) {
        if (target.equals("Anonymous"))
            JOptionPane.showMessageDialog(null, "SYSTEM ACCOUNT!", "CANNOT DELETE ACCOUNT", JOptionPane.WARNING_MESSAGE);
        else {
            if (JOptionPane.showConfirmDialog(null,"Confirm Delete","Deleting ID: " + target, JOptionPane.YES_NO_OPTION) == 0) {   
                if ( table == 0 ) 
                    try {
                        SQL.runSQL("UPDATE TRANSACTIONS SET TRANSACTIONS.toID = 'Anonymous' WHERE TRANSACTIONS.toID = '" + target + "'");
                        SQL.runSQL("UPDATE TRANSACTIONS SET TRANSACTIONS.fromID = 'Anonymous' WHERE TRANSACTIONS.fromID = '" + target + "'");

                        SQL.runSQL("DELETE FROM ACCOUNTS WHERE ACCOUNTS.accountID = '" + target + "'");
                    } catch(Exception e){ System.out.println("ACCOUNT ID ERROR: " + e); }
                else {
                    try {
                        SQL.runSQL("DELETE FROM TRANSACTIONS WHERE TRANSACTIONS.transactionID = '" + target + "'");
                    }catch(Exception e){ System.out.println("TRANSACTION ID ERROR: " + e); }
                }
                JOptionPane.showMessageDialog(null, "Record Deleted.", "RECORD DELETED", JOptionPane.PLAIN_MESSAGE);
            }
        }   
    }
    
    public ComboBoxModel populateAccounts(){
        DefaultComboBoxModel tmpDComboBoxModel = new DefaultComboBoxModel();
        ComboBoxModel tmpComboBoxModel;
        TableModel tmpTableModel = new DefaultTableModel();
        try {
             tmpTableModel = SQL.requestTableData("SELECT ACCOUNTS.accountName FROM ACCOUNTS ORDER BY ACCOUNTNAME DESC");
        } catch (Exception e){ System.out.print(e);}
        if (tmpTableModel.getRowCount() > 0) {
            int rowCount = tmpTableModel.getRowCount();
            tmpDComboBoxModel.removeAllElements();
            for ( int accountNo = 0; accountNo < rowCount ; accountNo++) {
                tmpDComboBoxModel.addElement(tmpTableModel.getValueAt(accountNo, 0));
            }            
        }
        tmpComboBoxModel = tmpDComboBoxModel;
        return tmpComboBoxModel;  
    }
    
    public ComboBoxModel populateYears(){
        DefaultComboBoxModel tmpDComboBoxModel = new DefaultComboBoxModel();
        ComboBoxModel tmpComboBoxModel;
            
        int startYear = -5;
        int endYear = 5;
        
        Calendar.getInstance().setTimeInMillis(System.currentTimeMillis());
        tmpDComboBoxModel.removeAllElements();
        
        for ( int cbxStart = startYear ; cbxStart < endYear ; cbxStart++){
            tmpDComboBoxModel.addElement(Calendar.getInstance().get(Calendar.YEAR) + cbxStart);
        }
        tmpComboBoxModel = tmpDComboBoxModel;
        return tmpComboBoxModel;
    }
    
    public ComboBoxModel repopulateDays(int selectedYear, int selectedMonth){
        DefaultComboBoxModel tmpDComboBoxModel = new DefaultComboBoxModel();
        ComboBoxModel tmpComboBoxModel;
        
        Calendar.getInstance().set(Calendar.YEAR, selectedYear);
        Calendar.getInstance().set(Calendar.MONTH, selectedMonth);
        
        int daysOfMonth = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH);
        
        Calendar.getInstance().setTimeInMillis(System.currentTimeMillis());
        
        tmpDComboBoxModel.removeAllElements();
        for ( int cbxStart = 0 ; cbxStart < daysOfMonth ; cbxStart++){   
            tmpDComboBoxModel.addElement( cbxStart + 1 );
        }
        
        tmpComboBoxModel = tmpDComboBoxModel;
        return tmpComboBoxModel;
    }
    
    public ComboBoxModel populateDays(){
        Calendar.getInstance().setTimeInMillis(System.currentTimeMillis());
        int selectedYear = Calendar.getInstance().get(Calendar.YEAR);
        int selectedMonth = Calendar.getInstance().get(Calendar.MONTH);
        return repopulateDays(selectedYear, selectedMonth);
    } 
}