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
    public static Db db = null;
       public static Connection myConn = null;
       static String theuser;
       
       static String thepassword; 
       static String thedburl;
       static ResultSet rs = null;
//       static String theuser;
//      
//       static String thepassword; 
//       static String thedburl;
      public Connection connecttoDB() throws FileNotFoundException, IOException, SQLException {
          //load the props file
      try{
        Properties props= new Properties();
        FileInputStream fis = new FileInputStream("jdbcconnect2.properties");
         props.load(fis);     
        //2. read the props
       theuser= props.getProperty("user");
       thepassword = props.getProperty("password");
      thedburl= props.getProperty("dburl");
      db = new Db(thedburl, theuser,thepassword);
        myConn = DriverManager.getConnection(thedburl, theuser, thepassword);
      //why does this need cast
        System.out.println( theuser+ thepassword +thedburl);
      } catch (Exception exc) {
          System.out.println("connection not created1");
          exc.printStackTrace();
      }
    return myConn; 
          
      
      
      }
      }   
