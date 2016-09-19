    package JDBCfiles;
    import java.io.FileInputStream;
    import java.sql.*;
    import java.util.Properties;
public class PreparedStatementexclass {
    //   private static final String userName = "root";
    //   private static final String pass = "admin";
    //   static final String url = "jdbc:mysql://localhost:3306/tiy?autoReconnect=true&useSSL=false";
       static Connection myConn = null;
       static Statement stmt = null;
       static PreparedStatement pstmt = null;
       static ResultSet myrs = null;
       static int id = 300;
       static String firstname; static String secondname;
    //import jdbcDemo.JDBCconnect.java;
   public static void main(String[] args) {

       try {myConn = DriverManager.getConnection(JDBCconnect.thedburl,JDBCconnect.theuser,JDBCconnect.thepassword);
       // 2. Create the SQL Statement
       
       // 3. Execute the SQL Query
       Properties pr= new Properties();
       FileInputStream fis = new FileInputStream("inputdata.txt");
       pr.load(fis);
       //String data = "'"+pr.getProperty("1st")+"','"+pr.getProperty("2nd")+"','"+pr.getProperty("start")+"','"+pr.getProperty("major")+"','"+pr.getProperty("sat")+"','"+ pr.getProperty("gpa")+"'";
       //"'"+pr.getProperty("id")+"','"+pr.getProperty("1st")+"\","\"+pr.getProperty("2nd")+"\","\"+pr.getProperty("start")+"\","\"+pr.getProperty("maj")+"\","\"+pr.getProperty("sat")+"\","\"+ pr.getProperty("gpa")+";
      //System.out.println(data);
       stmt = myConn.prepareStatement("Select * from student where gpa>? and sat > ?");
      // stmt = myConn.prepareStatement();
       pstmt.setDouble(1,2.0);
       pstmt.setInt(2,1000);
       myrs= pstmt.executeQuery();
       display();
       System.out.println("Second Result Set");
       
       
       while(mrs.next()){
           //String fname = 
          System.out.printf("%s, %s, %s, %d,%f mrs.getString("first_name"),mrs.getString("last_name"),mrs.getString("major_id"),mrs.getString("sat"),mrs.getString("gpa"));
       
       
       
       }  
}}