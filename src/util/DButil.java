
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
  
    /**
     * This function connects the program to the given database 
     * 
     * @return The connection to the database
     * @throws SQLException
     */
    
    public static Connection getConnection() throws SQLException {
	  
        try {
        	
            Class.forName(Driver);

        } catch (ClassNotFoundException e) {
        	
        	e.printStackTrace();
        	con.close();
        }    
        
        con = (Connection) DriverManager.getConnection(ConnectionString, user, pwd);
        return con;
    }

}
    

