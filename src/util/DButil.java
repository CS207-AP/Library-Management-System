
package util;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DButil {
    

    
    private static Connection con;
    private static final String Driver = "com.mysql.cj.jdbc.Driver";
    private static final String ConnectionString = "jdbc:mysql://localhost/lms_db";
    private static final String user = "root";
    private static final String pwd = "password";
   
  public static Connection getConnection() throws SQLException {
        try {
            Class.forName(Driver);
        } catch (ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
            con.close();
        }      
        con = (Connection) DriverManager.getConnection(ConnectionString, user, pwd);
        return con;
    }


}//end JDBCExample
    

