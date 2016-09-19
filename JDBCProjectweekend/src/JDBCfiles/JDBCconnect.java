package JDBCfiles;

import java.io.FileInputStream;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class JDBCconnect {

       static Connection myConn = null;
       static Statement stmt = null;
       static ResultSet rs = null;
       static String theuser;
      
       static String thepassword; 
       static String thedburl;
      public void connect() throws SQLException{
          //load the props file
      try{
        Properties props= new Properties();
        FileInputStream fis = new FileInputStream("jdbcconnect.properties");
         props.load(fis);     
        //2. read the props
        theuser= props.getProperty("user");
       thepassword = props.getProperty("password");
        thedburl= props.getProperty("dburl");
        myConn = (Connection)DriverManager.getConnection(thedburl,theuser,thepassword);
        stmt = (Statement) myConn.createStatement();
        rs =stmt.executeQuery("select * from student;");
//        System.out.println("First Name " + "\t "+"Last Name");
//            while(rs.next())
//                System.out.println(rs.getString("first_name") + "\t \t"+ rs.getString("last_name"));
        
//        
      } catch (Exception exc) {
          exc.printStackTrace();
      } finally {
          if (myConn != null) {
              myConn.close();
          }
          if (stmt != null) {
              stmt.close();
          }
          
      }
      }
}