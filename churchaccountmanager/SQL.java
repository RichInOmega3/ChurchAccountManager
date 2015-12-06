package churchaccountmanager;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

public class SQL {    
    private static void connectToDatabase(){
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            }   catch (ClassNotFoundException exception){
                    System.out.println("Oracle Driver Class Not found Exception: " + exception.toString());
                }   
    }

    public static void runSQL(String sql)throws SQLException{
        connectToDatabase();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultset = null;
        
        try {
            connection = DriverManager.getConnection("jdbcjdbc:oracle:thin:@localhost:1521:xe", "CAM", "CAM");
            
            try { 
                statement = connection.createStatement();
                
                try {
                    resultset = statement.executeQuery(sql);
                    
                } finally {if (resultset!=null) resultset.close();}
            } finally { if( statement!=null) statement.close();}
        } finally { if(connection!=null) connection.close(); }
    }

    public static DefaultTableModel requestTableData(String sql)throws SQLException{
        connectToDatabase();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultset = null;
        
        Vector columnNames = new Vector();
        Vector data = new Vector();
        try {
            connection = DriverManager.getConnection("jdbcjdbc:oracle:thin:@localhost:1521:xe", "CAM", "CAM");
            
            try { 
                statement = connection.createStatement();
                
                try {
                    resultset = statement.executeQuery(sql);
                    ResultSetMetaData metaData = resultset.getMetaData();

                    // names of columns        
                    int columnCount = metaData.getColumnCount();
                    for (int column = 1; column <= columnCount; column++) {
                        columnNames.add(metaData.getColumnName(column));
                    }

                    // data of the table
                    while (resultset.next()) {
                        Vector vector = new Vector();
                        for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                            vector.add(resultset.getObject(columnIndex));
                        }
                        data.add(vector);
                    }       
                } finally {if (resultset!=null) resultset.close();}
            } finally { if( statement!=null) statement.close();}
        } finally { if(connection!=null) connection.close(); }     
    return new DefaultTableModel(data, columnNames);
    }
}
