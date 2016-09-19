package JDBCProject2;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class EducationSystem {
    private static java.util.Random rnd = new java.util.Random((new java.util.Date()).getTime());
    
    private static Connection conn = null;
    private static PreparedStatement pState = null;
    private static Properties properties = null;
    
    public static final int NOT_FOUND = -1;
    public static final int LOW_SAT = -2;
        
    String[] studentFirstNames = {"Adam", "Graham", "Ella", "Stanley", "Lou"};
    String[] studentLastNames = {"Zapel", "Krakir", "Vader", "Kupp", "Zar"};
    int[] studentSats = {1200, 500, 800, 1350, 950};
    double[] studentGpas = {3.0, 2.5, 3.0, 3.3, 3.0};
    String[] studentMajors = {"Finance", "General Studies", "Accounting", "Engineering", "Education"};
    
    static {
        properties = new Properties();
        try {
            properties.load(new FileInputStream("common/sql.properties"));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public EducationSystem() throws SQLException {
        this.conn = DBConnect.getConnection();
        
        for(int idx = 0; idx < studentFirstNames.length; idx++) {
            Student student = new Student();
            student.setFirst_name(studentFirstNames[idx]);
            student.setLast_name(studentLastNames[idx]);
            student.setSat(studentSats[idx]);
            student.setGpa(studentGpas[idx]);
            student.setMajorDescription(studentMajors[idx]);
            
            System.out.println(enrollStudent(student));
        }
    }
        
    public String enrollStudent(Student student) throws SQLException {
        pState = conn.prepareStatement(properties.getProperty("sql.enroll"));
                
        pState.setString(1, student.getFirst_name());
        pState.setString(2, student.getLast_name());
        pState.setInt(3, student.getSat());
        pState.setDouble(4, student.getGpa());

        int major_id = getMajorId(student.getMajorDescription(), student.getSat());
        
        StringBuffer sb = new StringBuffer();
        sb.append("Enrolled " + student.getFullName() + " as a new student.\n");
        sb.append(student.getFullName() + " has an SAT score of " + student.getSat() + ".\n");
        
        if(major_id == NOT_FOUND || major_id == LOW_SAT) {
            pState.setNull(5, java.sql.Types.INTEGER);
            if(major_id == NOT_FOUND) {
                sb.append("The major selected by " + student.getFullName() + " was not found.\n");
            } else {
                sb.append("Sorry, but a " + student.getMajorDescription() + " major requires an SAT score of ")
                  .append(getMinSatScore(-1, student.getMajorDescription() ) + ".\n");
            }
        } else {
            pState.setInt(5,  major_id);
            sb.append("Assigned " + student.getFullName() + " to the " + student.getMajorDescription() + " major ")
              .append("which requires an SAT score of " + getMinSatScore(major_id, "") + ".\n");
        }
        pState.executeUpdate();

        getStudentId(student);
        
        /*
        if(major_id > 0) {
            
            pState = conn.prepareStatement(properties.getProperty("sql.list.major.classes"));
            pState.setInt(1, major_id);
            ResultSet rs = pState.executeQuery();
            
            // Guaranteed to be at least 2...
            rs.next();
            int classId1 = rs.getInt("class_id");
            int classId2 = rs.getInt("class_id");
            
            // Register the student
            pState = conn.prepareStatement(properties.getProperty("sql.register.student"));
        } else {
            for(int idx = 1; idx <= 4; idx++) {
                
            }
        }
        */
        return sb.toString();
    }
    

    
    // Returns a valid major id based on the description or -1 if the major is not found OR
    // -2 if the minimum SAT score requirement is not met 
    private int getMajorId(String description, int sat) throws SQLException {
        int major_id = NOT_FOUND;
        PreparedStatement pStateMajor = conn.prepareStatement(properties.getProperty("sql.major.id"));
        pStateMajor.setString(1, description);
        ResultSet rs = pStateMajor.executeQuery();
        
        // Check if the major was found
        if(rs.next()) {
            // Check the sat requirement
            if(rs.getInt("req_sat") > sat) {
                major_id = LOW_SAT;
            } 
            // Set the major_id otherwise
            else {          
                major_id = rs.getInt("id");
            }
        }
        
        pStateMajor.close();
        return major_id;
    }
    
    private int getMinSatScore(int id, String description) throws SQLException {
        PreparedStatement pStateMinSat = conn.prepareStatement(properties.getProperty("sql.major.sat"));
        pStateMinSat.setInt(1, id);
        pStateMinSat.setString(2, description);
        ResultSet rs = pStateMinSat.executeQuery();
        
        // Query is guaranteed to have a result since either the major code or 
        // description is present
        rs.next();
        
        int minSat = rs.getInt("req_sat");
        pStateMinSat.close();
        
        return minSat;      
    }
    
    private void getStudentId(Student student) throws SQLException {
        PreparedStatement pStateId = conn.prepareStatement(properties.getProperty("sql.student.id"));
        pStateId.setString(1, student.getFirst_name());
        pStateId.setString(2, student.getLast_name());
        pStateId.setDouble(3, student.getGpa());
        pStateId.setInt(4,  student.getSat());
        
        ResultSet rs = pStateId.executeQuery();
        rs.next();
        student.setId(rs.getInt("id"));
    }
    
    private int getRandomClass() {
        
        int randomInt = rnd.nextInt(4) + 1; // 1 - 4
        return randomInt;
    }
}