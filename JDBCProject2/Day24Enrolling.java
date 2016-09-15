package JDBCProject2;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

//import JDBCconnect.java;  //why not work
public class Day24Enrolling {
   
        public static Connection conn = JDBCconnect2.myConn;
        public static PreparedStatement pStmt=null;
        public static ResultSet myRs=null;
        public static Statement stmt=null;
        
        public static void main(String[] args) throws SQLException {
            enroll(Student.newstudents);
        }
        public static void enroll(ArrayList<Student> newstudents) throws SQLException {
            String sql0 ="insert student value (?,?,?,?,?,?);";
            
            pStmt= conn.prepareStatement(sql0);
            
            //pStmt.setString(2, newstudents.get(0));
            for (Student stu: newstudents){
                pStmt.setNull(1, java.util.Types.Integer);
                pStmt.setString(2, newstudents.get(0));//first
                pStmt.setString(3, newstudents.get(1));//second
                pStmt.setInt(4, newstudents.get(2));//sat
                pStmt.setDouble(5, newstudents.get(0));//gpa
                pStmt.setString(6, newstudents.get(0));
                pStmt.executeUpdate();
                pStmt.updateQuery();
            }
//            String sql1="insert admittee values (?,?,?,?,?);";
//            pStmt= myConn.prepareStatement(sql1);
//            pStmt.setInt(1, 0);
//            pStmt.setString(2, "Adam");
//            pStmt.setString(3, "Zapel");
//            pStmt.setInt(4, 1200);
//            pStmt.setDouble(5, 3.0);
//            pStmt.executeUpdate();
        }
        public static void display() throws SQLException{
            
            while(myRs.next()){
                String fName= myRs.getString("first_name");
                String lName= myRs.getString("last_name");
                double gpa=myRs.getDouble("gpa");
                int sat= myRs.getInt("sat");
                
                System.out.printf("%s %s %.2f %d", fName, lName,gpa,sat);
                System.out.println();
            
            }   
            
        }
        
        private static void fetchData() throws SQLException{
            
            try{
                JDBCconnect2.connecttoDB();
                //now we "preparaeStateement" instead of createStatement. need to test if can follow same syntax
//                stmt = myConn.createStatement();
//                String sql = "select * from student where first_name="+"'"+first+"'"+ "and last_name="+"'"+second+"'";

                // 3. Execute the SQL Query
                //rs =stmt.executeQuery(sql);
                pStmt= conn.prepareStatement("select * from student where gpa > ? and sat > ?");
                
                // Set Parameter Values  //not required as part of method!!!!
                pStmt.setDouble(1, 2.0);
                pStmt.setInt(2, 1000);
                //compare this with my conrtaption: String sql = "select * from student where first_name="+"'"+first+"'"+ "and last_name="+"'"+second+"'";
                //Execute the Query -- same syntax but note pstmt is not a stmt
                    myRs= pStmt.executeQuery();
                //Process the first  ResultSet
                     System.out.println("+++++++++++++++++++++++++++");
                     System.out.println("First Result set");
                     System.out.println("+++++++++++++++++++++++++++");
                 display();//only the select statemenst can work with resultsets
                 System.out.println("+++++++++++++++++++++++++++");
                 System.out.println("Second Result set");
                 System.out.println("+++++++++++++++++++++++++++");
                 
                
                //statement reuse (process the second result set)
                // Set Parameter Values
                            pStmt.setDouble(1, 3.0);
                            pStmt.setInt(2, 900);
                            myRs= pStmt.executeQuery();
                            display();
            
            }catch(Exception ex){
                ex.printStackTrace();
            }finally{
                close();
            }
            
        }
        private static void deleteData() throws SQLException{
            
            try{
                JDBCconnect2.connecttoDB();
                pStmt= conn.prepareStatement("delete from student where id= ?");
                
                pStmt.setInt(1, 170);
                
                pStmt.executeUpdate();
                        
            }catch(Exception ex){
                
            }finally{
                close();
                
            }
            
        }
        
        public static void close() throws SQLException{
            if(conn!=null)
                conn.close();
            if(pStmt != null)
                pStmt.close();
            if(myRs != null)
                myRs.close();
            
        }

    }

