package JDBCProject2;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.sql.Connection;
//import com.mysql.jdbc.Connection; //this causes errors with DriverManager
import com.mysql.jdbc.Statement;
//the above have to be imported from lib


public class JDBCconnect2 {

       public static Connection myConn = null;
       
       static ResultSet rs = null;
//       static String theuser;
//      
//       static String thepassword; 
//       static String thedburl;
      public static void connecttoDB() throws FileNotFoundException, IOException, SQLException {
          //load the props file
      try{
        Properties props= new Properties();
        FileInputStream fis = new FileInputStream("jdbcconnect2.properties");
         props.load(fis);     
        //2. read the props
        String theuser= props.getProperty("user");
       String thepassword = props.getProperty("password");
       String thedburl= props.getProperty("dburl");
        myConn = DriverManager.getConnection(thedburl, theuser, thepassword);
      //why does this need cast
        
      } catch (Exception exc) {
          exc.printStackTrace();
      } 
          
      
      }
      
}