package JDBCfiles;
import java.io.FileInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;
//import jdbcDemo.JDBCconnect.java;
public class JDBCcommands {

//   private static final String userName = "root";
//   private static final String pass = "admin";
//   static final String url = "jdbc:mysql://localhost:3306/tiy?autoReconnect=true&useSSL=false";
   static Connection myConn = null;
   static Statement stmt = null;
   static ResultSet rs = null;
   static int id = 300;
   static String firstname; static String secondname;
   private double satInterimscore;
   ArrayList<String> backupsarr = new ArrayList<>();
  
   public static void close(Connection con, Statement stmt) throws SQLException {
       if (con != null) {
           con.close();      }
       if (stmt != null) {
           stmt.close();       }
    }
//   if (rs != null) {rs.close();       }
   public void update(String first, String second, double score) throws SQLException {
       try {
           // 1. Connection to the Database
           myConn = DriverManager.getConnection(JDBCconnect.thedburl,JDBCconnect.theuser,JDBCconnect.thepassword);
           stmt = myConn.createStatement();
           // 23. create and Execute the SQL Query
           String sql = "update student set gpa ="+"'"+score+"'"+",sat=1400 , major_id='gbs' where first_name="+"'"+first+"'"+ "and last_name="+"'"+second+"'";
           satInterimscore = score;
           int rowAffected = stmt.executeUpdate(sql);
//           UPDATE mytable SET title = CASE
//                   WHEN id = 1 THEN 'Great Expectations';
//                   WHEN id = 2 THEN 'War and Peace';
//                   ELSE title
//                   END;
           // 4. Process the result set
           displayStudent(first,second);
//           if (rowAffected == 1) {
//               System.out.println("Row Affected by update" + rowAffected);
//           } else {
//               System.out.println("Row Not Affected by update" + rowAffected);
//           }

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
   //delete from student where last_name='washington';
   public void insertStudent() throws SQLException {
       try {myConn = DriverManager.getConnection(JDBCconnect.thedburl,JDBCconnect.theuser,JDBCconnect.thepassword);
           // 2. Create the SQL Statement
           stmt = myConn.createStatement();
           // 3. Execute the SQL Query
           Properties pr= new Properties();
           FileInputStream fis = new FileInputStream("inputdata.txt");
           pr.load(fis);
           String data = "'"+pr.getProperty("1st")+"','"+pr.getProperty("2nd")+"','"+pr.getProperty("start")+"','"+pr.getProperty("major")+"','"+pr.getProperty("sat")+"','"+ pr.getProperty("gpa")+"'";
           //"'"+pr.getProperty("id")+"','"+pr.getProperty("1st")+"\","\"+pr.getProperty("2nd")+"\","\"+pr.getProperty("start")+"\","\"+pr.getProperty("maj")+"\","\"+pr.getProperty("sat")+"\","\"+ pr.getProperty("gpa")+";
          //System.out.println(data);
           firstname=pr.getProperty("1st");
           secondname=pr.getProperty("2nd");
           String sql = "insert student (first_name,last_name,major_id,sat,gpa) values('George','Washington',null,1600,4.0);";
           //String sql = "insert student values(data);";
           int rowAffected = stmt.executeUpdate(sql);
           // 4. Process the result set
           if (rowAffected == 1) {
               System.out.println("debug Row Affected by insert" + rowAffected+"for "+firstname+secondname);
           } else {
               System.out.println("Row Not Affected by insert" + rowAffected);
           }
       } catch (Exception exc) {
           exc.printStackTrace();
       } finally {
           close(myConn,stmt);}
   }//ALTER TABLE assignment drop FOREIGN KEY assignment_ibfk_1,
   public void displayStudent(String first,String second) throws SQLException {
       try {myConn = DriverManager.getConnection(JDBCconnect.thedburl,JDBCconnect.theuser,JDBCconnect.thepassword);
           // 2. Create the SQL Statement
           stmt = myConn.createStatement();
           String sql = "select * from student where first_name="+"'"+first+"'"+ "and last_name="+"'"+second+"'";
           System.out.println(sql);
           // 3. Execute the SQL Query
           rs =stmt.executeQuery(sql);
           System.out.println("ID "  + "  "+ "First Name " + "\t "+"Last Name"+ "\t "+ "Major"+ "\t "+"SAT"+ "\t "+ "GPA");
           System.out.println("==========================================================================================================");
           if (rs!=null){    
           while(rs.next())
                   System.out.println(rs.getString("id")+ "  "+rs.getString("first_name") + "\t\t"+ rs.getString("last_name")+ "\t"+rs.getString("major_id")+ " \t"+rs.getString("gpa")+ " \t"+rs.getString("sat"));
           //int rowAffected = stmt.executeQuery(sql);
           // 4. Process the result set
           } else  {
               System.out.println("Query not performed");
           }
       } catch (Exception exc) {
           exc.printStackTrace();
       } finally {
           close(myConn,stmt);}
   }

   public void deleteData(String first, String last) throws SQLException {
       try {
           myConn = DriverManager.getConnection(JDBCconnect.thedburl,JDBCconnect.theuser,JDBCconnect.thepassword);
           stmt = myConn.createStatement();             
           //String sql = "delete from student where sat="+satInterimscore+" and"+ "last_name="+"'"+last+"'";
           String sql = "delete from student where sat=1400  and last_name='Washington'";
           int rowAffected = stmt.executeUpdate(sql);
           System.out.println();

           // 4. Process the result set
           if (rs != null) {
               
               displayStudent(first,last);
               System.out.println("Delete occurred at row" + rowAffected + " for student "+first +" "+last);
           } else {
               System.out.println("No delete occurred" + rowAffected);
           }

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
   public void backup() throws SQLException {
       try {
           myConn = DriverManager.getConnection(JDBCconnect.thedburl,JDBCconnect.theuser,JDBCconnect.thepassword);
           stmt = myConn.createStatement();             
           //String sql = "delete from student where sat="+satInterimscore+" and"+ "last_name="+"'"+last+"'";
           String sql = "select * from student;";
           //int rowAffected = stmt.executeUpdate(sql);
           System.out.println();
           rs =stmt.executeQuery(sql);
           System.out.println("Backup Resultset for recreating Table fields:First Name, Second Name, Major, Sat, GPA");
           System.out.println("====================================================================================");
           if (rs!=null){    
           while(rs.next()){
                   //System.out.println(rs.getString("id")+ "  "+rs.getString("first_name") + "\t\t"+ rs.getString("last_name")+ "\t"+rs.getString("major_id")+  " \t"+rs.getString("sat")+ "    "+rs.getString("gpa"));
          String sql2 = "insert student (first_name,last_name,major_id,sat,gpa) values('George','Washington',null,1600,4.0);";
           String start ="insert student (first_name,last_name,major_id,sat,gpa) values (";
           int psat = Integer.parseInt(rs.getString("sat"));
           double gpad = Double.parseDouble(rs.getString("gpa"));
           //System.out.printf("%s %s,%s, %s, %d,%2.1f", start,rs.getString("first_name"),rs.getString("last_name"),rs.getString("major_id"),rs.getString("sat"),rs.getString("gpa"));
           System.out.printf("%s'%s','%s','%s',%d,%2.2f);%n", start,rs.getString("first_name"),rs.getString("last_name"),rs.getString("major_id"),psat,gpad);
                   //rs.getString("first_name"),rs.getString("last_name"),rs.getString("major_id"),rs.getString("sat"),rs.getString("gpa"));
           
           //int rowAffected = stmt.executeQuery(sql);
           // 4. Process the result set
//           } else  {
//               System.out.println("Query not performed");
           }
          }
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
   public void backupAll() throws SQLException {
       try {
           myConn = DriverManager.getConnection(JDBCconnect.thedburl,JDBCconnect.theuser,JDBCconnect.thepassword);
           stmt = myConn.createStatement();             
           //String sql = "delete from student where sat="+satInterimscore+" and"+ "last_name="+"'"+last+"'";
           String[] myStringArray = {"student","class","major"};
           String sql = "select * from student;";
           //int rowAffected = stmt.executeUpdate(sql);
           System.out.println();
           rs =stmt.executeQuery(sql);
           System.out.println("Backup Resultset for recreating Table student fields:First Name, Second Name, Major, Sat, GPA");
           System.out.println("====================================================================================");
           if (rs!=null){    
           while(rs.next()){
           String start ="insert student (first_name,last_name,major_id,sat,gpa) values (";
           int psat = Integer.parseInt(rs.getString("sat"));
           double gpad = Double.parseDouble(rs.getString("gpa"));
           String insert = String.format("%s'%s','%s','%s',%d,%2.2f);", start,rs.getString("first_name"),rs.getString("last_name"),rs.getString("major_id"),psat,gpad);
           backupsarr.add(insert);
           }
           
           String sql2 = "select * from major;";
           //int rowAffected = stmt.executeUpdate(sql);
           System.out.println();
           ResultSet rs =stmt.executeQuery(sql2);
           //ha!! rs works twice and gets reset above
           System.out.println("Backup Resultset for recreating Table fields:ID, Major Name, MinSAT");
           System.out.println("====================================================================================");
           
           if (rs!=null){    
           while(rs.next()){
           String start2 ="insert major (id,majorname,maj_minsat) values (";
//           int minsat = Integer.parseInt(rs2.getString("maj_minsat"));
           String insert2 = String.format("%s'%s','%s','%s');", start2,rs.getString("id"),rs.getString("majorname"),rs.getInt("maj_minsat"));
           backupsarr.add(insert2);
           
          }
           for(int i=0;i<backupsarr.size();i++){
               System.out.println(backupsarr.get(i));
               //this prints all the 
           } 
           }
           }   
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
   public static void main(String[] args) throws SQLException {

       JDBCconnect connection1 = new JDBCconnect();
       connection1.connect();
       JDBCcommands connection2 = new JDBCcommands();
       
       connection2.insertStudent();
       connection2.displayStudent(firstname,secondname);
     connection2.update(firstname,secondname,3.5);
       connection2.deleteData(firstname,secondname);
       //connection2.backup();
       connection2.backupAll();


   }

}