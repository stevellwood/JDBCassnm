package JDBCProject2;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Admittee {
    public static void admitstudents() throws SQLException{
        String sql0=null;
        try{
            JDBCconnect2.connecttoDB();
            DatabaseMetaData dbm = JDBCconnect2.myConn.getMetaData();
         // check if "employee" table is there
         ResultSet tables = dbm.getTables(null, "tiy2", "admittee" ,new String[] {"TABLE"} );
         if (tables.next()) {
           System.out.println("Table exists");
         }
         else {
           // Table does not exist
         
            sql0 ="create table admittee("
                    + "id int primary key auto_increment,"
                    + "first_name varchar(30) not null,"
                    + "last_name varchar(30) not null,"
                    + "sat int, gpa decimal(4,2) not null"
                    + ")";
            }
            pStmt= JDBCconnect2.myConn.prepareStatement(sql0);
            pStmt.executeUpdate();
            System.out.println("Table admittee is created!");
//            String sql1="insert admittee values (?,?,?,?,?);";
//            pStmt= myConn.prepareStatement(sql1);
//            pStmt.setInt(1, 0);
//            pStmt.setString(2, "Adam");
//            pStmt.setString(3, "Zapel");
//            pStmt.setInt(4, 1200);
//            pStmt.setDouble(5, 3.0);
//            pStmt.executeUpdate();
    }catch(Exception ex){
        ex.printStackTrace();
    }finally{
        if(JDBCconnect2.myConn!=null)
            JDBCconnect2.myConn.close();
//        if(pStmt != null)
//            pStmt.close();
    }
    
}
}


//preparedStatement = dbConnection.prepareStatement(createTableSQL);
//
//System.out.println(createTableSQL);
//
//// execute create SQL stetement
//preparedStatement.executeUpdate();
//
//System.out.println("Table \"dbuser\" is created!");

