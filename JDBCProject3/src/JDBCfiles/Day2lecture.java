package JDBCfiles;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class Day2lecture {
    
    public static Connection myConn=null;
    public static PreparedStatement pStmt=null;
    public static ResultSet myRs=null;
    
    public static void main(String[] args) throws SQLException {
        
        //deleteData();
        fetchData();
        
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
            makeConnection();
            //now we "preparaeStateement" instead of createStatement. need to test if can follow same syntax
//            stmt = myConn.createStatement();
//            String sql = "select * from student where first_name="+"'"+first+"'"+ "and last_name="+"'"+second+"'";

            // 3. Execute the SQL Query
            //rs =stmt.executeQuery(sql);
            pStmt= myConn.prepareStatement("select * from student where gpa > ? and sat > ?");
            
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
    private static void makeConnection() throws FileNotFoundException, IOException, SQLException {
        Properties prop= new Properties();
        prop.load(new FileInputStream("jdbcconnect2.properties"));
        String dburl= prop.getProperty("dburl");
        String user=prop.getProperty("user");
        String pass=prop.getProperty("password");
        myConn=DriverManager.getConnection(dburl, user, pass);
        
    }
    
    private static void deleteData() throws SQLException{
        
        try{
            makeConnection();
            pStmt= myConn.prepareStatement("delete from student where id= ?");
            
            pStmt.setInt(1, 170);
            
            pStmt.executeUpdate();
                    
        }catch(Exception ex){
            
        }finally{
            close();
            
        }
        
    }
    
    public static void close() throws SQLException{
        if(myConn!=null)
            myConn.close();
        if(pStmt != null)
            pStmt.close();
        if(myRs != null)
            myRs.close();
        
    }

}